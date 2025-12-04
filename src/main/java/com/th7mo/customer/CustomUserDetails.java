package com.th7mo.customer;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private final Customer customer;

	public CustomUserDetails(Customer customer) {
		this.customer = customer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String roleName = "ROLE_" + customer.getRole().name();
		return List.of(new SimpleGrantedAuthority(roleName));
	}

	@Override
	public String getUsername() {
		return customer.getUsername();
	}

	@Override
	public String getPassword() {
		return customer.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() { return true; }

	@Override
	public boolean isAccountNonLocked() { return true; }

	@Override
	public boolean isCredentialsNonExpired() { return true; }

	@Override
	public boolean isEnabled() { return true; }
}
