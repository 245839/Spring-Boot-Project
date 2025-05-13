package com.example.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springbootproject.entity.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByContactName(String contactName);

    List<Customer> findByCountry(String country);

    List<Customer> findByCountryAndCity(String country, String city);

//    @Query("SELECT c.customerID FROM Customer c ORDER BY 1 DESC LIMIT 1")
//    Integer getLastCustomerID();
}