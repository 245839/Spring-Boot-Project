package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Employee;
import com.example.springbootproject.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import com.example.springbootproject.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @Autowired
    private EmployeeRepository employeeRepository;

    // GET /api/employees?page=0&size=10&sort=lastName,asc
    @GetMapping
    public Page<Employee> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    // GET /api/employees/search?lastName=Smith&page=0&size=10&sort=firstName,desc
    @GetMapping("/search")
    public Page<Employee> searchByLastName(
            @RequestParam String lastName,
            Pageable pageable) {
        return service.findByLastName(lastName, pageable);
    }

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

    @GetMapping("/nameIC/{firstName}")
    public List<Employee> getEmployeeByFirstNameIC(@PathVariable("firstName") String firstName) {
        return employeeRepository.findByFirstNameIgnoreCase(firstName);
    }

    @GetMapping("/surname/{lastName}")
    public List<Employee> getEmployeeByLastName(@PathVariable("lastName") String lastName) {
        return employeeRepository.findByLastName(lastName);
    }

    @GetMapping("/birthdate/{dateFrom}/{dateTo}")
    public List<Employee> getEmployeeByBirthDate(@PathVariable("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom, @PathVariable("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
        return employeeRepository.findByBirthDateBetween(dateFrom, dateTo);
    }

    @GetMapping("/topN-by-birthdate/{dateFrom}/{dateTo}/{Nvalue}")
    public List<Employee> getTopNByBirthDateBetween(
            @PathVariable("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateFrom,
            @PathVariable("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateTo,
            @PathVariable("Nvalue") int Nvalue
    ) {
        Pageable pageable = PageRequest.of(0, Nvalue);
        return employeeRepository.findNByBirthDateBetween(dateFrom, dateTo, pageable);
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
