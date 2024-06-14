package com.projects.backend.employee;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
@Transactional
public class EmployeeService {
  @Autowired
  EmployeeRepository repo;

  @Autowired
  ModelMapper mapper;

  public Page<Employee> findAll(int page) {
    Sort sort = Sort.by("firstName").ascending()
  .and(Sort.by("middleName").ascending())
  .and(Sort.by("lastName").ascending())
  ;
    Pageable pageable = PageRequest.of(page,2).withSort(sort);
    return this.repo.findAll(pageable);
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

  public Optional<Employee> create(CreateEmployeeDTO data) throws ValidationException {
    Employee newEmployee = mapper.map(data,Employee.class);
    if(newEmployee.getFinishDate() != null && newEmployee.getStartDate().compareTo(newEmployee.getFinishDate()) > 0){
      throw new ValidationException("finish date cannot be before start date");
    }
    
    return Optional.of(this.repo.save(newEmployee));    
  }

  public Optional<Employee> updateById(Long id, UpdateEmployeeDTO data) throws ValidationException{
    Optional<Employee> maybeEmployee = this.repo.findById(id);
    if(maybeEmployee.isPresent()) {
      Employee foundEmployee = maybeEmployee.get();
      mapper.map(data,foundEmployee);
      if(foundEmployee.getFinishDate() != null && foundEmployee.getStartDate().compareTo(foundEmployee.getFinishDate()) > 0){
      throw new ValidationException("finish date cannot be before start date");
    }
      return Optional.of(this.repo.save(foundEmployee));
    }
    return maybeEmployee;
  }
  
  
}
