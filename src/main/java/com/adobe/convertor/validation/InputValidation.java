package com.adobe.convertor.validation;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 28 2024 - 5:41 PM
 */


import com.adobe.convertor.exception.InvalidInputException;

public class InputValidation {
    public static void validateSingleNumber(int number) {
        if (number < 1 || number > 3999) {
            throw new InvalidInputException("Number out of range (1-3999): " + number);
        }
    }

    public static void validateRange(int min, int max) {
        if (min < 1 || max > 3999 || min > max) {
            throw new InvalidInputException("Invalid range. Ensure min < max and both are in the range 1-3999.");
        }
    }

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
