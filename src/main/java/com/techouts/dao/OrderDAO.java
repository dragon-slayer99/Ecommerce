package com.techouts.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.techouts.entities.Cart;
import com.techouts.entities.CartItem;
import com.techouts.entities.Order;
import com.techouts.entities.OrderItem;
import com.techouts.entities.User;
import com.techouts.utils.enums.DeliveryStatus;
import com.techouts.utils.hibernateutil.HibernateUtil;

public class OrderDAO {

    public static boolean createOrder(User user, String address, String paymentMethod, float totalPrice) {

        Transaction tx = null;

        if (user == null || address == null || paymentMethod == null) {
            throw new RuntimeException("No parameter found in OrderDao.createOrder");
        }

        try (Session session = HibernateUtil.getHibernateSession()) {

            tx = session.beginTransaction();

            Order order = new Order(user, totalPrice, LocalDate.now().plusDays(3), paymentMethod, address);

            Cart cart = session.createQuery(CartDAO.getUserCartHql, Cart.class)
                    .setParameter("user", user).getSingleResult();

            List<CartItem> cartItems = cart.getCartItemList();

            if (cartItems == null || cartItems.isEmpty()) {

                return false;

            }

            List<OrderItem> orderItems = convertCartItemsToOrderItems(cart.getCartItemList(), order);

            if (orderItems == null) {
                return false;
            }

            order.setOrderItems(orderItems);

            session.persist(order);

            cart.getCartItemList().clear();

            tx.commit();

            return true;

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();

            System.err.println(e);

        }

        return false;

    }

    public static List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItems, Order order) {

        if (cartItems == null || order == null || cartItems.isEmpty()) {
            return null;
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {

            orderItems.add(new OrderItem(
                    cartItem.getProductId(),
                    order,
                    cartItem.getQuantity(),
                    cartItem.getProductId().getPrice()));

        }

        return orderItems;

    }

    public static boolean changeOrderStatus(int orderId, String status) {

        Transaction tx = null;

        try(Session session = HibernateUtil.getHibernateSession()) {

            tx = session.beginTransaction();

            Order order = session.get(Order.class, orderId);

            if(order != null) {

                order.setDeliveryStatus(DeliveryStatus.valueOf(status.toUpperCase()));

            }

            tx.commit();

            return true;

        } catch (Exception e) {
            if(tx != null) tx.rollback();
            System.err.println(e);
        }

        return false;

    }

    public static Map<Order, List<OrderItem>> getOrderItems(User user) {

        try (Session session = HibernateUtil.getHibernateSession()) {

            Query<Order> query = session.createQuery("FROM Order WHERE userId = :user", Order.class)
                    .setParameter("user", user);

            List<Order> userOrders = query.list();

            if(userOrders == null) {
                throw new RuntimeException("no user order was fetched!!!!");
            }

            Map<Order, List<OrderItem>> userOrderMap = new HashMap<>();

            for (Order order : userOrders) {

                List<OrderItem> orderItems = order.getOrderItems().stream()
                        .parallel()
                        .peek((item) -> item.getProductId())
                        .collect(Collectors.toList()); // to avoid proxy exception

                userOrderMap.putIfAbsent(order, orderItems);

            }
            return userOrderMap;

        } catch (Exception e) {

            System.err.println(e);

        }

        return new HashMap<>();

    }

}
