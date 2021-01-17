package com.example.demo.exception;

import com.example.demo.util.ErrorMessage;
import org.apache.commons.lang3.StringUtils;

public class CallServiceException extends RuntimeException {

  private ErrorMessage errorMessage;

  public CallServiceException(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage;
  }

  public ErrorMessage getRawMessage() {
    return errorMessage;
  }

  @Override
  public String getMessage() {
    return String.format(errorMessage.getMessage(), StringUtils.join(errorMessage.getParams(), ","));
  }

}
