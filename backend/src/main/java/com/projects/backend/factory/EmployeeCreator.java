package com.projects.backend.factory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.projects.backend.department.Department;
import com.projects.backend.department.DepartmentService;
import com.projects.backend.employee.ContractType;
import com.projects.backend.employee.DepartmentType;
import com.projects.backend.employee.Employee;
import com.projects.backend.employee.EmployeeRepository;
import com.projects.backend.employee.EmploymentType;

import net.datafaker.Faker;

public class EmployeeCreator {

  public EmployeeCreator(EmployeeRepository repo,List<Department> departments) {
    Faker faker = new Faker();
    for (int i = 0; i < 25; ++i) {
      Employee emp = new Employee();
      emp.setAddress(faker.address().streetAddress());
      emp.setContractType(faker.options().option(ContractType.class));
      emp.setFirstName(faker.name().firstName());
      emp.setMiddleName(faker.name().firstName());
      emp.setLastName(faker.name().lastName());
      emp.setEmail(emp.getFirstName() + "@xmail.com");
      emp.setEmploymentType(faker.options().option(EmploymentType.class));
      emp.setFinishDate(null);
      emp.setStartDate(faker.date().past(500, TimeUnit.DAYS));
      emp.setHoursPerWeek(faker.number().numberBetween(20, 40));
      emp.setMobileNumber("041" + faker.phoneNumber().subscriberNumber(7));
      emp.setRole(faker.job().title());
      emp.setDepartment(departments.get(i%departments.size()));
      System.out.println(emp);
      repo.save(emp);
    }
  }
}
