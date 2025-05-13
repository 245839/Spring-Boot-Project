package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Page<Employee> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
    List<Employee> findByLastNameStartingWith(String prefix);
}
