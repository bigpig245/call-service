package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "agency")
public class Agency extends BaseEntity {
  @Id
  @Column(name = "id")
  private String id;
  @Column(name = "name")
  private String name;
  @Column(name = "location")
  private String location;

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
