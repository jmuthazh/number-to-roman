package com.adobe.convertor.exception;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 25 2024 - 3:15 PM
 */


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * The {@code GlobalExceptionHandler} class is a global exception handler that handles various types of exceptions
 * and provides custom error responses. It is annotated with {@link ControllerAdvice} to handle exceptions globally
 * across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link NumberFormatException} exceptions and returns a {@link ResponseEntity} with a {@link ErrorDetails}
     * object containing error details and an HTTP status of {@link HttpStatus#BAD_REQUEST}.
     *
     * @param ex      the {@link NumberFormatException} that was thrown
     * @param request the {@link WebRequest} containing request details
     * @return a {@link ResponseEntity} containing the error details and HTTP status
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorDetails> handleNumberFormatException(NumberFormatException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link InvalidInputException} exceptions and returns a {@link ResponseEntity} with a {@link ErrorDetails}
     * object containing error details and an HTTP status of {@link HttpStatus#BAD_REQUEST}.
     *
     * @param ex      the {@link InvalidInputException} that was thrown
     * @param request the {@link WebRequest} containing request details
     * @return a {@link ResponseEntity} containing the error details and HTTP status
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorDetails> handleInvalidInputException(InvalidInputException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general {@link Exception} instances and returns a {@link ResponseEntity} with a {@link ErrorDetails}
     * object containing error details and an HTTP status of {@link HttpStatus#INTERNAL_SERVER_ERROR}.
     *
     * @param ex      the {@link Exception} that was thrown
     * @param request the {@link WebRequest} containing request details
     * @return a {@link ResponseEntity} containing the error details and HTTP status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
