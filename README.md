# API Documentation

## Endpoints

### 01. Follow a User
- **Method:** POST
- **URL:** `http://localhost:8080/users/1/follow/9`

---

### 02. Get Follower Count
- **Method:** GET
- **URL:** `http://localhost:8080/users/1/followers/count`

---

### 03. List Followers of a Seller
- **Method:** GET
- **URL:** `http://localhost:8080/users/1/followers/list`

---

### 04. List Followed Sellers by a User
- **Method:** GET
- **URL:** `http://localhost:8080/users/1/followed/list`

---

### 05. Create a New Post
- **Method:** POST
- **URL:** `http://localhost:8080/products/post`
- **Body:**
```json
{
    "user_id": 1,
    "date": "29-04-2025",
    "product": {
        "product_id": 1,
        "product_name": "Silla Gamer",
        "type": "Gamer",
        "brand": "Racer",
        "color": "Red & Black",
        "notes": "Special Edition"
    },
    "category": 100,
    "price": 1500.50
}
```

---

### 06. Get Recent Posts by Followed Sellers
- **Method:** GET
- **URL:** `http://localhost:8080/products/followed/1/list`

---

### 07. Unfollow a Seller
- **Method:** POST
- **URL:** `http://localhost:8080/users/1/unfollow/4`

---

### 08. Followers Sorting Options
#### 08.1 Ascending Order by Name
- **Method:** GET
- **URL:** `http://localhost:8080/users/1/followers/list?order=name_asc`

#### 08.2 Descending Order by Name
- **Method:** GET
- **URL:** `http://localhost:8080/users/1/followers/list?order=name_desc`

#### 08.3 Ascending Order of Followed Sellers by Name
- **Method:** GET
- **URL:** `http://localhost:8080/users/1/followed/list?order=name_asc`

#### 08.4 Descending Order of Followed Sellers by Name
- **Method:** GET
- **URL:** `http://localhost:8080/users/1/followed/list?order=name_desc`

---

### 09. Post Sorting Options
#### 09.1 Ascending Order by Date
- **Method:** GET
- **URL:** `http://localhost:8080/products/followed/1/list?order=date_asc`

#### 09.2 Descending Order by Date
- **Method:** GET
- **URL:** `http://localhost:8080/products/followed/1/list?order=date_desc`

---

### 10. Create a Promotional Post
- **Method:** POST
- **URL:** `http://localhost:8080/products/promo-post`
- **Body:**
```json
{
    "user_id": 1,
    "date": "29-04-2021",
    "product": {
        "product_id": 1,
        "product_name": "Silla Gamer",
        "type": "Gamer",
        "brand": "Racer",
        "color": "Red & Black",
        "notes": "Special Edition"
    },
    "category": 100,
    "price": 1500.50,
    "has_promo": true,
    "discount": 0.25
}
```

---

### 11. Get Promotional Product Count by Seller
- **Method:** GET
- **URL:** `http://localhost:8080/products/promo-post/count?user_id=1`
