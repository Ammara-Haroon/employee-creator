package com.projects.backend.employee;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmploymentTypeValidator.class)
public @interface EmploymentTypeConstraint{
    String message() default "employment type can be permanent or part_time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?>[] customValidationGroups() default {};
}



