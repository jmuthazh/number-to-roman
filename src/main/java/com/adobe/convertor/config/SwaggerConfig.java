package com.adobe.convertor.config;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 27 2024 - 9:21 AM
 */


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The {@code SwaggerConfig} class provides configuration for Swagger/OpenAPI documentation.
 * This class is annotated with {@link Configuration} to indicate that it contains one or more
 * {@link Bean} methods will be processed by the Spring container to generate bean definitions
 * and service requests for those beans at runtime.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates a custom {@link OpenAPI} bean with API metadata for the "Number to Roman Converter API".
     *
     * @return a custom {@link OpenAPI} instance with API information
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Number to Roman Converter API")
                        .version("1.0")
                        .description("API for converting numbers to Roman numerals"));
    }
}
