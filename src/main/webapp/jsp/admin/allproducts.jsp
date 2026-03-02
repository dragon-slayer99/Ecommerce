<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
        <%@ taglib prefix="c" uri="jakarta.tags.core" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Admin: Manage Products - Lumina Store</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/allproducts.css">
            </head>

            <body>

                <header class="site-header">
                    <div class="container">
                        <a href="#" class="logo">LUMINA <span>Admin</span></a>
                        <nav class="user-nav">
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </nav>
                    </div>
                </header>

                <main class="admin-section">

                    <div class="admin-header">
                        <h1>Manage Products</h1>
                        <form action="${pageContext.request.contextPath}/admin/addproduct" method="get">
                            <button type="submit" class="btn-primary">+ Add New Product</button>
                        </form>
                    </div>

                    <div class="table-container">
                        <table class="product-table">
                            <thead>
                                <tr>
                                    <th>Product ID</th>
                                    <th class="thumb-cell">Image</th>
                                    <th>Name</th>
                                    <th>Category</th>
                                    <th>Price</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach var="product" items="${requestScope.productList}">

                                    <tr>
                                        <td><span class="id-badge">LUM-${product.id}</span></td>
                                        <td class="thumb-cell">
                                            <img src="${product.productImage}" alt="Sneakers" class="product-thumb">
                                        </td>
                                        <td class="product-name">${product.name}</td>
                                        <td><span class="category-pill">${product.category}</span></td>
                                        <td class="product-price">$
                                            <fmt:formatNumber value="${product.price}" type="number"
                                                minFractionDigits="2" maxFractionDigits="2" />
                                        </td>
                                        <td>
                                            <form class="action-links">
                                                <input type="hidden" name="productId" value="${product.id}">
                                                <button type="submit" class="btn-delete">Delete</button>
                                            </form>
                                        </td>
                                    </tr>

                                </c:forEach>

                            </tbody>
                        </table>
                    </div>

                </main>
                <script>
                    document.querySelectorAll('.action-links').forEach(form => {
                        form.addEventListener('submit', function (e) {
                            e.preventDefault();

                            const formData = new FormData(this);
                            const params = new URLSearchParams(formData).toString();

                            fetch('${pageContext.request.contextPath}/admin/delete', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                                body: params
                            })
                                .then(response => {
                                    if (response.ok) {
                                        location.reload();
                                    }
                                })
                                .catch(err => console.error(err));
                        });
                    });
                </script>
            </body>

            </html>