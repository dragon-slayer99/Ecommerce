package com.techouts.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.techouts.entities.Product;
import com.techouts.utils.enums.Category;
import com.techouts.utils.hibernateutil.HibernateUtil;


public class ProductDAO {

    public static boolean addProduct(String name, String category, float price, String productDescription,
            String productImage) {

        if (name == null || category == null || productDescription == null || productImage == null) {
            return false;
        }

        Transaction tx = null;

        try (Session session = HibernateUtil.getHibernateSession()) {

            Category productCategory = Category.valueOf(category.toUpperCase());

            String hql = "FROM Product WHERE name = :name AND price = :price AND productDescription = :productDescription AND category = :category";

            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("name", name);
            query.setParameter("price", price);
            query.setParameter("productDescription", productDescription);
            query.setParameter("category", productCategory);

            if(!query.getResultList().isEmpty()) {
                return false;
            }   

            tx = session.beginTransaction();

            Product product = new Product(name, price, productDescription, productCategory, productImage);

            session.persist(product);

            tx.commit();

            return true;

        } catch (Exception e) {
            if(tx != null) tx.rollback();
            System.err.println(e);
        }

        return false;

    }

    public static List<Product> getProductsList() {

        String hql = "FROM Product";

        try (Session session = HibernateUtil.getHibernateSession()) {

            Query<Product> query = session.createQuery(hql, Product.class);

            return query.setCacheable(true).list();

        } catch (Exception e) {
            System.err.println(e);
        }

        return new ArrayList<>();

    }

    public static Product getProductById(int id) {

        try (Session session = HibernateUtil.getHibernateSession()) {

            Product product = session.get(Product.class, id);

            return product;

        } catch (Exception e) {

            System.err.println(e);

        }

        return null;

    }

}
