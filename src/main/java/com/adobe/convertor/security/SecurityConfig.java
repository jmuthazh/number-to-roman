package com.adobe.convertor.security;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Aug, 02 2024 - 3:17 AM
 */


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/actuator/**").permitAll() // Allow actuator endpoints
                                .requestMatchers("/api/otel/**").permitAll() // Allow OpenTelemetry endpoints
                                .anyRequest().authenticated()
                ).httpBasic(httpBasic -> {
                    // Configure basic authentication if needed
                })
                .securityContext(securityContext -> {
                    // Configure security context if needed
                });
        return http.build();
    }
}


