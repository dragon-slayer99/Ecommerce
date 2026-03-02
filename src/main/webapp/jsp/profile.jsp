<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
        <%@ taglib prefix="c" uri="jakarta.tags.core" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>My Account - Lumina Store</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/profile.css">
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

                <main class="profile-section">
                    <div class="profile-header">
                        <h1>My Account</h1>
                    </div>

                    <div class="profile-grid">

                        <aside class="profile-sidebar">
                            <div class="avatar">
                                ${fn:substring(sessionScope.user.name, 0, 1)}
                            </div>
                            <h2>${sessionScope.user.name}</h2>
                            <p>Member since ${sessionScope.user.formattedJoinedDate}</p>

                            <a href="${pageContext.request.contextPath}/logout" class="btn-primary">
                                Log Out
                            </a>
                        </aside>

                        <section class="profile-details-card">
                            <h3>Personal Information</h3>

                            <div class="detail-grid">
                                <div class="detail-item">
                                    <label>Full Name</label>
                                    <p>${sessionScope.user.name}</p>
                                </div>

                                <div class="detail-item">
                                    <label>Email Address</label>
                                    <p>${sessionScope.user.email}</p>
                                </div>

                            </div>
                        </section>

                    </div>
                </main>

                <footer>
                    <div class="footer-bottom">
                        <p>&copy; 2026 Lumina Store. All rights reserved.</p>
                    </div>
                </footer>

            </body>

            </html>