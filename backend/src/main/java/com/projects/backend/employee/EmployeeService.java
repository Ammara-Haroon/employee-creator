package com.projects.backend.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.projects.backend.department.Department;
import com.projects.backend.department.DepartmentService;
import com.projects.backend.factory.EmployeeCreator;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
@Transactional
public class EmployeeService {
  @Autowired
  EmployeeRepository repo;

  @Autowired
  DepartmentService departmentService;

  @Autowired
  ModelMapper mapper;

  public List<Employee> findAll() {
    return this.repo.findAll();
  }

  public Page<Employee> findAll(String name, String department, String employmentType, String contractType, int page,
      String sortType) {
    
    int NUMBER_OF_EMPLOPYEES_PER_PAGE = 20;

    Sort sort;
    if (sortType.equals(SortType.DESC.label)) {
      sort = Sort.by("firstName").descending()
          .and(Sort.by("middleName").descending())
          .and(Sort.by("lastName").descending());
    } else {
      sort = Sort.by("firstName").ascending()
          .and(Sort.by("middleName").ascending())
          .and(Sort.by("lastName").ascending());
    }
    Pageable pageable = PageRequest.of(page, NUMBER_OF_EMPLOPYEES_PER_PAGE).withSort(sort);

    Collection<String> et = Arrays.asList(employmentType.split(","));
    Collection<String> ct = Arrays.asList(contractType.split(","));
    Collection<String> dept = Arrays.asList(department.split(","));

    return this.repo.findByQueryParams(et, ct, dept, name, pageable);

  }

  public Optional<Employee> findById(Long id) {
    return this.repo.findById(id);
  }

  public boolean deleteById(Long id) {
    Optional<Employee> maybeEmployee = this.repo.findById(id);
    if (maybeEmployee.isEmpty())
      return false;
    this.repo.delete(maybeEmployee.get());
    return true;
  }

  public Optional<Employee> create(CreateEmployeeDTO data) throws ValidationException {
    List<Department> deptList = this.departmentService.findByName(data.getDepartment());

    if (deptList.size() == 0) {
      return Optional.empty();
    }

    Employee newEmployee = mapper.map(data, Employee.class);
    newEmployee.setDepartment(deptList.get(0));
    if (newEmployee.getFinishDate() != null && newEmployee.getStartDate().compareTo(newEmployee.getFinishDate()) > 0) {
      throw new ValidationException("finish date cannot be before start date");
    }

    return Optional.of(this.repo.save(newEmployee));
  }

  public Optional<Employee> updateById(Long id, UpdateEmployeeDTO data) throws ValidationException {
    Optional<Employee> maybeEmployee = this.repo.findById(id);
    if (maybeEmployee.isEmpty()) {
      return Optional.empty();
    }
    List<Department> deptList = this.departmentService.findByName(data.getDepartment());
    if (deptList.size() == 0) {
      return Optional.empty();
    }

    Employee foundEmployee = maybeEmployee.get();
    mapper.map(data, foundEmployee);
    foundEmployee.setDepartment(deptList.get(0));

    if (foundEmployee.getFinishDate() != null
        && foundEmployee.getStartDate().compareTo(foundEmployee.getFinishDate()) > 0) {
      throw new ValidationException("finish date cannot be before start date");
    }
    return Optional.of(this.repo.save(foundEmployee));

  }

  public void setUpEmployeeDatabase() {
    new EmployeeCreator(this.repo);
  }

}
