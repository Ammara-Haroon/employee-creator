package com.projects.backend.employee;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContractTypeValidator implements ConstraintValidator<ContractTypeConstraint, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if(value == null) return true;
    return Arrays.stream(ContractType.values())
      .map(val->val.getValue())
      .toList()
      .contains(value.trim().toUpperCase());
  }

}
