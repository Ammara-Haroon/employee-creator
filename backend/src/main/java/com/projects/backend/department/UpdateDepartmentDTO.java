package com.projects.backend.department;

import jakarta.validation.constraints.NotBlank;

public class UpdateDepartmentDTO {

  @NotBlank
  String name;

  public String getName() {
    return name;
  }

}
