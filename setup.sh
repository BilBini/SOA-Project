#!/bin/bash

# Exit on any error
set -e

echo "🚀 Starting SOA Project Setup..."

# Install Java if not present
if ! command -v java &> /dev/null; then
    echo "Installing Java..."
    sudo apt-get update
    sudo apt-get install -y openjdk-11-jdk
fi

# Install Maven if not present
if ! command -v mvn &> /dev/null; then
    echo "Installing Maven..."
    sudo apt-get install -y maven
fi

# Create project directories and data files
mkdir -p SOA_Project/{AuthorsAPI,BooksAPI,MainApp}
mkdir -p SOA_Project/data

# Create initial JSON data files
echo '{"authors": []}' > SOA_Project/data/authors.json
echo '{"books": []}' > SOA_Project/data/books.json

echo "✅ Setup completed successfully!"
echo "
Data files have been created.
Next steps:
1. Build and deploy each service:
   cd SOA_Project/[ServiceName]
   mvn clean package
   java -jar target/[service-name]-1.0.0.jar

2. Access the services at:
   - MainApp: http://localhost:8080
   - AuthorsAPI: http://localhost:8081
   - BooksAPI: http://localhost:8082

Default credentials for MainApp:
Username: admin
Password: admin123
"