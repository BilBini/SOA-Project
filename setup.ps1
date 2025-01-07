# Stop on first error
$ErrorActionPreference = "Stop"

Write-Host "🚀 Starting SOA Project Setup..."

# Check and install Java if not present
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    Write-Host "Java not found. Please install Java 11 or later from:"
    Write-Host "https://adoptium.net/temurin/releases/"
    exit 1
}

# Check and install Maven if not present
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    Write-Host "Maven not found. Please install Maven from:"
    Write-Host "https://maven.apache.org/download.cgi"
    exit 1
}

# Create project directories
$directories = @(
    "SOA_Project/AuthorsAPI",
    "SOA_Project/BooksAPI", 
    "SOA_Project/MainApp",
    "SOA_Project/data"
)

foreach ($dir in $directories) {
    New-Item -ItemType Directory -Path $dir -Force | Out-Null
}

# Create initial JSON data files
@{ authors = @() } | ConvertTo-Json | Set-Content -Path "SOA_Project/data/authors.json"
@{ books = @() } | ConvertTo-Json | Set-Content -Path "SOA_Project/data/books.json"

Write-Host "✅ Setup completed successfully!"
Write-Host @"

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
"@