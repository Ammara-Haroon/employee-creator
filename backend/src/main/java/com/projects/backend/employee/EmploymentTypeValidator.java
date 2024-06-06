package com.projects.backend.employee;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmploymentTypeValidator implements ConstraintValidator<EmploymentTypeConstraint, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if(value == null) return true;
    return Arrays.stream(EmploymentType.values())
      .map(val->val.getValue())
      .toList()
      .contains(value.trim().toUpperCase());
  }

}
