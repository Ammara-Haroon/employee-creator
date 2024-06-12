package com.projects.backend.employee;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.projects.backend.exceptions.NotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
  @Autowired
  EmployeeService employeeService;

  @GetMapping()
  public ResponseEntity<List<Employee>> findAllEmployees() {
    List<Employee> allEmployees = this.employeeService.findAll();
    return new ResponseEntity<>(allEmployees, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> findEmployeeById(@PathVariable Long id) throws NotFoundException {
    Optional<Employee> maybeEmployee = this.employeeService.findById(id);
    Employee foundEmployee = maybeEmployee.orElseThrow(() -> new NotFoundException(Employee.class.getSimpleName(),id));
    return new ResponseEntity<>(foundEmployee, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeDTO data)
      throws NotFoundException, BadRequestException{
    System.out.println(data);    
    Optional<Employee> maybeEmployee;
    try {
      maybeEmployee = this.employeeService.updateById(id, data);
    } catch(ValidationException ve){
      throw new BadRequestException(ve.getMessage());
    }
    Employee updatedEmployee = maybeEmployee.orElseThrow(()->new NotFoundException(Employee.class.getSimpleName(), id));
    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) throws NotFoundException {
    boolean success = this.employeeService.deleteById(id);
    if(!success){
      throw new NotFoundException(Employee.class.getSimpleName(),id);
    }
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @PostMapping()
  public ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateEmployeeDTO data) throws BadRequestException {
    Optional<Employee> mayBeEmployee;
    try{
    mayBeEmployee = this.employeeService.create(data);
   
  }catch(ValidationException ve)
  {
      throw new BadRequestException(ve.getMessage());
  }
    Employee createdEmployee = mayBeEmployee.get();
    return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
  }
}
