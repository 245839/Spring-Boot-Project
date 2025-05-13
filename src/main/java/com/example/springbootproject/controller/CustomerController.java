package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Customer;
import com.example.springbootproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{customerId}")
    public Optional<Customer> getCustomerById(@PathVariable Integer customerId) {
        return customerRepository.findById(customerId);
    }

    @GetMapping("/contact/{contactName}")
    public List<Customer> getCustomerByContactName(@PathVariable String contactName) {
        return customerRepository.findByContactName(contactName);
    }

    @GetMapping("/country/{country}")
    public List<Customer> getCustomerByCountry(@PathVariable String country) {
        return customerRepository.findByCountry(country);
    }

    @GetMapping("/country-city/{country}/{city}")
    public List<Customer> getCustomerByCountryAndCity(@PathVariable String country, @PathVariable String city) {
        return customerRepository.findByCountryAndCity(country, city);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        if (customer.getCustomerName() == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nie podano nazwy klienta.");
        } else if (customer.getContactName() == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nie podano nazwy kontaktowej klienta.");
        } else if (customer.getCountry() == null || customer.getCity() == null || customer.getAddress() == null || customer.getPostalCode() == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nie podano danych adresowych klienta.");
        }
        customerRepository.save(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body("Utworzono nowego klienta o nr ID: " + customer.getCustomerID() + " i nazwie: " + customer.getCustomerName());
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable Integer customerId, @RequestParam(required = false) String customerName, @RequestParam(required = false) String contactName, @RequestParam(required = false) String country, @RequestParam(required = false) String city, @RequestParam(required = false) String address, @RequestParam(required = false) String postalCode) {
        Optional<Customer> optional = customerRepository.findById(customerId);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono klienta o nr ID: " + customerId);
        }

        Customer customer = optional.get();
        if (customerName != null) {
            customer.setCustomerName(customerName);
        }

        if (contactName != null) {
            customer.setContactName(contactName);
        }

        if (country != null) {
            customer.setCountry(country);
        }

        if (city != null) {
            customer.setCity(city);
        }

        if (address != null) {
            customer.setAddress(address);
        }

        if (postalCode != null) {
            customer.setPostalCode(postalCode);
        }

        customerRepository.save(customer);

        return ResponseEntity.status(HttpStatus.OK).body("Prawidłowo zaktualizowano klienta o nr ID: " + customerId);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Klient o ID " + customerId + " nie istnieje.");
        }
        customerRepository.deleteById(customerId);
        return ResponseEntity.ok("Klient został usunięty.");
    }
}
