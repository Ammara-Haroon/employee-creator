package com.projects.backend.employee;

public enum ContractType {
  PERMANENT("PERMANENT"), CONTRACT("CONTRACT");
  public final String value;
  private ContractType(String value){
    this.value = value;
  }
  public String getValue() {
    return this.value;
  }
}
