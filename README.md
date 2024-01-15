# Event Manager Application

The Event Manager Application is a Java Spring Boot application that allows users to manage and organize events. It uses an H2 in-memory database for data storage and provides API documentation via Swagger.

## Prerequisites

Before running the application, make sure you have the following prerequisites installed:
- Java 17
- Maven
- Docker (optional, for containerization)

## Getting Started

To run the application locally, follow these steps:
1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Build the application using Maven: `mvn clean install`.
4. Run the application: `java -jar target/event-manager.jar`.

If you prefer to run the application in a Docker container, you can build a Docker image using the provided Dockerfile and run a Docker container.

## Usage

Once the application is running, you can access the API documentation and interact with the endpoints using Swagger. The Swagger UI can be accessed at http://localhost:9090/swagger-ui/index.html (assuming the application is running on port 9090).

## Configuration

The application uses an H2 in-memory database by default. You can customize the database settings and other configuration options by modifying the application.properties file. 
DB URL:http://localhost:9090/h2
userName: sa
password: password

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
