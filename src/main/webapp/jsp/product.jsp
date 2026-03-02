<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
        <%@ taglib prefix="c" uri="jakarta.tags.core" %>


            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Lumina Pro Laptop - Lumina Store</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/product.css">
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

                <main class="product-section">

                    <div class="product-grid">

                        <div class="image-stack">
                            <img src="${requestScope.productDetail.productImage}" alt="Lumina Pro Laptop Front">
                        </div>

                        <div class="product-details">
                            <h1 class="product-title">${requestScope.productDetail.name}</h1>
                            <span class="product-price">
                                <fmt:formatNumber value="${requestScope.productDetail.price}" type="number"
                                    minFractionDigits="2" maxFractionDigits="2" />
                            </span>

                            <p class="product-description">
                                ${requestScope.productDetail.productDescription}
                            </p>

                            <form class="add-item-to-cart">
                                <input type="hidden" name="productId" value="${requestScope.productDetail.id}">
                                <button type="submit" class="btn-primary">Add to Cart</button>
                            </form>

                        </div>
                    </div>
                </main>

                <script>
                    document.querySelectorAll('.add-item-to-cart').forEach(form => {
                        form.addEventListener('submit', function (e) {
                            e.preventDefault();

                            const formData = new FormData(this);
                            const params = new URLSearchParams(formData).toString();

                            fetch('${pageContext.request.contextPath}/cart/add', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                                body: params
                            })
                                .then(response => response.text())
                                .then(status => {
                                    if (status === 'success') {
                                        alert('Product added to cart!');
                                    } else {
                                        alert('Failed to add product.');
                                    }
                                })
                                .catch(err => console.error(err));
                        });
                    });
                </script>
            </body>

            </html>