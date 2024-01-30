# Pluto

## Description

- Pluto is the web service that enables to interact with the Reporting API

## Installation

1. Clone the repository: `git clone https://github.com/utknl/pluto.git`
2. Navigate to the project directory: `cd project`
3. Install dependencies using the built-in mvn wrapper: `./mvnw install`
4. All commands below will also use the built-in mvn wrapper

## Usage

- To start the application, run the following command in the project directory:

```bash
./mvnw spring-boot:run
```

### Note
- For security purposes, baseUrl of the API is removed, please add the baseUrl to the `src/main/resources/application.yml` file
- Be sure to change the desired port on the `src/main/resources/application.yml` file

## Testing

- To run the unit tests, use the following command:

```bash
./mvnw test
```

## API Documentation

- This project uses Swagger UI for API documentation and testing. Once the application is running, you can access the
  Swagger UI at:

```bash
http://localhost:8791/swagger-ui/index.html
```

- Api documentation is also available at:

```bash 
http://localhost:8791/api-docs
```
