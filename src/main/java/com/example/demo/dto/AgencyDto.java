package com.example.demo.dto;

import com.example.demo.domain.Agency;

public class AgencyDto extends BaseDto {
  private String id;
  private String name;
  private String location;

  public AgencyDto() {
  }

  public AgencyDto(Agency agency) {
    this.id = agency.getId();
    this.name = agency.getName();
    this.location = agency.getLocation();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}
