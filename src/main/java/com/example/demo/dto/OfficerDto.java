package com.example.demo.dto;

import com.example.demo.domain.Officer;

public class OfficerDto extends BaseDto {
  private String id;
  private String officerNo;
  private String officerName;
  private String agencyId;
  private String accessToken;

  public OfficerDto() {
  }

  public OfficerDto(Officer officer) {
    this.id = officer.getId();
    this.officerNo = officer.getOfficerNo();
    this.officerName = officer.getOfficerName();
    this.agencyId = officer.getAgencyId();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOfficerNo() {
    return officerNo;
  }

  public void setOfficerNo(String officerNo) {
    this.officerNo = officerNo;
  }

  public String getOfficerName() {
    return officerName;
  }

  public void setOfficerName(String officerName) {
    this.officerName = officerName;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String password) {
    this.accessToken = password;
  }

  public String getAgencyId() {
    return agencyId;
  }

  public void setAgencyId(String agencyId) {
    this.agencyId = agencyId;
  }
}
