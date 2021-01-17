package com.example.demo.config;

import org.slf4j.MDC;

import java.util.UUID;

public class ExecutionContextHolder {
  private static final ThreadLocal<String> requestIdHolder = new ThreadLocal<>();

  private static void setCurrentRequestId(String requestId) {
    requestIdHolder.set(requestId);
  }

  public static String getCurrentRequestId() {
    return requestIdHolder.get();
  }

  public static void startRequest() {
    String requestId = generateUUID().replaceAll("-", "");
    setCurrentRequestId(requestId);
    MDC.put("executionId", requestId);
  }

  public static void endRequest() {
    requestIdHolder.remove();
  }

  private static String generateUUID() {
    return UUID.randomUUID().toString();
  }
}
