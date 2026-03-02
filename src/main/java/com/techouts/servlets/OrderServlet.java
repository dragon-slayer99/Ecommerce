package com.techouts.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.techouts.dao.OrderDAO;
import com.techouts.entities.Order;
import com.techouts.entities.OrderItem;
import com.techouts.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/order", "/order/*" })
public class OrderServlet extends HttpServlet {

    private void placeOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = req.getParameter("address");
        System.out.println(address);
        String paymentMethod = req.getParameter("paymentMethod");
        System.out.println(paymentMethod);

        User user = (User) req.getSession(false).getAttribute("user");

        if (user == null) {

            throw new RuntimeException("User not present in the doPost method!!!!");

        }

        float totalPrice = (float) req.getSession().getAttribute("cartTotalPrice");

        boolean orderCreationStatus = OrderDAO.createOrder(user, address, paymentMethod, totalPrice);

        if (orderCreationStatus) {

            req.getSession(false).setAttribute("cartTotalPrice", 0f);

        }

        resp.setContentType("text/plain");
        resp.getWriter().write(orderCreationStatus ? "success" : "failed");
    }

    private void cancelOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("orderId") == null)
            return;

        int orderId = Integer.parseInt(req.getParameter("orderId"));

        boolean cancellationStatus = OrderDAO.changeOrderStatus(orderId, "cancelled");

        System.out.println(cancellationStatus);
        resp.setContentType("text/plain");
        resp.getWriter().write(cancellationStatus ? "success" : "failed");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<Order, List<OrderItem>> userOrderMap = OrderDAO
                .getOrderItems((User) req.getSession(false).getAttribute("user"));

        System.out.println(userOrderMap);
        req.setAttribute("userOrderMap", userOrderMap);

        req.getRequestDispatcher("/jsp/order.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();

        if (path == null) {

            placeOrder(req, resp);

        } else if ("/cancel".equals(path)) {

            cancelOrder(req, resp);

        }

    }

}
