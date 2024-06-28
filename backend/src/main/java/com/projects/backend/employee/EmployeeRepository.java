package com.projects.backend.employee;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
        @Query("SELECT e FROM Employee e WHERE  e.employmentType IN ?1 AND e.contractType IN ?2 AND e.department.name IN ?3 AND "
                        + "(e.firstName LIKE %?4% OR  e.middleName LIKE %?4% OR e.lastName  LIKE %?4%)")
        Page<Employee> findByQueryParams(Collection<String> employmentType, Collection<String> contractType,
                        Collection<String> department, String name, Pageable pageable);
}
