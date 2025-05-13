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
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/get")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam Integer id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/post")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/upd")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/del")
    public void delEmployee(@RequestParam Integer index) {
        employeeRepository.deleteById(index);
    }

    @GetMapping(value = "{employeeId}")
    public Optional<Employee> getId (@PathVariable("employeeId") Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @GetMapping("/add")
    public Employee addEmployee(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        return employeeRepository.save(employee);
    }

    @GetMapping("/update")
    public ResponseEntity<Employee> updateEmployee(
            @RequestParam Integer id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName
    ) {
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Employee employee = optional.get();
        if (firstName != null) employee.setFirstName(firstName);
        if (lastName != null) employee.setLastName(lastName);
        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteEmployee(@RequestParam Integer id) {
        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pracownik o ID " + id + " nie istnieje.");
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.ok("Pracownik usunięty.");
    }

    @GetMapping("/starts-with")
    public List<Employee> getEmployeesWithLastNameStartingWith(@RequestParam String prefix) {
        List<Employee> employees = employeeRepository.findByLastNameStartingWith(prefix);

        System.out.println("==== PRACOWNICY Z PREFIXEM '" + prefix + "' ====");
        for (Employee e : employees) {
            System.out.println(e);
        }

        return employees;
    }


}
