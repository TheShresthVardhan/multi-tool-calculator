package com.example.calculator.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BmiServiceTest {

    private final BmiService service = new BmiService();

    @Test
    void calculateBmiMetric() {
        assertEquals(22.9, service.calculateBmi(70, "kg", 175, "cm"));
    }

    @Test
    void calculateBmiImperial() {
        double bmi = service.calculateBmi(154, "lb", 69, "in");
        assertTrue(bmi > 20 && bmi < 30);
    }

    @Test
    void calculateBmiInvalidHeight() {
        assertThrows(IllegalArgumentException.class, () -> service.calculateBmi(70, "kg", 0, "cm"));
    }

    @Test
    void categorize() {
        assertEquals("Underweight", service.categorize(16.0));
        assertEquals("Normal weight", service.categorize(22.0));
        assertEquals("Overweight", service.categorize(27.0));
        assertEquals("Obese", service.categorize(35.0));
    }
}
