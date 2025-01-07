# SOA Project Setup Guide

This project consists of three Spring Boot applications:
- AuthorsAPI (Port 8081)
- BooksAPI (Port 8082)
- MainApp (Port 8080)

## Quick Start

1. Make the setup script executable:
```bash
chmod +x setup.sh
```

2. Run the setup script:
```bash
./setup.sh
```

3. Access the applications:
- MainApp: http://localhost:8080
- AuthorsAPI: http://localhost:8081
- BooksAPI: http://localhost:8082

## Default Credentials
- Username: admin
- Password: admin123

## Stopping the Services
To stop all services:
```bash
pkill -f 'spring-boot'
```

## Project Structure
```
SOA_Project/
├── AuthorsAPI/
├── BooksAPI/
└── MainApp/
```

## API Documentation
- AuthorsAPI Swagger UI: http://localhost:8081/swagger-ui.html
- BooksAPI Swagger UI: http://localhost:8082/swagger-ui.html

## Troubleshooting
If you encounter any issues:
1. Check if all services are running:
```bash
ps aux | grep spring-boot
```
2. Verify MySQL is running:
```bash
systemctl status mysql
```
3. Check application logs in the nohup.out files in each service directory