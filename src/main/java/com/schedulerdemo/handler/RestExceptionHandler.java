package com.schedulerdemo.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Mamatha Dec 12, 2019 9:17:23 PM
 * RestExceptionHandler.java
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    String errorDesc = "Malformed JSON request";
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Error", errorDesc));
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler({NullPointerException.class})
  public ResponseEntity<Object> handleRunTimeException(RuntimeException e) {
    return buildResponseEntity(
        new ApiError(HttpStatus.NOT_FOUND, "Error", "Information for selected date not found"));
  }

}

