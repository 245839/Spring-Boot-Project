package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE e.lastName LIKE :prefix%")
    List<Employee> findByLastNameStartingWith(String prefix);

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByLastName(String lastName);
}
