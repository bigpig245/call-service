package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

import static java.util.Optional.ofNullable;

public class EventRequestSearchDto {
  @JsonIgnore
  private String agencyId;
  private String responder;
  private LocalDate eventDateFrom;
  private LocalDate eventDateTo;
  private String sortBy;
  private String sortOrder;
  private Integer page;
  private Integer limit;

  public String getAgencyId() {
    return agencyId;
  }

  public void setAgencyId(String agencyId) {
    this.agencyId = agencyId;
  }

  public String getResponder() {
    return responder;
  }

  public void setResponder(String responder) {
    this.responder = responder;
  }

  public LocalDate getEventDateFrom() {
    return eventDateFrom;
  }

  public void setEventDateFrom(LocalDate eventDateFrom) {
    this.eventDateFrom = eventDateFrom;
  }

  public LocalDate getEventDateTo() {
    return eventDateTo;
  }

  public void setEventDateTo(LocalDate eventDateTo) {
    this.eventDateTo = eventDateTo;
  }

  public int getPage() {
    return ofNullable(page).orElse(1);
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public int getLimit() {
    return ofNullable(limit).orElse(100);
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getSortBy() {
    return ofNullable(sortBy).orElse("lastModifiedDate");
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }
}
