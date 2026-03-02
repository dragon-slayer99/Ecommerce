package com.techouts.filters;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
        "/home", "/cart", "/product", "/logout", "/order", "/profile", "/admin", "/admin/*"
})
public class UserFilter extends HttpFilter {

    @Override
    public void init() throws ServletException {
        System.out.println("USER FILTER INITIALIZED....");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {

            req.setAttribute("loginMessage", "Please login before accessing our products");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);

            return;
        }

        chain.doFilter(req, res);

    }

    @Override
    public void destroy() {
        System.out.println("HOME FILTER DESTROYED!!!!");
    }

}
