package com.projects.backend.employee;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.projects.backend.exceptions.NotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  @Autowired
  EmployeeService employeeService;

  @GetMapping()
  public ResponseEntity<Page<Employee>> findAllEmployees(@RequestParam String name, @RequestParam String department,
      @RequestParam String employmentType, @RequestParam String contractType, @RequestParam int page,
      @RequestParam(required = true, name = "sort") String sort) {
    Page<Employee> allEmployees = this.employeeService.findAll(name, department, employmentType, contractType, page,
        sort);
    return new ResponseEntity<>(allEmployees, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> findEmployeeById(@PathVariable Long id) throws NotFoundException {
    Optional<Employee> maybeEmployee = this.employeeService.findById(id);
    Employee foundEmployee = maybeEmployee.orElseThrow(() -> new NotFoundException(Employee.class.getSimpleName(), id));
    return new ResponseEntity<>(foundEmployee, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeDTO data)
      throws NotFoundException, BadRequestException {
    Optional<Employee> maybeEmployee;
    try {
      maybeEmployee = this.employeeService.updateById(id, data);
    } catch (ValidationException ve) {
      throw new BadRequestException(ve.getMessage());
    }
    Employee updatedEmployee = maybeEmployee
        .orElseThrow(() -> new NotFoundException(Employee.class.getSimpleName(), id));
    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) throws NotFoundException {
    boolean success = this.employeeService.deleteById(id);
    if (!success) {
      throw new NotFoundException(Employee.class.getSimpleName(), id);
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping()
  public ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateEmployeeDTO data)
      throws BadRequestException {
    Optional<Employee> mayBeEmployee;
    try {
      mayBeEmployee = this.employeeService.create(data);
      if (mayBeEmployee.isEmpty()) {
        throw new BadRequestException("Department name is invalid");
      }
    } catch (ValidationException ve) {
      throw new BadRequestException(ve.getMessage());
    }
    Employee createdEmployee = mayBeEmployee.get();
    return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
  }
}
