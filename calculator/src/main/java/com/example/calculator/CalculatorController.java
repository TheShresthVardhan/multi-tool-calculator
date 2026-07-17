package com.example.calculator;

import com.example.calculator.service.BmiService;
import com.example.calculator.service.CalculatorService;
import com.example.calculator.service.ConversionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    private final CalculatorService calculatorService;
    private final BmiService bmiService;
    private final ConversionService conversionService;

    public CalculatorController(CalculatorService calculatorService, BmiService bmiService, ConversionService conversionService) {
        this.calculatorService = calculatorService;
        this.bmiService = bmiService;
        this.conversionService = conversionService;
    }

    @GetMapping("/calculate")
    public CalculateResponse calculate(@RequestParam double num1, @RequestParam double num2, @RequestParam String operation) {
        try {
            return new CalculateResponse(calculatorService.calculate(num1, num2, operation), null);
        } catch (ArithmeticException | IllegalArgumentException e) {
            return new CalculateResponse(null, e.getMessage());
        }
    }

    @GetMapping("/bmi")
    public BmiResponse calculateBmi(@RequestParam double weight, @RequestParam String weightUnit, @RequestParam double height, @RequestParam String heightUnit) {
        try {
            double bmi = bmiService.calculateBmi(weight, weightUnit, height, heightUnit);
            return new BmiResponse(bmi, bmiService.categorize(bmi), null);
        } catch (IllegalArgumentException e) {
            return new BmiResponse(null, null, e.getMessage());
        }
    }

    @GetMapping("/convert")
    public ConvertResponse convertUnit(@RequestParam double value, @RequestParam String category, @RequestParam String fromUnit, @RequestParam String toUnit) {
        try {
            double result = Math.round(conversionService.convert(value, category, fromUnit, toUnit) * 10000.0) / 10000.0;
            return new ConvertResponse(result, null);
        } catch (Exception e) {
            return new ConvertResponse(null, "Conversion failed");
        }
    }
}
