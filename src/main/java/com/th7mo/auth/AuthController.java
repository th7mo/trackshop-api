package com.th7mo.auth;

import com.th7mo.customer.Customer;
import com.th7mo.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final CustomerService customerService;

	@PostMapping("/login")
	public String login(@RequestBody Customer customer) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword())
		);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Long customerId = customerService.getCustomerByUsername(userDetails.getUsername()).getId();
		return jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities(), customerId);
	}
}
