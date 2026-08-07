package com.mayur.taskflowai.repository;

import com.mayur.taskflowai.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCaseOrDesignationContainingIgnoreCase(
            String firstName,
            String lastName,
            String email,
            String department,
            String designation
    );

    List<Employee> findByDepartmentIgnoreCase(String department);

    List<Employee> findByAvailability(Boolean availability);

    List<Employee> findByDepartmentIgnoreCaseAndAvailability(
            String department,
            Boolean availability
    );
}