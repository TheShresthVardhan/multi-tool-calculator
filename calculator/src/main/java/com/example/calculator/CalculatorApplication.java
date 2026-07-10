package com.example.calculator;

/*
 * PACKAGE: com.example.calculator
 *
 * IMPORTS: Spring Boot core classes for application startup and auto-configuration
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * CLASS: CalculatorApplication
 * Description: Spring Boot application entry point for the calculator app
 */
@SpringBootApplication
public class CalculatorApplication {

	/*
	 * MAIN: Application entrypoint
	 */
	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

}
