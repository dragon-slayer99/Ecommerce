<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
        <%@ taglib prefix="c" uri="jakarta.tags.core" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Lumina Store - Premium E-Commerce</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home.css">
            </head>

            <body>

                <!-- HEADER -->
                <header class="site-header">
                    <div class="container header-content">
                        <a href="${pageContext.request.contextPath}/home" class="logo">LUMINA</a>

                        <div class="search-bar">
                            <input type="text" placeholder="Search for anything...">
                            <button type="submit">Search</button>
                        </div>

                        <nav class="user-nav">
                            <a href="${pageContext.request.contextPath}/profile">Account</a>
                            <a href="${pageContext.request.contextPath}/order">Orders</a>
                            <a href="${pageContext.request.contextPath}/cart">Cart</a>
                        </nav>
                    </div>
                </header>

                <!-- MARQUEE BANNER -->
                <div class="marquee-container">
                    <div class="marquee-track">
                        <!-- Set 1 -->
                        <span class="marquee-item">Free Worldwide Shipping on Orders Over $150</span>
                        <span class="marquee-sep">•</span>
                        <span class="marquee-item">New Minimalist Collection Drops Friday</span>
                        <span class="marquee-sep">•</span>
                        <span class="marquee-item">Subscribe for 15% Off Your First Order</span>
                        <span class="marquee-sep">•</span>

                        <!-- Set 2 -->
                        <span class="marquee-item">Free Worldwide Shipping on Orders Over $150</span>
                        <span class="marquee-sep">•</span>
                        <span class="marquee-item">New Minimalist Collection Drops Friday</span>
                        <span class="marquee-sep">•</span>
                        <span class="marquee-item">Subscribe for 15% Off Your First Order</span>
                        <span class="marquee-sep">•</span>
                    </div>
                </div>

                <!-- HERO SECTION -->
                <section class="hero">
                    <h1 class="hero-title">Elevate Your<br>Everyday</h1>

                    <p class="hero-subtitle">
                        Discover thoughtfully curated pieces designed for modern living.
                        Minimalist aesthetics meets uncompromising quality.
                    </p>

                    <div class="hero-actions">
                        <a href="#products" class="btn btn-primary">Browse Products</a>
                    </div>
                </section>

                <!-- PRODUCTS -->
                <section class="products-section" id="products">
                    <section class="page-header">
                        <h1>All Products</h1>
                        <p>Discover our complete collection of minimalist essentials, meticulously crafted for your
                            everyday
                            life.</p>

                        <div class="category-filters">
                            <button class="filter-btn active">All</button>
                            <button class="filter-btn">Electronics</button>
                            <button class="filter-btn">Footwear</button>
                            <button class="filter-btn">Apparel</button>
                            <button class="filter-btn">Home</button>
                        </div>
                    </section>

                    <div class="toolbar">
                        <span class="item-count">Showing ${fn:length(productsList)} products</span>
                        <div class="sort-options">
                            <label for="sort">Sort by: </label>
                            <select id="sort" class="sort-dropdown">
                                <option value="featured">Featured</option>
                                <option value="price-low">Price: Low to High</option>
                                <option value="price-high">Price: High to Low</option>
                                <option value="newest">Newest Arrivals</option>
                            </select>
                        </div>
                    </div>

                    <main class="products-container">
                        <div class="product-grid">

                            <c:forEach var="product" items="${productsList}">
                                <div class="product-card">
                                    <div class="image-wrapper">
                                        <img src="${product.productImage}" alt="${product.category}">
                                    </div>
                                    <div class="product-info">
                                        <span class="category">${product.category}</span>
                                        <h3><a href="${pageContext.request.contextPath}/product?id=${product.id}">${product.name}</a></h3>
                                        <span class="price">$${product.price}</span>
                                        <form class="add-item-to-cart">
                                            <input type="hidden" name="productId" value="${product.id}">
                                            <button type="submit" class="btn-secondary">Add to Cart</button>
                                        </form>
                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                    </main>
                </section>

                <!-- FOOTER -->
                <footer>
                    <div class="footer-bottom">
                        <p>&copy; 2026 Lumina Store. All rights reserved.</p>
                    </div>
                </footer>

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