package com.projects.backend.employee;

public enum DepartmentType {
  ADMIN("ADMIN"), FINANCE("FINANCE"), IT("IT");
  public final String value;
  private DepartmentType(String value){
    this.value = value;
  }
  public String getValue() {
    return this.value;
  }
}
