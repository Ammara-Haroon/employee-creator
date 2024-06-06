package com.projects.backend.employee;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "employee")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = true)
  private String middleName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false, length = 20)
  private String mobileNumber;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ContractType contractType;
  
  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Column(nullable = true)
  @Temporal(TemporalType.DATE)
  private Date finishDate;
  
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private EmploymentType employmentType;
  
  @Column(nullable = false)
  private int hoursPerWeek;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public ContractType getContractType() {
    return contractType;
  }

  public void setContractType(ContractType contractType) {
    this.contractType = contractType;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getFinishDate() {
    return finishDate;
  }

  public void setFinishDate(Date finishDate) {
    this.finishDate = finishDate;
  }

  public EmploymentType getEmploymentType() {
    return employmentType;
  }

  public void setEmploymentType(EmploymentType employmentType) {
    this.employmentType = employmentType;
  }

  public int getHoursPerWeek() {
    return hoursPerWeek;
  }

  public void setHoursPerWeek(int hoursPerWeek) {
    this.hoursPerWeek = hoursPerWeek;
  }

  @Override
  public String toString() {
    return "Employee [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
        + ", email=" + email + ", mobileNumber=" + mobileNumber + ", address=" + address + ", contractType="
        + contractType + ", startDate=" + startDate + ", finsihDate=" + finishDate+ ", employmentType="
        + employmentType + ", hoursPerWeek=" + hoursPerWeek + "]";
  }
}
