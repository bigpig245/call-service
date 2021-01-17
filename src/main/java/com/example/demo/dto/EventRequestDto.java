package com.example.demo.dto;

import com.example.demo.domain.EventRequest;
import com.example.demo.enumeration.EventType;
import com.example.demo.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class EventRequestDto extends BaseDto {
  @JsonProperty("event_id")
  private String id;
  @JsonProperty("agency_id")
  private String agencyId;
  @JsonProperty("event_number")
  private String eventNumber;
  @JsonProperty("event_type")
  private EventType eventType;
  @JsonProperty("event_time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_DATETIME_FORMAT)
  private LocalDateTime eventTime;
  @JsonProperty("dispatch_time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_DATETIME_FORMAT)
  private LocalDateTime dispatchTime;
  private String responder;
  private String dispatcher;

  public EventRequestDto() {
  }

  public EventRequestDto(EventRequest eventRequest) {
    this.id = eventRequest.getId();
    this.agencyId = eventRequest.getAgencyId();
    this.eventNumber = eventRequest.getEventNumber();
    this.eventType = eventRequest.getEventType();
    this.eventTime = eventRequest.getEventTime();
    this.dispatchTime = eventRequest.getDispatchTime();
    this.responder = eventRequest.getResponder();
    this.dispatcher = eventRequest.getDispatcher();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public LocalDateTime getDispatchTime() {
    return dispatchTime;
  }

  public void setDispatchTime(LocalDateTime dispatchTime) {
    this.dispatchTime = dispatchTime;
  }
}
