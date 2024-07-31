package com.adobe.convertor.bean;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 27 2024 - 9:12 AM
 */


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * The {@code ConversionResponse} class represents a response containing a list of conversion results.
 * This class is annotated with {@link Data} and {@link AllArgsConstructor} from Lombok to automatically
 * generate boilerplate code such as getters, setters, toString, equals, hashCode, and an all-arguments constructor.
 */
@Data
@AllArgsConstructor
public class ConversionResponse {

    /**
     * A list of {@link ConversionResult} representing the conversion results.
     */
    private List<ConversionResult> conversions;
}
