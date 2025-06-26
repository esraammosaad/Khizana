<table>
  <tr>
    <td><h1>Khizana - M-Commerce Admin App</h1></td>
    <td>
      <img src="https://github.com/user-attachments/assets/80a8ac13-668f-409f-990d-3b64c1d18f69" alt="Khizana Logo" height="40"/>
    </td>
  </tr>
</table>

**Khizana** is an Android admin application built to manage an e-commerce store powered by the Shopify Admin API. It allows administrators to perform complete CRUD operations on products, variants, coupons, and inventory, as well as view sales analytics — all within a clean and modern Jetpack Compose interface.

---

## Features

### Authentication
- Admin login using Firebase Authentication.

### Overview & Analytics
- Dashboard displaying:
  - Total number of products.
  - Total number of inventory locations.
  - Total revenue in the last 7 days.
  - Total order count in the last 7 days (bar graph).

### Product Management
- Add, edit, delete, and view products.
- Upload product images using Cloudinary.
- Manage product variants and options (e.g., size, color).
- View detailed product information.
- Search products using Levenshtein distance algorithm for fuzzy matching.

### Inventory Management
- Edit inventory quantity and cost per product.
- Toggle inventory tracking.
- View total inventory across locations.

### Coupons & Discount Management
- Full CRUD support for price rules.
- Manage discount codes within each price rule.

---

## Tech Stack

| Category | Tools |
|---------|-------|
| Architecture | MVVM + Clean Architecture |
| Dependency Injection | Hilt |
| UI | Jetpack Compose, Navigation Component |
| API & Networking | Shopify Admin API, Retrofit, OkHttp (with caching), ConnectivityManager |
| Image Handling | Coil (for loading), Cloudinary (for uploading) |
| Authentication | Firebase Auth |
| Testing | JUnit, Mockito, Robolectric, Hamcrest, |
| Language | Kotlin |
| Utilities | Levenshtein distance algorithm for search |

---

## Testing

- 100% unit test coverage
- Tested modules:
  - ViewModels
  - Use Cases
  - Repositories
  - Utility classes (search, helpers, etc.)

---

## Overview about Khizana

https://github.com/user-attachments/assets/e019ab93-7987-4776-b3fa-3e4d4caa30ab


