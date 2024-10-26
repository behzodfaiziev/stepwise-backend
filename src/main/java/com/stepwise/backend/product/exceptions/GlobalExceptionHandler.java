package com.stepwise.backend.product.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stepwise.backend.core.exception.CustomErrorResponse;
import com.stepwise.backend.product.exceptions.generic.ResourceNotFoundException;
import com.stepwise.backend.product.exceptions.user.EmailAlreadyExistsException;
import com.stepwise.backend.product.exceptions.user.UserNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private void exceptionLogger(String exceptionType, Exception ex) {
    logger.error("GlobalException: {}, {}", exceptionType, ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomErrorResponse> handleException(Exception ex) {
    exceptionLogger("Exception", ex);
    CustomErrorResponse errorResponse = new CustomErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.toString());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<CustomErrorResponse> handleRuntimeException(RuntimeException ex) {
    exceptionLogger("RuntimeException", ex);
    CustomErrorResponse errorResponse = new CustomErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ExecutionException.class)
  public ResponseEntity<CustomErrorResponse> handleExecutionException(ExecutionException ex) {
    exceptionLogger("ExecutionException", ex);
    CustomErrorResponse errorResponse = new CustomErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "error occurred while executing the request. Code:execution");
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InterruptedException.class)
  public ResponseEntity<CustomErrorResponse> handleInterruptedException(InterruptedException ex) {
    exceptionLogger("InterruptedException", ex);
    CustomErrorResponse errorResponse = new CustomErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "error occurred while executing the request. Code:interrupted");
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<CustomErrorResponse> handleNotFoundException(ResourceNotFoundException ex) {
    exceptionLogger("ResourceNotFoundException", ex);
    CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),
        ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UnsupportedOperationException.class)
  public ResponseEntity<CustomErrorResponse> handleUnsupportedOperationException(
      UnsupportedOperationException ex) {
    exceptionLogger("UnsupportedOperationException", ex);
    CustomErrorResponse errorResponse = new CustomErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), "Operation not supported: " + ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<CustomErrorResponse> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    exceptionLogger("IllegalArgumentException", ex);
    CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),
        ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
    exceptionLogger("AccessDeniedException", e);
    CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.FORBIDDEN.value(),
        e.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<CustomErrorResponse> handleUserAlreadyExistsException(
      EmailAlreadyExistsException e) {
    CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),
        e.getMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),
        "Required request body is missing");
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<CustomErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),
        e.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomErrorResponse> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(FieldError::getDefaultMessage)
        .toList();
    CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),
        errors.get(0));
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

  }

  @ExceptionHandler(JsonProcessingException.class)
  public ResponseEntity<CustomErrorResponse> handleJsonProcessingException(
      JsonProcessingException ex) {
    exceptionLogger("JsonProcessingException", ex);
    CustomErrorResponse errorResponse = new CustomErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occurred while processing the request: Json ");
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<CustomErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
    exceptionLogger("NoResourceFoundException", ex);
    CustomErrorResponse errorResponse = new CustomErrorResponse(
        HttpStatus.NOT_FOUND.value(), ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

}