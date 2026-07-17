package com.example.calculator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class ConversionService {

    private static final Logger log = LoggerFactory.getLogger(ConversionService.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, Double> offlineRatesToUSD;

    public ConversionService(@Value("${conversion.currency.usd.EUR:0.92}") double eur,
                             @Value("${conversion.currency.usd.GBP:0.79}") double gbp,
                             @Value("${conversion.currency.usd.INR:83.50}") double inr,
                             @Value("${conversion.currency.usd.JPY:155.0}") double jpy,
                             @Value("${conversion.currency.usd.CAD:1.37}") double cad,
                             @Value("${conversion.currency.usd.AUD:1.52}") double aud,
                             @Value("${conversion.currency.usd.CHF:0.91}") double chf,
                             @Value("${conversion.currency.usd.CNY:7.24}") double cny,
                             @Value("${conversion.currency.usd.SGD:1.35}") double sgd,
                             @Value("${conversion.currency.usd.NZD:1.67}") double nzd,
                             @Value("${conversion.currency.usd.ZAR:18.50}") double zar,
                             @Value("${conversion.currency.usd.BRL:5.15}") double brl,
                             @Value("${conversion.currency.usd.KRW:1370.0}") double krw) {
        this.offlineRatesToUSD = Map.ofEntries(
            Map.entry("USD", 1.0), Map.entry("EUR", eur), Map.entry("GBP", gbp), Map.entry("INR", inr),
            Map.entry("JPY", jpy), Map.entry("CAD", cad), Map.entry("AUD", aud), Map.entry("CHF", chf),
            Map.entry("CNY", cny), Map.entry("SGD", sgd), Map.entry("NZD", nzd), Map.entry("ZAR", zar),
            Map.entry("BRL", brl), Map.entry("KRW", krw)
        );
    }

    public double convert(double value, String category, String fromUnit, String toUnit) {
        return switch (category) {
            case "temperature" -> convertTemperature(value, fromUnit, toUnit);
            case "length" -> convertLength(value, fromUnit, toUnit);
            case "weight" -> convertWeight(value, fromUnit, toUnit);
            case "currency" -> convertCurrency(value, fromUnit, toUnit);
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        };
    }

    private double convertTemperature(double val, String from, String to) {
        if (from.equals(to)) return val;
        double celsius = switch (from) {
            case "F" -> (val - 32) * 5 / 9;
            case "K" -> val - 273.15;
            default -> val;
        };
        return switch (to) {
            case "F" -> (celsius * 9 / 5) + 32;
            case "K" -> celsius + 273.15;
            default -> celsius;
        };
    }

    private double convertLength(double val, String from, String to) {
        Map<String, Double> toMeters = Map.of(
            "m", 1.0, "km", 1000.0, "cm", 0.01, "mm", 0.001,
            "in", 0.0254, "ft", 0.3048, "yd", 0.9144, "mi", 1609.34);
        return val * toMeters.get(from) / toMeters.get(to);
    }

    private double convertWeight(double val, String from, String to) {
        Map<String, Double> toKg = Map.of(
            "kg", 1.0, "g", 0.001, "mg", 0.000001, "lb", 0.453592, "oz", 0.0283495);
        return val * toKg.get(from) / toKg.get(to);
    }

    @SuppressWarnings("unchecked")
    private double convertCurrency(double val, String from, String to) {
        if (from.equals(to)) return val;
        try {
            Map<String, Object> apiResponse = restTemplate.getForObject("https://open.er-api.com/v6/latest/" + from, Map.class);
            if (apiResponse != null && apiResponse.containsKey("rates")) {
                Map<String, Double> liveRates = (Map<String, Double>) apiResponse.get("rates");
                if (liveRates.containsKey(to))
                    return val * Double.parseDouble(liveRates.get(to).toString());
            }
        } catch (Exception e) {
            log.warn("Live API failed, using offline rates: {}", e.getMessage());
        }
        return val / offlineRatesToUSD.get(from) * offlineRatesToUSD.get(to);
    }
}
