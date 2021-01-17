package com.example.demo.util;

import com.example.demo.enumeration.EventType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.TreeSet;

public enum ErrorMessage {
  TOKEN_NOT_FOUND("ERR_001", "Token not found", HttpStatus.NOT_FOUND),
  RESPONDER_NOT_FOUND("ERR_002", "Responder not found", HttpStatus.NOT_FOUND),
  RESPONDER_AND_DISPATCHER_NOT_MATCH("ERR_003", "Responder and Dispatcher do not belong to one agency",
      HttpStatus.BAD_REQUEST),
  DISPATCHER_DOES_NOT_BELONG_TO_AGENCY("ERR_005", "You are not authorized to access/request from other agency",
      HttpStatus.FORBIDDEN),
  REQUIRED_FIELD("ERR_006", "%s is required",
      HttpStatus.BAD_REQUEST),
  INVALID_EVENT_TYPE("ERR_007", "event_type with value %s is invalid. Allowed values: "
      + StringUtils.join(EventType.values(), ","),
      HttpStatus.BAD_REQUEST),
  OK("0", "SUCCESS", HttpStatus.OK);

  private final String code;
  private final String message;
  private Set<String> params;
  private final HttpStatus httpStatus;

  ErrorMessage(String code, String message, HttpStatus httpStatus) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
    this.params = new TreeSet<>();
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public Set<String> getParams() {
    return params;
  }

  public ErrorMessage setParams(Set<String> params) {
    this.params = params;
    return this;
  }
}
