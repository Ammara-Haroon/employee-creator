package com.projects.backend.department;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DepartmentService {
  @Autowired
  private ModelMapper mapper;

  @Autowired
  private DepartmentRepository repo;

  public Department create(CreateDepartmentDTO data) {
    Department newDepartment = mapper.map(data, Department.class);
    return this.repo.save(newDepartment);
  }

  public List<Department> findAll() {
    if (this.repo.count() == 0)
      setupDefaultDepartments();

    return this.repo.findAll(Sort.by("name").ascending());
  }

  public Optional<Department> findById(Long id) {
    return this.repo.findById(id);
  }

  private void setupDefaultDepartments() {
    String[] defaultDepartments = { "ADMIN", "FINANCE", "IT" };
    for (int i = 0; i < defaultDepartments.length; ++i) {
      Department newDepartment = new Department();
      newDepartment.setName(defaultDepartments[i]);
      this.repo.save(newDepartment);
    }
  }

  public List<Department> findByName(String name) {
    return this.repo.findByName(name);
  }

  public boolean deleteById(Long id) {
    Optional<Department> maybeDepartment = this.findById(id);
    if (maybeDepartment.isEmpty()) {
      return false;
    }
    this.repo.delete(maybeDepartment.get());
    return true;
  }

  public Optional<Department> updateById(Long id, UpdateDepartmentDTO data) {
    Optional<Department> maybeDepartment = this.findById(id);
    if (maybeDepartment.isEmpty()) {
      return maybeDepartment;
    }

    Department foundDepartment = maybeDepartment.get();
    mapper.map(data, foundDepartment);
    Department updatedDepartment = this.repo.save(foundDepartment);
    return Optional.of(updatedDepartment);
  }

}
