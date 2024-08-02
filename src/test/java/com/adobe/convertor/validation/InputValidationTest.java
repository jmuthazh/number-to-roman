package com.adobe.convertor.validation;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 28 2024 - 6:00 PM
 */


import com.adobe.convertor.exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputValidationTest {

    @Test
    void testPrivateConstructor() throws Exception {
        // Access the private constructor using reflection
        Constructor<InputValidation> constructor = InputValidation.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // Verify that instantiating the class throws an AssertionError
        assertThrows(AssertionError.class, () -> invokePrivateConstructor(constructor));
    }

    void invokePrivateConstructor(Constructor<InputValidation> constructor) throws Throwable {
        try {
            constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    void testValidateSingleNumber_Valid() {
        // Should not throw any exception
        InputValidation.validateSingleNumber(1);
        InputValidation.validateSingleNumber(3999);
    }

    @Test
    void testValidateSingleNumber_Invalid() {
        // Number less than 1
        assertThatThrownBy(() -> InputValidation.validateSingleNumber(0))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Number out of range (1-3999): 0");

        // Number greater than 3999
        assertThatThrownBy(() -> InputValidation.validateSingleNumber(4000))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Number out of range (1-3999): 4000");
    }

    @Test
    void testValidateRange_Valid() {
        // Should not throw any exception
        InputValidation.validateRange(1, 10);
        InputValidation.validateRange(100, 200);
    }

    @Test
    void testValidateRange_Invalid() {
        // Min greater than max
        assertThatThrownBy(() -> InputValidation.validateRange(10, 1))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid range. Ensure min < max and both are in the range 1-3999.");

        // Min less than 1
        assertThatThrownBy(() -> InputValidation.validateRange(0, 10))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid range. Ensure min < max and both are in the range 1-3999.");

        // Max greater than 3999
        assertThatThrownBy(() -> InputValidation.validateRange(10, 4000))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid range. Ensure min < max and both are in the range 1-3999.");
    }

    @Test
    void testValidateAndParseText_Valid() {
        // Should return the parsed number
        assertThat(InputValidation.validateAndParseText("100")).isEqualTo(100);
        assertThat(InputValidation.validateAndParseText("3999")).isEqualTo(3999);
    }

    @Test
    void testValidateAndParseText_InvalidFormat() {
        // Invalid number format
        assertThatThrownBy(() -> InputValidation.validateAndParseText("abc"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid number format: abc");
    }

    @Test
    void testValidateAndParseText_OutOfRange() {
        // Number less than 1
        assertThatThrownBy(() -> InputValidation.validateAndParseText("0"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Number out of range (1-3999): 0");

        // Number greater than 3999
        assertThatThrownBy(() -> InputValidation.validateAndParseText("4000"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Number out of range (1-3999): 4000");
    }
}

