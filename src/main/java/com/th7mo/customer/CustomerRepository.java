package com.th7mo.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface CustomerRepository extends Repository<Customer, Long> {
	
	Customer save(Customer customer);
	List<Customer> findAll();
	Optional<Customer> findById(Long customerId);
	Optional<Customer> findByUsername(String username);
}
