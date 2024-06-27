package com.projects.backend.employee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.projects.backend.employee.validators.ContractTypeConstraint;
import com.projects.backend.employee.validators.DepartmentTypeConstraint;
import com.projects.backend.employee.validators.EmploymentTypeConstraint;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateEmployeeDTO {
  @NotBlank
  private String firstName;

  @Pattern(regexp = ".*\\S.*", message = "middle name should be at least one character long")
  private String middleName;

  @NotBlank
  private String lastName;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Length(max = 20, min = 8)
  @Pattern(regexp = "\\d+", message = "mobile number can only contain numbers")
  private String mobileNumber;

  @NotBlank
  @Pattern(regexp = ".*\\S.*\\S.*", message = "address should contain at least 2 non-white-space characters")
  private String address;

  @NotBlank
  @ContractTypeConstraint
  private String contractType;

  @NotBlank
  @EmploymentTypeConstraint
  private String employmentType;

  public String getDepartment() {
    return department;
  }

  public String getRole() {
    return role;
  }

  @NotBlank
  @DepartmentTypeConstraint
  private String department;

  @NotBlank
  @Pattern(regexp = ".*\\S.*", message = "role should contain at least 1 non-white-space characters")
  private String role;

  @NotBlank
  @Pattern(regexp = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]).*", message = "date format should be yyyy-mm-dd")
  private String startDate;

  @Pattern(regexp = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]).*", message = "date format should be yyyy-mm-dd")
  private String finishDate;

  @Min(1)
  @Max(40)
  private int hoursPerWeek;

  @Override
  public String toString() {
    return "CreateEmployeeDTO [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
        + ", email=" + email + ", mobileNumber=" + mobileNumber + ", address=" + address + ", contractType="
        + contractType + ", employmentType=" + employmentType + ", startDate=" + startDate + ", finishDate="
        + finishDate + ", hoursPerWeek=" + hoursPerWeek + " ]";
  }

  public String getFinishDate() {
    return finishDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getContractType() {
    return contractType;
  }

  public String getEmploymentType() {
    return employmentType;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public String getAddress() {
    return address;
  }

  public int getHoursPerWeek() {
    return hoursPerWeek;
  }
}
