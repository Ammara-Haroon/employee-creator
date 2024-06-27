package com.projects.backend.employee.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContractTypeValidator.class)
public @interface ContractTypeConstraint {
  String message()

  default "contract type can be permanent or contract";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<?>[] customValidationGroups() default {
  };
}