package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Employee;
import com.example.springbootproject.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{employeeId}")
    public Optional<Employee> getEmployeeById(@PathVariable("employeeId") Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @GetMapping("/name/{firstName}")
    public List<Employee> getEmployeeByFirstName(@PathVariable("firstName") String firstName) {
        return employeeRepository.findByFirstName(firstName);
    }

    @GetMapping("/surname/{lastName}")
    public List<Employee> getEmployeeByLastName(@PathVariable("lastName") String lastName) {
        return employeeRepository.findByLastName(lastName);
    }

    @GetMapping("/prefix/{lastNamePrefix}")
    public List<Employee> getEmployeesWithLastNameStartingWith(@PathVariable("lastNamePrefix") String lastNamePrefix) {
        List<Employee> employees = employeeRepository.findByLastNameStartingWith(lastNamePrefix);

        System.out.println("==== PRACOWNICY Z PREFIXEM '" + lastNamePrefix + "' ====");
        for (Employee e : employees) {
            System.out.println(e);
        }

        return employees;
    }

    @PostMapping("/{firstName}/{lastName}")
    public Employee addEmployee(
            @PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName
    ) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        return employeeRepository.save(employee);
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/update")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Integer employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pracownik o ID " + employeeId + " nie istnieje.");
        }
        employeeRepository.deleteById(employeeId);
        return ResponseEntity.ok("Pracownik usunięty.");
    }
}
