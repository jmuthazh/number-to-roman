package com.adobe.convertor.exception;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 25 2024 - 3:20 PM
 */


/**
 * The {@code InvalidInputException} class is a custom exception that extends {@link RuntimeException}.
 * It is used to indicate that an invalid input was provided.
 */
public class InvalidInputException extends RuntimeException {
    /**
     * Constructs a new {@code InvalidInputException} with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
