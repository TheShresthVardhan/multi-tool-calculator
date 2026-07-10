# Multi-Tool Calculator

The Multi-Tool Calculator is a web-based project combining a standard calculator, BMI calculator, and unit converter in a single responsive interface. It features a Spring Boot backend delivering calculation APIs and a modern HTML frontend for user interaction. This project demonstrates modular arithmetic processing, health utility, and unit conversion features through a unified Java-based web service.

---

## Features

- **Standard Arithmetic Calculator**
  - Supports addition, subtraction, multiplication, and division.
- **BMI Calculator**
  - Calculates Body Mass Index (BMI) with dynamic unit support for both weight (kg/lb) and height (m/cm/in).
- **Unit Converter**
  - Converts between different supported units (details in source).
- **Responsive Web UI**
  - User-friendly HTML/CSS frontend with theme support and clear input forms.
- **RESTful API**
  - Exposes endpoints at `/api` for all core calculations.
- **Error Handling**
  - Handles invalid operations and division by zero with clear error messages.

---

## Usage

The application exposes its functionality through a web UI and REST API endpoints.

- Access the web interface via `calculator/src/main/resources/static/index.html`.
- The main backend endpoints are:
  - `/api/calculate` for arithmetic operations.
  - `/api/bmi` for BMI calculation.
  - `/api/convert` for unit conversion.

> [!TIP]
> You can interact with the APIs directly for integration or automation, or use the built-in HTML frontend.

---

## Installation

To install and run the application, use the Java and Maven tools as provisioned in the repository.

```steps
1. Install Java 26 | The project uses Java version 26 as specified in `calculator/pom.xml`.
2. Install Maven | Make sure Maven is available to build the project.
3. Clone Repository | Download the repository to your local machine.
4. Build the Project | Use Maven to build: `./mvnw clean package` from the `calculator` directory.
5. Run the Application | Start with `./mvnw spring-boot:run` or by running the generated JAR.
6. Access the UI | Open your browser to the running server's address (default port 8080) to use the calculator.
```

> [!NOTE]
> The `mvnw` script is included for environments without Maven pre-installed.

---

## Requirements

- Java 26 or higher
- Maven (or use included `mvnw`)
- Web browser for the frontend interface

---

## Configuration

- Application properties can be set in `calculator/src/main/resources/application.properties`.
- The default application name is set as `calculator`.

---

## API Endpoints

### Calculate Endpoint (`GET /api/calculate`)

```api
{
    "title": "Standard Calculation",
    "description": "Performs basic arithmetic operations (add, subtract, multiply, divide).",
    "method": "GET",
    "baseUrl": "http://localhost:8080",
    "endpoint": "/api/calculate",
    "headers": [],
    "queryParams": [
        {"key": "num1", "value": "First operand", "required": true},
        {"key": "num2", "value": "Second operand", "required": true},
        {"key": "operation", "value": "Operation (add|subtract|multiply|divide)", "required": true}
    ],
    "pathParams": [],
    "bodyType": "none",
    "requestBody": "",
    "formData": [],
    "responses": {
        "200": {
            "description": "Calculation result",
            "body": "{\n  \"result\": 8\n}"
        },
        "400": {
            "description": "Error or invalid operation",
            "body": "{\n  \"error\": \"Cannot divide by zero\"\n}"
        }
    }
}
```

### BMI Calculation Endpoint (`GET /api/bmi`)

```api
{
    "title": "BMI Calculation",
    "description": "Calculates BMI based on weight and height with units.",
    "method": "GET",
    "baseUrl": "http://localhost:8080",
    "endpoint": "/api/bmi",
    "headers": [],
    "queryParams": [
        {"key": "weight", "value": "Weight value", "required": true},
        {"key": "weightUnit", "value": "Weight unit (kg|lb)", "required": true},
        {"key": "height", "value": "Height value", "required": true},
        {"key": "heightUnit", "value": "Height unit (m|cm|in)", "required": true}
    ],
    "pathParams": [],
    "bodyType": "none",
    "requestBody": "",
    "formData": [],
    "responses": {
        "200": {
            "description": "BMI result",
            "body": "{\n  \"bmi\": 22.3\n}"
        },
        "400": {
            "description": "Error due to invalid input",
            "body": "{\n  \"error\": \"Height must be greater than zero\"\n}"
        }
    }
}
```

### Unit Conversion Endpoint (`GET /api/convert`)

```api
{
    "title": "Unit Conversion",
    "description": "Converts between supported units.",
    "method": "GET",
    "baseUrl": "http://localhost:8080",
    "endpoint": "/api/convert",
    "headers": [],
    "queryParams": [
        {"key": "value", "value": "Value to convert", "required": true},
        {"key": "fromUnit", "value": "Source unit", "required": true},
        {"key": "toUnit", "value": "Target unit", "required": true}
    ],
    "pathParams": [],
    "bodyType": "none",
    "requestBody": "",
    "formData": [],
    "responses": {
        "200": {
            "description": "Converted value",
            "body": "{\n  \"converted\": 2.54\n}"
        },
        "400": {
            "description": "Error for unsupported conversion",
            "body": "{\n  \"error\": \"Unsupported unit conversion\"\n}"
        }
    }
}
```

---

## Project Structure

- **Backend Source**
  - `calculator/src/main/java/com/example/calculator/CalculatorApplication.java`: Application entry point.
  - `calculator/src/main/java/com/example/calculator/CalculatorController.java`: REST API controller for calculator endpoints.
- **Frontend**
  - `calculator/src/main/resources/static/index.html`: HTML, CSS, and JS for the calculator interface.
- **Configuration**
  - `calculator/src/main/resources/application.properties`: Spring Boot configuration properties.
- **Build & Dependency**
  - `calculator/pom.xml`: Maven build and dependencies file.
  - `calculator/mvnw`: Maven wrapper script.
- **Testing**
  - `calculator/src/test/java/com/example/calculator/CalculatorApplicationTests.java`: Spring Boot test class.

---

## License

This project is licensed under the MIT License. See `calculator/LICENSE` for details.

> [!IMPORTANT]
> You are free to use, modify, and distribute this software under the terms of the MIT License.

---

## Contributing

Contributions are welcome! Fork the repository and submit a pull request.

- Please include clear commit messages and relevant test coverage.
- By contributing, you agree that your code is licensed under the MIT License.

---

## Acknowledgments

- Built with [Spring Boot](https://spring.io/projects/spring-boot).
- Project structure and dependencies are managed via Maven.

---

> [!NOTE]
> For extended documentation, review the source files under `calculator/src/main/java/com/example/calculator/` and the frontend code in `calculator/src/main/resources/static/index.html`.