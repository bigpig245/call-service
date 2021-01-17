package com.example.demo.config;

import com.example.demo.dto.ErrorDto;
import com.example.demo.exception.CallServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * exception handler for controller
 */
@ControllerAdvice
public class RestExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(value = {CallServiceException.class})
  @ResponseBody
  public ResponseEntity<ErrorDto> handleResourceUnauthorized(CallServiceException ex) {
    ErrorDto error = new ErrorDto(ex.getRawMessage().getCode(), ex.getMessage());
    logger.error(ex.getMessage(), ex);
    return ResponseEntity.status(ex.getRawMessage().getHttpStatus()).body(error);
  }

  @ExceptionHandler(value = {Exception.class})
  @ResponseBody
  public ResponseEntity<ErrorDto> handleResourceUnauthorized(Exception ex) {
    ErrorDto error = new ErrorDto(ex.getMessage(), ex.getMessage());
    logger.error(ex.getMessage(), ex);
    return ResponseEntity.badRequest().body(error);
  }

}
