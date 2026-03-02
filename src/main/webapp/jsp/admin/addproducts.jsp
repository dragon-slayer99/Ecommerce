<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin: Add Product - Lumina Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/addproducts.css">
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
            <h1>Add New Product</h1>
            <a href="${pageContext.request.contextPath}/admin" class="back-link">Back to Product List</a>
        </div>

        <div class="form-card">
            <form action="${pageContext.request.contextPath}/admin" method="POST">

                <div class="form-grid">

                    <div class="form-group full-width">
                        <label for="productName">Product Name</label>
                        <input type="text" id="productName" name="productName" placeholder="e.g. Lumina Pro Laptop"
                            required>
                    </div>

                    <div class="form-group">
                        <label for="productCategory">Category</label>
                        <select id="productCategory" name="productCategory" required>
                            <option value="" disabled selected>Select a category</option>
                            <option value="ELECTRONICS">Electronics</option>
                            <option value="FASHION">Fashion</option>
                            <option value="HOME_GARDEN">Home Ganden</option>
                            <option value="HEALTH_BEAUTY">Health beauty</option>
                            <option value="TOYS_KIDS">Toys Kids</option>
                            <option value="SPORTS_OUTDOORS">Sports Outdoors</option>
                            <option value="GROCERIES">Groceries</option>
                            <option value="BOOKS_MEDIA">Books</option>
                            <option value="OTHERS">Others</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="productPrice">Price</label>
                        <div class="price-wrapper">
                            <span>$</span>
                            <input type="number" id="productPrice" name="productPrice" placeholder="0.00" step="0.01"
                                min="0" required>
                        </div>
                    </div>

                    <div class="form-group full-width">
                        <label for="productDescription">Product Description</label>
                        <textarea id="productDescription" name="productDescription"
                            placeholder="Write a detailed description of the product..." required></textarea>
                    </div>


                    <div class="form-group full-width">
                        <label for="productName">Product Image URL</label>
                        <input type="text" id="productName" name="productImage" placeholder="e.g. https://e2dw3-e3d33d-v35r4f4.png"
                            required>
                    </div>

                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-primary">Save Product</button>
                </div>

            </form>
        </div>

    </main>

</body>

</html>