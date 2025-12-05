package com.th7mo.auth;
import com.th7mo.customer.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final CustomUserDetailsService userDetailsService;
	private final AuthEntryPointJwt unauthorizedHandler;
	private final AuthTokenFilter authenticationJwtTokenFilter;

	@Bean
	public AuthenticationManager authenticationManager(
		AuthenticationConfiguration authenticationConfiguration
	) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(_ -> {})
			.csrf(AbstractHttpConfigurer::disable)
			.exceptionHandling(e ->
				e.authenticationEntryPoint(unauthorizedHandler)
			)
			.sessionManagement(s ->
				s.sessionCreationPolicy(
					org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
			)
			.authorizeHttpRequests(a -> a
				.requestMatchers(HttpMethod.POST, "/api/login").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/customers").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/tracks").permitAll()

				.requestMatchers(HttpMethod.GET, "/api/customers/{id}")
				.hasAnyRole("ADMIN", "CUSTOMER")

				.requestMatchers(HttpMethod.DELETE, "/api/customers/{customerId}/cart/items/{trackId}")
				.hasAnyRole("ADMIN", "CUSTOMER")

				.requestMatchers(HttpMethod.DELETE, "/api/customers/{customerId}/cart")
				.hasAnyRole("ADMIN", "CUSTOMER")

				.requestMatchers(HttpMethod.POST, "/api/customers/{customerId}/cart/items/{trackId}")
				.hasAnyRole("ADMIN", "CUSTOMER")

				.requestMatchers(HttpMethod.GET, "/api/genres", "/api/tracks/{id}")
				.hasAnyRole("ADMIN", "CUSTOMER")

				.anyRequest().hasRole("ADMIN")
			);

		http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:4200");
		config.addAllowedOrigin("https://trackshop.th7mo.com");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

}