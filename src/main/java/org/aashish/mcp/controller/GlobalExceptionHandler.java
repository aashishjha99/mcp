package org.aashish.mcp.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to intercept and standardize error responses across all controllers.
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

  /**
   * Catches all general exceptions (Exception.class) and returns a structured JSON response.
   *
   * @param ex the caught Exception
   * @return a standard Map containing error details with a 500 status code
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
    log.error("An unexpected error occurred: ", ex);

    Map<String, Object> errorDetails = new HashMap<>();
    errorDetails.put("timestamp", LocalDateTime.now());
    errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    errorDetails.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    errorDetails.put("message", ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred");

    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
