package com.example.calculator;

/*
 * PACKAGE: com.example.calculator
 *
 * IMPORTS: Spring Web annotations, RestTemplate for external calls, and utility maps
 */
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

/*
 * CONTROLLER: CalculatorController
 * Base path: /api
 * Description: Exposes endpoints for standard calculations, BMI, and unit conversions
 */
@RestController
@RequestMapping("/api")
public class CalculatorController {

    /*
     * ENDPOINT: /api/calculate
     * Purpose: Standard arithmetic operations (add, subtract, multiply, divide)
     */
    @GetMapping("/calculate")
    public Map<String, Object> calculate(
            @RequestParam double num1,
            @RequestParam double num2,
            @RequestParam String operation) {

        double result = 0;
        String error = null;

        switch (operation) {
            case "add":
                result = num1 + num2;
                break;
            case "subtract":
                result = num1 - num2;
                break;
            case "multiply":
                result = num1 * num2;
                break;
            case "divide":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    error = "Cannot divide by zero";
                }
                break;
            default:
                error = "Invalid operation";
        }

        Map<String, Object> response = new HashMap<>();
        if (error != null)
            response.put("error", error);
        else
            response.put("result", result);

        return response;
    }

        /*
         * ENDPOINT: /api/bmi
         * Purpose: Calculate BMI with unit handling for weight and height
         */
        @GetMapping("/bmi")
        public Map<String, Object> calculateBmi(
            @RequestParam double weight, @RequestParam String weightUnit,
            @RequestParam double height, @RequestParam String heightUnit) {

        double weightInKg = weightUnit.equals("lb") ? weight * 0.453592 : weight;
        double heightInMeters = heightUnit.equals("in") ? height * 0.0254 : height / 100.0;

        Map<String, Object> response = new HashMap<>();
        if (heightInMeters <= 0 || weightInKg <= 0) {
            response.put("error", "Invalid height or weight");
            return response;
        }

        double bmi = weightInKg / (heightInMeters * heightInMeters);
        bmi = Math.round(bmi * 10.0) / 10.0;

        String category = "";
        if (bmi < 18.5)
            category = "Underweight";
        else if (bmi < 24.9)
            category = "Normal weight";
        else if (bmi < 29.9)
            category = "Overweight";
        else
            category = "Obese";

        response.put("result", bmi);
        response.put("category", category);
        return response;
    }

        /*
         * ENDPOINT: /api/convert
         * Purpose: Unit conversion across categories (temperature, length, weight, currency)
         * Note: Currency attempts a live API then falls back to offline rates
         */
        @GetMapping("/convert")
        public Map<String, Object> convertUnit(
            @RequestParam double value, @RequestParam String category,
            @RequestParam String fromUnit, @RequestParam String toUnit) {

        double result = 0;

        try {
            switch (category) {
                case "temperature":
                    result = convertTemperature(value, fromUnit, toUnit);
                    break;
                case "length":
                    result = convertLength(value, fromUnit, toUnit);
                    break;
                case "weight":
                    result = convertWeight(value, fromUnit, toUnit);
                    break;
                case "currency":
                    result = convertCurrency(value, fromUnit, toUnit);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown category");
            }
        } catch (Exception e) {
            Map<String, Object> errorRes = new HashMap<>();
            errorRes.put("error", "Conversion failed");
            return errorRes;
        }

        // Round to 4 decimal places for clean display
        result = Math.round(result * 10000.0) / 10000.0;

        Map<String, Object> response = new HashMap<>();
        response.put("result", result);
        return response;
    }

    /*
     * HELPERS: Temperature conversion helper
     */
    private double convertTemperature(double val, String from, String to) {
        if (from.equals(to))
            return val;
        double celsius = val;
        if (from.equals("F"))
            celsius = (val - 32) * 5 / 9;
        if (from.equals("K"))
            celsius = val - 273.15;

        if (to.equals("C"))
            return celsius;
        if (to.equals("F"))
            return (celsius * 9 / 5) + 32;
        if (to.equals("K"))
            return celsius + 273.15;
        return val;
    }

    /*
     * HELPERS: Length conversion helper using meters as an intermediary
     */
    private double convertLength(double val, String from, String to) {
        Map<String, Double> toMeters = Map.of(
                "m", 1.0, "km", 1000.0, "cm", 0.01, "mm", 0.001,
                "in", 0.0254, "ft", 0.3048, "yd", 0.9144, "mi", 1609.34);
        double meters = val * toMeters.get(from);
        return meters / toMeters.get(to);
    }

    /*
     * HELPERS: Weight conversion helper using kilograms as an intermediary
     */
    private double convertWeight(double val, String from, String to) {
        Map<String, Double> toKg = Map.of(
                "kg", 1.0, "g", 0.001, "mg", 0.000001,
                "lb", 0.453592, "oz", 0.0283495);
        double kg = val * toKg.get(from);
        return kg / toKg.get(to);
    }

    /*
     * LIVE CURRENCY: Attempts to fetch live exchange rates, falls back to offline rates
     */
    @SuppressWarnings("unchecked")
    private double convertCurrency(double val, String from, String to) {
        if (from.equals(to))
            return val;

        try {
            // 1. Ask the public API for the latest exchange rates based on our 'from'
            // currency
            String apiUrl = "https://open.er-api.com/v6/latest/" + from;
            RestTemplate restTemplate = new RestTemplate();

            // 2. Fetch and parse the JSON response
            Map<String, Object> apiResponse = restTemplate.getForObject(apiUrl, Map.class);

            // 3. Drill down into the "rates" section of the JSON
            if (apiResponse != null && apiResponse.containsKey("rates")) {
                Map<String, Double> liveRates = (Map<String, Double>) apiResponse.get("rates");

                // 4. Find our target currency and do the math
                if (liveRates.containsKey(to)) {
                    // Number comes through differently depending on the parser, converting safely:
                    double currentLiveRate = Double.parseDouble(liveRates.get(to).toString());
                    return val * currentLiveRate;
                }
            }
        } catch (Exception e) {
            System.out.println("Live API Failed. Falling back to offline mode. Error: " + e.getMessage());
        }

        // --- OFFLINE FALLBACK ---
        // If the internet is down, it uses these static rates instead of crashing.
        Map<String, Double> offlineRatesToUSD = Map.ofEntries(
            Map.entry("USD", 1.0), // US Dollar
            Map.entry("EUR", 0.92), // Euro
            Map.entry("GBP", 0.79), // British Pound
            Map.entry("INR", 83.50), // Indian Rupee
            Map.entry("JPY", 155.0), // Japanese Yen
            Map.entry("CAD", 1.37), // Canadian Dollar
            Map.entry("AUD", 1.52), // Australian Dollar
            Map.entry("CHF", 0.91), // Swiss Franc
            Map.entry("CNY", 7.24), // Chinese Yuan
            Map.entry("SGD", 1.35), // Singapore Dollar
            Map.entry("NZD", 1.67), // New Zealand Dollar
            Map.entry("ZAR", 18.50), // South African Rand
            Map.entry("BRL", 5.15),  // Brazilian Real
            Map.entry("KRW", 1370.0) // Korean Won
        );
        double inUSD = val / offlineRatesToUSD.get(from);
        return inUSD * offlineRatesToUSD.get(to);
    }
}