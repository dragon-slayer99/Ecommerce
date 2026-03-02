package com.techouts.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.techouts.utils.enums.DeliveryStatus;
import com.techouts.utils.logging.BaseHibernateLogger;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends BaseHibernateLogger {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus = DeliveryStatus.PROCESSING;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "delivery_date")
    private LocalDate estimatedDeliveryDate;

    @Column(name = "ordered_date")
    private LocalDate orderedDate;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "delivery_address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(User userId, float totalPrice, LocalDate estimatedDeliveryDate, String paymentType, String address) {

        this.userId = userId;
        this.totalPrice = totalPrice;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.paymentType = paymentType;
        this.address = address;
        this.orderedDate = LocalDate.now();

    }

    public String getFormattedOrderedDate() {
        if (orderedDate == null)
            return "";
        return orderedDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus status) {
        this.deliveryStatus = status;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public LocalDate getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(LocalDate orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
