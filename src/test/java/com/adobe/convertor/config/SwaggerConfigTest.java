package com.adobe.convertor.config;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 30 2024 - 1:05 AM
 */


import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SwaggerConfigTest {

 @Configuration
 static class TestConfig {
  @Bean
  public SwaggerConfig swaggerConfig() {
   return new SwaggerConfig();
  }
 }

 @Test
 public void testCustomOpenAPI() {
  SwaggerConfig swaggerConfig = new SwaggerConfig();
  OpenAPI openAPI = swaggerConfig.customOpenAPI();

  assertThat(openAPI).isNotNull();
  assertThat(openAPI.getInfo()).isNotNull();
  assertThat(openAPI.getInfo().getTitle()).isEqualTo("Number to Roman Converter API");
  assertThat(openAPI.getInfo().getVersion()).isEqualTo("1.0");
  assertThat(openAPI.getInfo().getDescription()).isEqualTo("API for converting numbers to Roman numerals");
 }
}