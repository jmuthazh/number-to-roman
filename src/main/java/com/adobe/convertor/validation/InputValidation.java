package com.adobe.convertor.validation;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 28 2024 - 5:41 PM
 */


import com.adobe.convertor.exception.InvalidInputException;

/**
 * The {@code InputValidation} class provides static utility methods for validating numbers and ranges.
 * It ensures that numbers and ranges fall within the acceptable range (1-3999) and validates number formats.
 */
public class InputValidation {

    // Private constructor to prevent instantiation
    private InputValidation() {
        throw new AssertionError("Instantiating utility class is not allowed");
    }

    /**
     * Validates that a single number is within the acceptable range (1-3999).
     *
     * @param number the number to be validated
     * @throws InvalidInputException if the number is out of the range (1-3999)
     */
    public static void validateSingleNumber(int number) {
        if (number < 1 || number > 3999) {
            throw new InvalidInputException("Number out of range (1-3999): " + number);
        }
    }

    /**
     * Validates that a range of numbers is valid.
     * The minimum and maximum values must both be within the acceptable range (1-3999), and min must be less than or equal to max.
     *
     * @param min the minimum number in the range (inclusive)
     * @param max the maximum number in the range (inclusive)
     * @throws InvalidInputException if the range is invalid or if numbers are out of the range (1-3999)
     */
    public static void validateRange(int min, int max) {
        if (min < 1 || max > 3999 || min > max) {
            throw new InvalidInputException("Invalid range. Ensure min < max and both are in the range 1-3999.");
        }
    }

    /**
     * Validates and parses a string to an integer.
     * It first attempts to parse the string as an integer, then validates that the number is within the acceptable range (1-3999).
     *
     * @param text the string to be parsed and validated
     * @return the parsed integer value
     * @throws InvalidInputException if the string is not a valid integer or if the number is out of range (1-3999)
     */
    public static int validateAndParseText(String text) {
        try {
            int number = Integer.parseInt(text);
            validateSingleNumber(number);
            return number;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid number format: " + text);
        }
    }
}
