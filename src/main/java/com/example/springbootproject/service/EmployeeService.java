package com.example.springbootproject.service;

import com.example.springbootproject.entity.Employee;
import com.example.springbootproject.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    public Page<Employee> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Employee> findByLastName(String lastName, Pageable pageable) {
        return repo.findByLastNameContainingIgnoreCase(lastName, pageable);
    }
}