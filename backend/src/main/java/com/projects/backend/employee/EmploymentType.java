package com.projects.backend.employee;

public enum EmploymentType {
  PART_TIME("PART_TIME"),FULL_TIME("FULL_TIME");
  public final String value;
  private EmploymentType(String value){
    this.value = value;
  }
  public String getValue() {
    return this.value;
  }
}
