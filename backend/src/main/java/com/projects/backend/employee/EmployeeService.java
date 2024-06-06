package com.projects.backend.employee;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeeService {
  @Autowired
  EmployeeRepository repo;

  @Autowired
  ModelMapper mapper;

  public List<Employee> findAll() {
    return this.repo.findAll();
  }

  public Optional<Employee> findById(Long id) {
    return this.repo.findById(id);
  }

  public boolean deleteById(Long id) {
    Optional<Employee> maybeEmployee = this.repo.findById(id);
    if(maybeEmployee.isEmpty()) return false;
    this.repo.delete(maybeEmployee.get());
    return true;  
  }

  public Employee create(CreateEmployeeDTO data) {
    Employee newEmployee = mapper.map(data,Employee.class);
    this.repo.save(newEmployee);    
    return newEmployee;
    
  }

  public Optional<Employee> updateById(Long id, UpdateEmployeeDTO data) {
    Optional<Employee> maybeEmployee = this.repo.findById(id);
    if(maybeEmployee.isPresent()) {
      Employee foundEmployee = maybeEmployee.get();
      mapper.map(data,foundEmployee);
      return Optional.of(this.repo.save(foundEmployee));
    }
    return maybeEmployee;
  }
  
  
}
