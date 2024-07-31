package com.adobe.convertor.exception;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 25 2024 - 3:16 PM
 */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code ErrorDetails} class represents the details of an error response.
 * This class is annotated with {@link Data}, {@link AllArgsConstructor}, and {@link NoArgsConstructor} from Lombok to automatically
 * generate boilerplate code such as getters, setters, toString, equals, hashCode, an all-arguments constructor, and a no-arguments constructor.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;
}

