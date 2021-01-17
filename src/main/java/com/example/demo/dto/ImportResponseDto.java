package com.example.demo.dto;

import java.util.HashMap;
import java.util.Map;

public class ImportResponseDto {
  private int successItems = 0;
  private int failedItems = 0;
  private Map<Integer, String> errorItems = new HashMap<>();

  public int getSuccessItems() {
    return successItems;
  }

  public void setSuccessItems(int successItems) {
    this.successItems = successItems;
  }

  public int getFailedItems() {
    return failedItems;
  }

  public void setFailedItems(int failedItems) {
    this.failedItems = failedItems;
  }

  public Map<Integer, String> getErrorItems() {
    return errorItems;
  }

  public void addErrorMessage(int lineNumber, String message) {
    this.errorItems.put(lineNumber, message);
  }
}
