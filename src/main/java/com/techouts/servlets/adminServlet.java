package com.techouts.servlets;

import java.io.IOException;
import java.util.List;

import com.techouts.dao.ProductDAO;
import com.techouts.entities.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/*")
public class adminServlet extends HttpServlet {

    private void diplayAllProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> productsList = ProductDAO.getProductsList();

        req.setAttribute("productList", productsList);

        req.getRequestDispatcher("/jsp/admin/allproducts.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();

        if("/addproduct".equals(path)) {

            req.getRequestDispatcher("/jsp/admin/addproducts.jsp").forward(req, resp);

        } else if (path == null) {

            diplayAllProducts(req, resp);

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("productName");
        float price = Float.parseFloat(req.getParameter("productPrice"));
        String description = req.getParameter("productDescription");
        String category = req.getParameter("productCategory");
        String productImage = req.getParameter("productImage");

        boolean productInsertionStatus = ProductDAO.addProduct(name, category, price, description, productImage);

        resp.setContentType("text/plain");
        resp.getWriter().write(productInsertionStatus ? "success" : "failed");

    }

}