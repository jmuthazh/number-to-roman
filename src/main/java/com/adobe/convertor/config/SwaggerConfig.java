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

@Configuration
public class SwaggerConfig {

 @Bean
 public OpenAPI customOpenAPI() {
  return new OpenAPI()
          .info(new Info()
                  .title("Number to Roman Converter API")
                  .version("1.0")
                  .description("API for converting numbers to Roman numerals"));
 }
}
