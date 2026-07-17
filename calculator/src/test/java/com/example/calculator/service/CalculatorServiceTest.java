package com.example.calculator.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CalculatorServiceTest {

    private final CalculatorService service = new CalculatorService();

    @Test
    void add() {
        assertEquals(5, service.add(2, 3));
        assertEquals(-1, service.add(2, -3));
    }

    @Test
    void subtract() {
        assertEquals(-1, service.subtract(2, 3));
        assertEquals(5, service.subtract(2, -3));
    }

    @Test
    void multiply() {
        assertEquals(6, service.multiply(2, 3));
        assertEquals(0, service.multiply(0, 5));
        assertEquals(-6, service.multiply(2, -3));
    }

    @Test
    void divide() {
        assertEquals(2, service.divide(6, 3));
        assertEquals(0.5, service.divide(1, 2));
    }

    @Test
    void divideByZero() {
        assertThrows(ArithmeticException.class, () -> service.divide(5, 0));
    }

    @Test
    void calculate() {
        assertEquals(8, service.calculate(5, 3, "add"));
        assertEquals(2, service.calculate(5, 3, "subtract"));
        assertEquals(15, service.calculate(5, 3, "multiply"));
        assertEquals(2, service.calculate(6, 3, "divide"));
    }

    @Test
    void calculateInvalidOperation() {
        assertThrows(IllegalArgumentException.class, () -> service.calculate(1, 1, "mod"));
    }
}
