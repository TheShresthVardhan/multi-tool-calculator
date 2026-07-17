package com.example.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class BmiService {

    public double calculateBmi(double weight, String weightUnit, double height, String heightUnit) {
        double weightInKg = weightUnit.equals("lb") ? weight * 0.453592 : weight;
        double heightInMeters = heightUnit.equals("in") ? height * 0.0254 : height / 100.0;
        if (heightInMeters <= 0 || weightInKg <= 0)
            throw new IllegalArgumentException("Height and weight must be positive");
        return Math.round(weightInKg / (heightInMeters * heightInMeters) * 10.0) / 10.0;
    }

    public String categorize(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 24.9) return "Normal weight";
        if (bmi < 29.9) return "Overweight";
        return "Obese";
    }
}
