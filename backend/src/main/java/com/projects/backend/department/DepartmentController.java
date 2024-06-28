package com.projects.backend.department;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.projects.backend.exceptions.NotFoundException;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

  @Autowired
  DepartmentService departmentService;

  @PostMapping()
  public ResponseEntity<Department> createDepartment(@Valid @RequestBody CreateDepartmentDTO data) {
    Department createdDepartment = this.departmentService.create(data);
    return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<List<Department>> findAllDepartments() {
    List<Department> allDepartments = this.departmentService.findAll();
    return new ResponseEntity<>(allDepartments, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Department> findDepartmentById(@PathVariable Long id) throws NotFoundException {
    Optional<Department> maybeDepartment = this.departmentService.findById(id);
    Department foundDepartment = maybeDepartment
        .orElseThrow(() -> new NotFoundException(Department.class.getSimpleName(), id));
    return new ResponseEntity<>(foundDepartment, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Department> updateDepartmentById(@PathVariable Long id,
      @Valid @RequestBody UpdateDepartmentDTO data)
      throws NotFoundException {
    Optional<Department> maybeDepartment = this.departmentService.updateById(id, data);
    Department updatedDepartment = maybeDepartment
        .orElseThrow(() -> new NotFoundException(Department.class.getSimpleName(), id));
    return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDepartmentById(@PathVariable Long id) throws NotFoundException {
    if (!this.departmentService.deleteById(id))
      throw new NotFoundException(Department.class.getSimpleName(), id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
