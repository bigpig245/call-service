package com.example.demo.dto;

import com.example.demo.enumeration.EventType;
import com.example.demo.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InputEventRequestDto {
  @JsonProperty("agency_id")
  private String agencyId;
  @JsonProperty("event_number")
  private String eventNumber;
  @JsonProperty("event_type_code")
  private String eventTypeCode;
  @JsonIgnore
  private EventType eventType;
  @JsonProperty("event_time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_DATETIME_FORMAT)
  private LocalDateTime eventTime;
  @JsonProperty("dispatch_time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_DATETIME_FORMAT)
  private LocalDateTime dispatchTime;
  private String responder;
  @JsonIgnore
  private String dispatcher;

  public String getAgencyId() {
    return agencyId;
  }

  public void setAgencyId(String agencyId) {
    this.agencyId = agencyId;
  }

  public String getEventNumber() {
    return eventNumber;
  }

  public void setEventNumber(String eventNumber) {
    this.eventNumber = eventNumber;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public LocalDateTime getEventTime() {
    return eventTime;
  }

  public void setEventTime(LocalDateTime eventTime) {
    this.eventTime = eventTime;
  }

  public LocalDateTime getDispatchTime() {
    return dispatchTime;
  }

  public void setDispatchTime(LocalDateTime dispatchTime) {
    this.dispatchTime = dispatchTime;
  }

  public String getResponder() {
    return responder;
  }

  public void setResponder(String responder) {
    this.responder = responder;
  }

  public String getDispatcher() {
    return dispatcher;
  }

  public void setDispatcher(String dispatcher) {
    this.dispatcher = dispatcher;
  }

  public String getEventTypeCode() {
    return eventTypeCode;
  }

  public void setEventTypeCode(String eventTypeCode) {
    this.eventTypeCode = eventTypeCode;
  }
}
