package com.projects.backend.employee;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DepartmentTypeValidator.class)
public @interface DepartmentTypeConstraint{
    String message() default "department type can only be admin finance or it";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?>[] customValidationGroups() default {};
}

