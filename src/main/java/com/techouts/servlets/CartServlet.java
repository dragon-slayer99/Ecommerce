package com.techouts.servlets;

import java.io.IOException;
import java.util.List;

import com.techouts.dao.CartDAO;
import com.techouts.entities.CartItem;
import com.techouts.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cart/*")
public class CartServlet extends HttpServlet {

    private float calculateTotalCartPrice(HttpServletRequest req, HttpServletResponse resp, User user, List<CartItem> userCartItems) {

        List<CartItem> cartItems = null;

        if (userCartItems == null) {
            cartItems = CartDAO.getUserCartItems(user);
        }

        cartItems = userCartItems;

        if (cartItems == null) {
            return 0f;
        }

        float result = 0;

        for (CartItem cartItem : cartItems) {

            int productQuantity = cartItem.getQuantity();
            float productPrice = cartItem.getProductId().getPrice();

            result = result + (productPrice * productQuantity);

        }

        req.getSession().setAttribute("cartTotalPrice", result);

        System.out.println("CALCULATED TOTAL PRICE: " + result);
        return result;

    }

    private void addProductToCartItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println(req.getParameter("productId"));
        int productId = Integer.parseInt(req.getParameter("productId"));

        boolean productInsertionStatus = CartDAO.addProductToUser((User) req.getSession(false).getAttribute("user"),
                productId);

        calculateTotalCartPrice(req, resp, (User) req.getSession(false).getAttribute("user"), null);

        System.out.println(productInsertionStatus);
        resp.setContentType("text/plain");
        resp.getWriter().write(productInsertionStatus ? "success" : "failed");

    }

    private void removeProductCartItem(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        System.out.println(req.getParameter("CartItemId"));
        int cartItemId = Integer.parseInt(req.getParameter("CartItemId"));
        CartDAO.removeCartItem((User) req.getSession().getAttribute("user"),
                cartItemId);

        calculateTotalCartPrice(req, resp, (User) req.getSession(false).getAttribute("user"), null);

    }

    private void decreaseProductQuantity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(req.getParameter("CartItemId"));
        int cartItemId = Integer.parseInt(req.getParameter("CartItemId"));
        CartDAO.decreaseCartItemQuantity(
                (User) req.getSession().getAttribute("user"),
                cartItemId);

        calculateTotalCartPrice(req, resp, (User) req.getSession(false).getAttribute("user"), null);

    }

    private void increaseProductQuantity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(req.getParameter("CartItemId"));
        int cartItemId = Integer.parseInt(req.getParameter("CartItemId"));

        CartDAO.increaseCartItemQuantity(
                (User) req.getSession().getAttribute("user"),
                cartItemId);

        calculateTotalCartPrice(req, resp, (User) req.getSession(false).getAttribute("user"), null);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<CartItem> userCartItems = CartDAO.getUserCartItems((User) req.getSession().getAttribute("user"));

        req.setAttribute("cartItemsList", userCartItems);

        calculateTotalCartPrice(req, resp, (User) req.getSession(false).getAttribute("user"), userCartItems);

        req.getRequestDispatcher("/jsp/cart.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("cartTotalPrice",
                calculateTotalCartPrice(req, resp, (User) req.getSession(false).getAttribute("user"), null));

        String path = req.getPathInfo();

        switch (path) {
            case "/add" -> {
                addProductToCartItem(req, resp);
                return;
            }
            case "/remove" -> removeProductCartItem(req, resp);
            case "/decreasecnt" -> decreaseProductQuantity(req, resp);
            case "/increasecnt" -> increaseProductQuantity(req, resp);
            default -> doGet(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/cart");

    }

}
