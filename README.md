# SocialMeli

## Descripción del problema
Las redes sociales actuales no ofrecen una experiencia optimizada para la interacción entre vendedores y compradores en un entorno estructurado. Los usuarios tienen dificultades para seguir las actividades de los vendedores, organizar sus publicaciones y obtener información relevante de manera eficiente.

## Definiciones de Equipo

## Solución
Crear una aplicación que permita a los usuarios realizar acciones clave, como seguir o dejar de seguir a vendedores, gestionar publicaciones de productos (incluyendo promociones) y acceder a estadísticas detalladas de sus interacciones. Además, ofrecer opciones de filtrado y ordenamiento para mejorar la experiencia de usuario.

## Tecnologías Utilizadas

- **IntelliJ IDEA:** Entorno de desarrollo integrado (IDE) altamente potente y versátil desarrollado por JetBrains. Ofrece herramientas avanzadas para la creación, depuración y gestión de proyectos Java, facilitando el desarrollo de software con una interfaz intuitiva y funciones inteligentes.

- **Java 21:** Lenguaje de programación versátil y robusto que forma la base del desarrollo de la API.

- **Spring Boot 3.4.0:** Framework que simplifica el desarrollo de aplicaciones Java, proporcionando configuraciones predeterminadas y una estructura fácil de usar.

- **Maven:** Herramienta de automatización de compilación y gestión de dependencias que facilita el desarrollo y la gestión del proyecto.

- **Spring Web:** Módulos de Spring que permiten el desarrollo de aplicaciones web de manera eficiente.

- **Git y GitHub:** Herramientas de control de versiones y plataforma de alojamiento que facilitan la colaboración y el seguimiento del desarrollo del proyecto.

- **Postman:** Plataforma para el desarrollo y prueba de API's, facilitando la validación de endpoints y la interacción con la API.

## Diagrama de Clases

El **Diagrama de Clases** representa las clases y las relaciones entre ellas en el sistema. Muestra la estructura estática del sistema, incluyendo las clases, sus atributos, métodos y cómo se relacionan. Este diagrama proporciona una visión general de la arquitectura y la organización de clases en el código fuente.

![Diagrama de Clases](/src/main/resources/static/Sprint%20N°%201.png)

## Bonus

### 12. Historia de Usuario

**Como** usuario de la API, **quiero** buscar y filtrar productos basados en atributos como `category`, `price`, `hasPromo` y `discount`, **para** encontrar fácilmente los productos que cumplan con mis criterios específicos. El buscador debe permitir filtros combinados y ordenamiento por precio o descuento.


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

## Integrantes

| Integrante                | País      |
|--------------------------|-----------|
| Santiago Marino          | Argentina 🇦🇷 |
| Gianluca Panigatti      | Argentina 🇦🇷 |
| Eilin Dianella Restrepo  | Colombia 🇨🇴  |
| Santiago Montoya         | Colombia 🇨🇴  |
| Bryan Felipe Munoz       | Colombia 🇨🇴  |
| Karina Sierra            | Colombia 🇨🇴  |