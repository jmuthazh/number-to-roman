package com.adobe.convertor.service;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 28 2024 - 4:22 PM
 */


import com.adobe.convertor.bean.ConversionResult;
import java.util.List;


/**
 * The {@code NumberToRomanService} interface defines the contract for services that convert numbers to Roman numerals.
 * It includes methods for converting a single number as well as a range of numbers to their Roman numeral representations.
 */
public interface NumberToRomanService {

    /**
     * Converts a given number to its Roman numeral representation.
     *
     * @param number the number to be converted
     * @return a {@link ConversionResult} containing the original number and its Roman numeral representation
     */
    ConversionResult convertToRomanNumeral(int number);

    /**
     * Converts a range of numbers to their Roman numeral representations.
     *
     * @param min the minimum number in the range (inclusive)
     * @param max the maximum number in the range (inclusive)
     * @return a {@link List} of {@link ConversionResult} containing the original numbers and their Roman numeral representations
     */
    List<ConversionResult> convertRangeToRoman(int min, int max);
}
