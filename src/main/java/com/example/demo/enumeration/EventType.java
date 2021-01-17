package com.example.demo.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum EventType {
  SMO("smoke detected"),
  CAR("car fire"),
  PUB("public disturbance"),
  NOI("noise");

  private final String eventName;

  private static final Map<String, EventType> lookups = new HashMap<>();

  static {
    for (EventType item : values()) {
      lookups.put(item.name(), item);
    }
  }

  public static EventType fromName(String name){
    return lookups.get(name);
  }

  EventType(String eventName) {
    this.eventName = eventName;
  }

  public String getEventName() {
    return eventName;
  }
}
