package com.angboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.angboot.app.dto.AuthRequest;
import com.angboot.app.dto.AuthResponse;
import com.angboot.app.model.User;
import com.angboot.app.model.UserCredential;
import com.angboot.app.service.JwtService;
import com.angboot.app.service.UserDetailsServiceImpl;
import com.angboot.app.service.UserServiceImpl;

@RestController
public class RequestHandler {
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	UserServiceImpl userServiceImpl;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String jwt = jwtService.generateToken(userDetails);

		return ResponseEntity.ok(new AuthResponse(jwt));
	}

	@PostMapping("/registration")
	public ResponseEntity<?> doRegister(@RequestBody User user) {
		User response = userServiceImpl.doRegister(user);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/home")
	public ResponseEntity<?> getUserDetails() {
		List<User> userList = userServiceImpl.getUserDetails();
		return ResponseEntity.ok(userList);
	}
	
	
	// test apis
	@GetMapping("/getfromcred")
	public ResponseEntity<?> getFromUserDetails() {
		List<UserCredential> userList = userServiceImpl.getFromUserDetails();
		return ResponseEntity.ok(userList);
	}
	
	@GetMapping("/user/profile")
	public ResponseEntity<?> getUserProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
		User userList = userServiceImpl.getUserProfile(userName);
		return ResponseEntity.ok(userList);
	}

}
