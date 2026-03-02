package com.techouts.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.techouts.utils.logging.BaseHibernateLogger;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseHibernateLogger {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "joined_date")
    private LocalDate joinedDate;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
    private Cart cart;

    public User() {
    }

    public User(String name, String email, String password) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.joinedDate = LocalDate.now();
    }

    public User(String email, String password) {

        this.email = email;
        this.password = password;
    }

    public String getFormattedJoinedDate() {
        if (joinedDate == null)
            return "";
        return joinedDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

}
