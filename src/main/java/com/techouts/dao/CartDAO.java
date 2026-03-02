package com.techouts.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.techouts.entities.Cart;
import com.techouts.entities.CartItem;
import com.techouts.entities.Product;
import com.techouts.entities.User;
import com.techouts.utils.hibernateutil.HibernateUtil;

public class CartDAO {

    public static final String getUserCartHql = "FROM Cart WHERE userId = :user";

    public static Cart getCartByUser(User user) {

        try (Session session = HibernateUtil.getHibernateSession()) {
            Cart userCart = session.createQuery(getUserCartHql, Cart.class)
                    .setParameter("user", user).getSingleResult();

            return userCart;        
        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
    }

    public static boolean addProductToUser(User user, int productId) {

        if (user == null)
            return false;

        Transaction tx = null;

        try (Session session = HibernateUtil.getHibernateSession()) {

            tx = session.beginTransaction();

            Cart userCart = session.createQuery(getUserCartHql, Cart.class)
                    .setParameter("user", user).getSingleResult();

            Product currProduct = ProductDAO.getProductById(productId);

            if (userCart == null || currProduct == null) {

                System.out.println("USER CART OR PRODUCT IS NULL!!!!!");
                return false;
            }

            List<CartItem> cartItems = userCart.getCartItemList();

            CartItem currCartItem = cartItems.stream()
                    .filter((item) -> item.getProductId().getId() == productId)
                    .findFirst()
                    .orElse(null); // gets the product from the cart items list, if item is not present return null

            if (currCartItem == null) {
                CartItem cartItem = new CartItem(userCart, currProduct, 1);
                session.merge(cartItem);

                tx.commit();
                return true;
            }

            currCartItem.setQuantity(currCartItem.getQuantity() + 1);
            session.merge(currCartItem);
            tx.commit();

            return true;

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            System.err.println(e);
        }

        return false;

    }

    public static List<CartItem> getUserCartItems(User user) {

        try (Session session = HibernateUtil.getHibernateSession()) {

            Cart userCart = session.createQuery(getUserCartHql, Cart.class)
                    .setParameter("user", user).getSingleResult();

            List<CartItem> cartItems = userCart.getCartItemList()
                    .stream()
                    .parallel()
                    .peek((item) -> item.getProductId())
                    .collect(Collectors.toList()); // since hibernate return a proxy in the place of Product object
                                                   // inside the CartItem

            return cartItems;

        } catch (Exception e) {
            System.err.println(e);
        }

        return null;

    }

    public static boolean removeCartItem(User user, int cartItemId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getHibernateSession()) {

            tx = session.beginTransaction();

            Cart userCart = session.createQuery(getUserCartHql, Cart.class)
                    .setParameter("user", user).getSingleResult();

            if (userCart == null) {
                return false;
            }

            List<CartItem> cartItems = userCart.getCartItemList();

            CartItem itemForDeletion = cartItems.stream().filter((item) -> item.getId() == cartItemId).findFirst()
                    .orElse(null);

            if (itemForDeletion == null) {
                tx.commit();
                return false;
            }

            userCart.getCartItemList().remove(itemForDeletion); // hibernate will handle the updation until tx is
                                                                // commited

            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            System.err.println(e);
        }

        return false;
    }

    public static boolean decreaseCartItemQuantity(User user, int cartItemId) {

        return changeCartItemQuantity(user, cartItemId, false);

    }

    public static boolean increaseCartItemQuantity(User user, int cartItemId) {

        return changeCartItemQuantity(user, cartItemId, true);

    }

    private static boolean changeCartItemQuantity(User user, int cartItemId, boolean isAddition) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getHibernateSession()) {

            tx = session.beginTransaction();

            Cart userCart = session.createQuery(getUserCartHql, Cart.class)
                    .setParameter("user", user).getSingleResult();

            if (userCart == null) {
                return false;
            }

            List<CartItem> cartItems = userCart.getCartItemList();

            CartItem itemForUpdation = cartItems.stream().filter((item) -> item.getId() == cartItemId).findFirst()
                    .orElse(null);

            if (itemForUpdation == null) {
                tx.commit();
                return false;
            }

            if (isAddition) {
                itemForUpdation.setQuantity(itemForUpdation.getQuantity() + 1);
                session.merge(itemForUpdation);
            } else {
                itemForUpdation.setQuantity(itemForUpdation.getQuantity() - 1);
                if (itemForUpdation.getQuantity() <= 0) {
                    cartItems.remove(itemForUpdation);
                } else {
                    session.merge(itemForUpdation);
                }
            }

            tx.commit();
            System.out.println(true);
            return true;

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            System.err.println(e);
        }

        return false;
    }

}
