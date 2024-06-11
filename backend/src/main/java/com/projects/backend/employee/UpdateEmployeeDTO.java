package com.projects.backend.employee;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class UpdateEmployeeDTO {

  @Pattern(regexp = ".*\\S.*", message = "first name should be at least one character long")
  private String firstName;

  @Pattern(regexp = ".*\\S.*", message = "middle name should be at least one character long")
  private String middleName;

  @Pattern(regexp = ".*\\S.*", message = "last name should be at least one character long")
  private String lastName;

  @Email
  private String email;

  @Length(max = 20, min = 8)
  @Pattern(regexp = "\\d+", message = "mobile number can only contain numbers")
  private String mobileNumber;

  @Pattern(regexp = ".*\\S.*\\S.*", message = "address should contain at least 2 non-white-space characters")
  private String address;

  @ContractTypeConstraint
  private String contractType;
  
  @EmploymentTypeConstraint
  private String employmentType;

  @Pattern(regexp="^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]).*$",message="date format should be yyyy-mm-dd")
  private String startDate;

  @Pattern(regexp="^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]).*$",message="date format should be yyyy-mm-dd")
  private String finishDate;

  @Pattern(regexp="[1-9]|[1-3][0-9]|40",message="hours per week can be only be 1-40 inclusive")
  private String hoursPerWeek;
 
  @Override
  public String toString() {
    return "UpdateEmployeeDTO [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
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



  public String getHoursPerWeek() {
    return hoursPerWeek;
  }
}
