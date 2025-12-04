package com.th7mo.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

	@Value("${JWT.token.secret}")
	private String JWT_TOKEN_SECRET;

	private SecretKey key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(JWT_TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String username, Collection role, Long customerId) {
		return Jwts.builder()
			.subject(username)
			.claim("role", role)
			.claim("id", customerId)
			.issuedAt(new Date())
			.expiration(getExpirationDate())
			.signWith(key)
			.compact();
	}
	
	private Date getExpirationDate() {
		return new Date((new Date()).getTime() + 60 * 60 * 1000);
	}

	public String retrieveUsernameFromJwtToken(String jwtToken) {
		return Jwts.parser().verifyWith(key).build()
			.parseSignedClaims(jwtToken)
			.getPayload()
			.getSubject();
	}

	public boolean isValidJwtToken(String jwtToken) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken);
			return true;
		} catch (Exception e) {
			log.error("JWT validation error: {}", e.getMessage());
		}
		return false;
	}
}
