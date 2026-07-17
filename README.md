# 🧮 Multi-Tool Calculator

The **Multi-Tool Calculator** is a web-based project combining a standard calculator, BMI calculator, and unit converter in a single responsive interface. It features a Spring Boot backend 🚀 delivering calculation APIs and a modern HTML frontend 🎨 for user interaction.

---

## 📁 Project Structure

```
calculator/
├── src/main/java/com/example/calculator/
│   ├── CalculatorApplication.java         🚀 Entry point
│   ├── CalculatorController.java          🎮 REST controller
│   ├── CalculateResponse.java             📤 Arithmetic DTO
│   ├── BmiResponse.java                   📤 BMI DTO
│   ├── ConvertResponse.java               📤 Conversion DTO
│   └── service/
│       ├── CalculatorService.java         ➗ Arithmetic logic
│       ├── BmiService.java                ⚖️ BMI calculation
│       └── ConversionService.java         🔄 Conversion + 💰 currency
├── src/main/resources/
│   ├── static/index.html                  🎨 Frontend UI
│   └── application.properties             ⚙️ Configuration
├── src/test/java/com/example/calculator/
│   ├── CalculatorApplicationTests.java    ✅ Context test
│   └── service/
│       ├── CalculatorServiceTest.java     ✅ Arithmetic tests
│       └── BmiServiceTest.java            ✅ BMI tests
└── pom.xml                                📦 Maven build
```

---

## ✨ Features

- **➕ Standard Arithmetic Calculator** — add, subtract, multiply, divide
- **⚖️ BMI Calculator** — calculates BMI with unit support (kg/lb, m/cm/in) and weight category classification
- **🔄 Unit Converter** — temperature, length, weight, and currency (with live API + offline fallback)
- **🎨 Responsive Web UI** — clean interface with dark mode 🌓 theme toggle
- **🧩 RESTful API** — endpoints at `/api` for all core calculations
- **🛡️ Error Handling** — clear messages for invalid operations, division by zero, and bad inputs
- **📦 Service Layer** — business logic extracted into testable service classes
- **💰 Configurable Currency Rates** — offline rates set in `application.properties`

---

## 🚀 Usage

The application exposes its functionality through a web UI and REST API endpoints.

- Open `calculator/src/main/resources/static/index.html` in your browser 🌐
- Backend endpoints:
  - `/api/calculate` — arithmetic operations
  - `/api/bmi` — BMI calculation
  - `/api/convert` — unit conversion

> [!TIP]
> You can interact with the APIs directly for integration or automation, or use the built-in HTML frontend.

---

## 🛠️ Installation

```steps
1. ☕ Install Java 21 — The project uses Java version 21 as specified in `calculator/pom.xml`.
2. 📦 Install Maven — Make sure Maven is available to build the project.
3. 📂 Clone Repository — Download the repository to your local machine.
4. 🔨 Build the Project — `./mvnw clean package` from the `calculator` directory.
5. ▶️ Run the Application — `./mvnw spring-boot:run` or run the generated JAR.
6. 🌐 Access the UI — Open your browser to `http://localhost:8080`.
```

> [!NOTE]
> The `mvnw` script is included for environments without Maven pre-installed.

---

## ⚙️ Requirements

- ☕ Java 21 or higher
- 📦 Maven (or use included `mvnw`)
- 🌐 Web browser for the frontend interface

---

## 🔧 Configuration

- Edit `calculator/src/main/resources/application.properties` to customize:
  - `server.port` — change the default port (default: `8080`)
  - `conversion.currency.usd.*` — offline exchange rates for currency conversion (14 currencies supported)

---

## 📡 API Endpoints

### ➕ Calculate (`GET /api/calculate`)

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
    "responses": {
        "200": { "description": "Calculation result", "body": "{\n  \"result\": 8\n}" },
        "400": { "description": "Error or invalid operation", "body": "{\n  \"error\": \"Cannot divide by zero\"\n}" }
    }
}
```

### ⚖️ BMI Calculation (`GET /api/bmi`)

```api
{
    "title": "BMI Calculation",
    "description": "Calculates BMI based on weight and height with units.",
    "method": "GET",
    "baseUrl": "http://localhost:8080",
    "endpoint": "/api/bmi",
    "queryParams": [
        {"key": "weight", "value": "Weight value", "required": true},
        {"key": "weightUnit", "value": "Weight unit (kg|lb)", "required": true},
        {"key": "height", "value": "Height value", "required": true},
        {"key": "heightUnit", "value": "Height unit (m|cm|in)", "required": true}
    ],
    "responses": {
        "200": { "description": "BMI result", "body": "{\n  \"result\": 22.5,\n  \"category\": \"Normal weight\"\n}" },
        "400": { "description": "Error due to invalid input", "body": "{\n  \"error\": \"Invalid height or weight\"\n}" }
    }
}
```

### 🔄 Unit Conversion (`GET /api/convert`)

```api
{
    "title": "Unit Conversion",
    "description": "Converts between supported units (temperature, length, weight, currency).",
    "method": "GET",
    "baseUrl": "http://localhost:8080",
    "endpoint": "/api/convert",
    "queryParams": [
        {"key": "value", "value": "Value to convert", "required": true},
        {"key": "category", "value": "Conversion category (temperature|length|weight|currency)", "required": true},
        {"key": "fromUnit", "value": "Source unit", "required": true},
        {"key": "toUnit", "value": "Target unit", "required": true}
    ],
    "responses": {
        "200": { "description": "Converted value", "body": "{\n  \"result\": 2.54\n}" },
        "400": { "description": "Error for unsupported conversion", "body": "{\n  \"error\": \"Conversion failed\"\n}" }
    }
}
```

## 📄 License

This project is licensed under the MIT License. See [LICENSE](LICENSE) at the repository root for details.

> [!IMPORTANT]
> You are free to use, modify, and distribute this software under the terms of the MIT License.

---

## 🤝 Contributing

Contributions are welcome! Fork the repository and submit a pull request.

- Please include clear commit messages and relevant test coverage.
- By contributing, you agree that your code is licensed under the MIT License.

---

## 🙏 Acknowledgments

- Built with [Spring Boot](https://spring.io/projects/spring-boot) 🚀
- Project structure and dependencies are managed via Maven 📦
- Exchange rates powered by [open.er-api.com](https://open.er-api.com) 🌐
