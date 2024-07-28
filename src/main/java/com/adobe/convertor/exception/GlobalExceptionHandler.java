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

@ControllerAdvice
public class GlobalExceptionHandler {

 @ExceptionHandler(NumberFormatException.class)
 public ResponseEntity<?> handleNumberFormatException(NumberFormatException ex, WebRequest request) {
  ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
  return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
 }

 @ExceptionHandler(InvalidInputException.class)
 public ResponseEntity<?> handleInvalidInputException(InvalidInputException ex, WebRequest request) {
  ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
  return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
 }

 @ExceptionHandler(Exception.class)
 public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
  ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), request.getDescription(false));
  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
 }
}
