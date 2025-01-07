# SOA Project Setup Guide

This project consists of three Spring Boot applications:
- AuthorsAPI (Port 8081)
- BooksAPI (Port 8082) 
- MainApp (Port 8080)

## Quick Start

### 1. Build and Run AuthorsAPI
```bash
cd SOA_Project/AuthorsAPI
mvn clean package
mvn spring-boot:run
```

### 2. Build and Run BooksAPI
```bash
cd SOA_Project/BooksAPI  
mvn clean package
mvn spring-boot:run
```

### 3. Build and Run MainApp
```bash
cd SOA_Project/MainApp
mvn clean package
mvn spring-boot:run
```

### 4. Access Applications
- MainApp: http://localhost:8080
- AuthorsAPI: http://localhost:8081
- BooksAPI: http://localhost:8082

## Default Credentials
- Username: admin
- Password: admin123

## API Documentation
- AuthorsAPI Swagger UI: http://localhost:8081/swagger-ui.html
- BooksAPI Swagger UI: http://localhost:8082/swagger-ui.html

## Project Structure
```
SOA_Project/
├── AuthorsAPI/
├── BooksAPI/
└── MainApp/
```

## Stopping Services
```bash
pkill -f 'spring-boot'
```

## Troubleshooting
1. Check running services:
```bash
ps aux | grep spring-boot
```
2. View application logs:
```bash
tail -f SOA_Project/*/nohup.out
```
