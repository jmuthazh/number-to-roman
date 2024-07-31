package com.adobe.convertor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The {@code NumberToRomanApplication} class is the entry point for the Spring Boot application.
 * It contains the {@link #main(String[])} method which is used to launch the application.
 * <p>
 * This class is annotated with {@link SpringBootApplication} to enable Spring Boot's auto-configuration,
 * component scanning, and configuration support.
 */

@SpringBootApplication
public class NumberToRomanApplication {
    /**
     * The main method is the entry point of the Spring Boot application.
     * It initializes the Spring context and starts the application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(NumberToRomanApplication.class, args);
    }

}
