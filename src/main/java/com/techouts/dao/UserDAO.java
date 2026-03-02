package com.techouts.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.techouts.entities.Cart;
import com.techouts.entities.User;
import com.techouts.utils.HashPasswordUtil;
import com.techouts.utils.hibernateutil.HibernateUtil;

public class UserDAO {

    public static User checkUserExistence(User user) {

        try (Session session = HibernateUtil.getHibernateSession()) {

            String hqlString = "FROM User WHERE email = :email AND password = :password";

            Query<User> query = session.createQuery(hqlString, User.class);
            query.setParameter("email", user.getEmail());

            String hashedPassword = HashPasswordUtil.getHashedPassword(user.getPassword());
            query.setParameter("password", hashedPassword);

            User currUser = query.uniqueResult(); // using this since I made email attribute unique

            return currUser;

        } catch (Exception e) {

            System.err.println(e);

        }
        return user;

    }

    public static boolean registerUser(User user) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getHibernateSession()) {

            Cart userCart = new Cart();
            user.setCart(userCart);
            userCart.setUserId(user);

            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();
            System.err.println("Cannot persist user to DB");

        }

        return false;

    }

    public static boolean checkEmailExistence(String email) {

        try (Session session = HibernateUtil.getHibernateSession()) {

            String hqlString = "FROM User WHERE email = :email";

            Query<User> query = session.createQuery(hqlString, User.class);
            query.setParameter("email", email);

            List<User> userList = query.list();

            return !userList.isEmpty();

        } catch (Exception e) {

            System.err.println(e);

        }

        return false;
    }

}
