package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "officer")
public class Officer extends BaseEntity {
  @Id
  @Column(name = "id")
  private String id;
  @Column(name = "officer_no")
  private String officerNo;
  @Column(name = "officer_name")
  private String officerName;
  @Column(name = "access_token")
  private String accessToken;
  @Column(name = "agency_id")
  private String agencyId;

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

  public String getAgencyId() {
    return agencyId;
  }

  public void setAgencyId(String agencyId) {
    this.agencyId = agencyId;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String password) {
    this.accessToken = password;
  }

}
