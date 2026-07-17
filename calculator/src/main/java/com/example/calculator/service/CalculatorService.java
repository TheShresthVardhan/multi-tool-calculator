package com.example.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double add(double a, double b) { return a + b; }

    public double subtract(double a, double b) { return a - b; }

    public double multiply(double a, double b) { return a * b; }

    public double divide(double a, double b) {
        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
        return a / b;
    }

    public double calculate(double num1, double num2, String operation) {
        return switch (operation) {
            case "add" -> add(num1, num2);
            case "subtract" -> subtract(num1, num2);
            case "multiply" -> multiply(num1, num2);
            case "divide" -> divide(num1, num2);
            default -> throw new IllegalArgumentException("Invalid operation: " + operation);
        };
    }
}
