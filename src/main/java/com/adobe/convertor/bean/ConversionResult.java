package com.adobe.convertor.bean;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 27 2024 - 8:48 AM
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The {@code ConversionResult} class represents a single conversion result.
 * This class is annotated with {@link Data} and {@link AllArgsConstructor} from Lombok to automatically
 * generate boilerplate code such as getters, setters, toString, equals, hashCode, and an all-arguments constructor.
 */
@Data
@AllArgsConstructor
public class ConversionResult {

    /**
     * The input value for the conversion.
     */
    private String input;

    /**
     * The output value of the conversion.
     */
    private String output;
}
