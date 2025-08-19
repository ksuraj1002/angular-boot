package com.angboot.app.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secretKeyString;

	private Key key;

	@PostConstruct
	public void init() {
		byte[] keyBytes = Decoders.BASE64URL.decode(secretKeyString);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        // Add more user details as needed, e.g., roles
        if (userDetails.getAuthorities() != null) {
            claims.put("roles", userDetails.getAuthorities().stream()
                    .map(Object::toString)
                    .toList());
        }
		 String token = Jwts.builder()
                .setClaims(claims) // Add custom claims
                .setSubject(userDetails.getUsername()) // Set subject (optional, if you want it separate from claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60)) // 10 hours
                .signWith(this.key, SignatureAlgorithm.HS256) // Sign with the key
                .compact();
		 return token;
	}

	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(secretKeyString).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		Date expiration = Jwts.parser().setSigningKey(secretKeyString).parseClaimsJws(token).getBody().getExpiration();
		return expiration.before(new Date());
	}
}
