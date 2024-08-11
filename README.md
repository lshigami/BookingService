# Homestay Reservation System
This project is a backend system for a homestay reservation platform, similar to Agoda, Booking.com, and Airbnb. It provides authentication and authorization, homestay search functionality, and booking management, ensuring secure and efficient reservations.
## Features
- **Authentication** and Authorization: Secure login and access control using JWT (JSON Web Tokens).
- **Homestay Search**: Search for buildings based on user requirements.
- **Nearby Homestay Search**: Locate homestays in proximity using PostGIS.
- **Booking Management**: Book homestays with transaction management and race condition handling.
## Tech Stack
- **Java Spring Boot**: Framework for building the application.
- **Spring Data JPA**: Data access layer for interaction with the database.
- **Spring Security**: Security framework for authentication and authorization.
- **JWT (JSON Web Tokens)**: Token-based authentication.
- **PostgreSQL**: Database management system.
- **PostGIS**: Spatial database extender for PostgreSQL.
## Getting Started
Prerequisites
- **Java 17**
- **Spring boot 3**
- **Maven**
- **PostgreSQL**
- **PostGis**
Installation
1. Clone the repository:
```
git clone https://github.com/lshigami/BookingService.git
git cd project
```
2. Build and run the application:
```
mvn clean install
mvn spring-boot:run
```
3. Usgae : 
**API DOCUMENT** : https://documenter.getpostman.com/view/28665820/2sA3kUG2Yc

