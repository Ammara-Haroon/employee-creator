package com.projects.backend.employee.validators;

import java.util.Arrays;

import com.projects.backend.employee.DepartmentType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartmentTypeValidator implements ConstraintValidator<DepartmentTypeConstraint, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null)
      return true;
    return Arrays.stream(DepartmentType.values())
        .map(val -> val.getValue())
        .toList()
        .contains(value.trim().toUpperCase());
  }

}
