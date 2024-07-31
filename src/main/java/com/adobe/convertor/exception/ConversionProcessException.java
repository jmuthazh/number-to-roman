package com.adobe.convertor.exception;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 31 2024 - 12:23 AM
 */


/**
 * Custom exception for errors during the conversion process.
 */
public class ConversionProcessException extends RuntimeException {
    public ConversionProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}