package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE e.lastName LIKE :prefix%")

    Page<Employee> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    List<Employee> findByLastNameStartingWith(String prefix);

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByLastName(String lastName);

    List<Employee> findByFirstNameIgnoreCase(String firstName);

    List<Employee> findByBirthDateBetween(Date from, Date to);

    @Query("SELECT e FROM Employee e WHERE e.birthDate BETWEEN :from AND :to")
    List<Employee> findNByBirthDateBetween(@Param("from") Date from,
                                           @Param("to") Date to,
                                           Pageable pageable);

}
