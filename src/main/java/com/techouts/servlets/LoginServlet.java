package com.techouts.servlets;

import java.io.IOException;

import com.techouts.dao.UserDAO;
import com.techouts.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("login servlet started............");
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        // resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("email: " + req.getParameter("email"));
        System.out.println("Password: " + req.getParameter("password"));

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = UserDAO.checkUserExistence(new User(email, password));

        if (user == null) {

            req.setAttribute("loginMessage", "Wrong credientials or user doesnot exists");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);

            return;

        }

        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(60 * 60);
        session.setAttribute("user", user);
        session.setAttribute("userCart", session);

        if(email.equals("admin@gmail.com") && password.equals("Admin@123")) {
            resp.sendRedirect(req.getContextPath() + "/admin");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/home");

    }
}
