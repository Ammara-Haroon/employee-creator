package com.projects.backend.employee;

public enum SortType {
   ASC("ASC"),
  DESC("DESC");

  public final String label;

  private SortType(String label) {
    this.label = label;
  }
}
