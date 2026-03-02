<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
        <%@ taglib prefix="c" uri="jakarta.tags.core" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Your Orders - Lumina Store</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/order.css">
            </head>

            <body>

                <header class="site-header">
                    <div class="container">
                        <a href="${pageContext.request.contextPath}/home" class="logo">LUMINA</a>
                        <nav class="user-nav">
                            <a href="${pageContext.request.contextPath}/profile">Account</a>
                            <a href="${pageContext.request.contextPath}/order">Orders</a>
                            <a href="${pageContext.request.contextPath}/cart">Cart</a>
                        </nav>
                    </div>
                </header>

                <c:if test="${empty userOrderMap}">
                    <main class="empty-state-container">
                        <div class="empty-state-content">

                            <div class="empty-state-icon">
                                <svg width="60" height="60" viewBox="0 0 24 24" fill="none" stroke="#666666"
                                    stroke-width="1.5" stroke-linecap="square">
                                    <path
                                        d="M21 8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16Z" />
                                    <path d="m3.3 7 8.7 5 8.7-5" />
                                    <path d="M12 22V12" />
                                </svg>
                            </div>

                            <h1 class="empty-state-title">YOU HAVE NO<br>ORDERS YET</h1>
                            <p class="empty-state-subtitle">The pieces you love are waiting for you. Discover our latest
                                arrivals and tech essentials.</p>

                            <div class="empty-state-actions">
                                <a href="${pageContext.request.contextPath}/home" class="btn-solid">Shop Collection</a>
                                <a href="${pageContext.request.contextPath}/home#products" class="btn-outline">New
                                    Arrivals</a>
                            </div>

                        </div>
                    </main>
                </c:if>
                <c:if test="${!empty userOrderMap}">
                    <main class="orders-section">
                        <div class="page-header">
                            <h1>Order History</h1>
                        </div>

                        <c:forEach var="entry" items="${userOrderMap}">
                            <c:set var="order" value="${entry.key}" />
                            <c:set var="orderItems" value="${entry.value}" />

                            <div class="order-card">

                                <div class="order-meta">
                                    <div class="meta-group">
                                        <label>Order Placed</label>
                                        <span>${order.formattedOrderedDate}</span>
                                    </div>
                                    <div class="meta-group">
                                        <label>Total</label>
                                        <span>
                                            $
                                            <fmt:formatNumber value="${order.totalPrice}" type="number"
                                                minFractionDigits="2" maxFractionDigits="2" />
                                        </span>
                                    </div>
                                    <div class="meta-group">
                                        <label>Order #</label>
                                        <span>LM-4545${order.id}</span>
                                    </div>
                                    <div class="meta-group">
                                        <label>Status</label>
                                        <c:choose>
                                            <c:when test="${order.deliveryStatus == 'DELIVERED'}">
                                                <span
                                                    class="status-badge status-delivered">${order.deliveryStatus}</span>
                                            </c:when>
                                            <c:when test="${order.deliveryStatus == 'PROCESSING'}">
                                                <span
                                                    class="status-badge status-processing">${order.deliveryStatus}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-badge status-pending">${order.deliveryStatus}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                                <div class="order-items">
                                    <c:forEach var="item" items="${orderItems}">
                                        <div class="order-item">
                                            <img src="${item.productId.productImage}" alt="${item.productId.name}"
                                                class="item-image">
                                            <div class="item-info">
                                                <h3>${item.productId.name}</h3>
                                                <p>Quantity: ${item.quantity} • ${item.productId.productDescription}</p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                            </div>
                        </c:forEach>

                    </main>
                </c:if>


            </body>

            </html>