package com.example.demo.domain;

import com.example.demo.dto.EventRequestDto;
import com.example.demo.enumeration.EventType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event_request")
public class EventRequest extends BaseEntity {
  @Id
  @Column(name = "id")
  private String id;
  @Column(name = "agency_id")
  private String agencyId;
  @Column(name = "event_number")
  private String eventNumber;
  @Column(name = "event_type_code")
  @Enumerated(EnumType.STRING)
  private EventType eventType;
  @Column(name = "event_time")
  private LocalDateTime eventTime;
  @Column(name = "dispatch_time")
  private LocalDateTime dispatchTime;
  @Column(name = "responder")
  private String responder;
  @Column(name = "dispatcher")
  private String dispatcher;

  public EventRequest() {
  }

  public EventRequest(EventRequestDto requestDto) {
    this.setId(UUID.randomUUID().toString());
    this.setAgencyId(requestDto.getAgencyId());
    this.setEventNumber(requestDto.getEventNumber());
    this.setEventType(requestDto.getEventType());
    this.setEventTime(requestDto.getEventTime());
    this.setDispatchTime(requestDto.getDispatchTime());
    this.setDispatcher(requestDto.getDispatcher());
    this.setResponder(requestDto.getResponder());
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

  public void setEventType(EventType eventTypeCode) {
    this.eventType = eventTypeCode;
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
