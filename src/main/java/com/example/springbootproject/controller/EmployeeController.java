package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Employee;
import com.example.springbootproject.repository.EmployeeRepository;
import com.example.springbootproject.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // GET /api/employees/all
    @GetMapping("/all")
    public List<Employee> getAllUnpaged() {
        return employeeRepository.findAll();
    }

    // GET /api/employees/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Integer id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/employees
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // PUT /api/employees/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Integer id,
            @RequestBody Employee updated) {
        return employeeRepository.findById(id)
                .map(emp -> {
                    emp.setFirstName(updated.getFirstName());
                    emp.setLastName(updated.getLastName());
                    return ResponseEntity.ok(employeeRepository.save(emp));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/employees/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.ok("Pracownik usunięty.");
    }

    // GET /api/employees/starts-with?prefix=Sm
    @GetMapping("/starts-with")
    public List<Employee> getStartsWith(@RequestParam String prefix) {
        return employeeRepository.findByLastNameStartingWith(prefix);
    }
}
