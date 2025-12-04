package com.th7mo.customer;

import java.util.List;

import com.th7mo.shopping_cart.ShoppingCart;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Customer post(Customer customer) {
		ShoppingCart shoppingCart = new ShoppingCart();
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		customer.setShoppingCart(shoppingCart);
		customer.setRole(Role.CUSTOMER);
		return customerRepository.save(customer);
	}
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	public Customer getCustomer(Long customerId) {
		return customerRepository.findById(customerId).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
		);
	}
	
	public Customer getCustomerByUsername(String username) {
		return customerRepository.findByUsername(username).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
		);
	}
}
