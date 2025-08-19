package com.angboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.angboot.app.model.Roles;
import com.angboot.app.model.User;
import com.angboot.app.model.UserCredential;
import com.angboot.app.repository.UserCredentialRepository;
import com.angboot.app.repository.UserRepository;

@Service
public class UserServiceImpl {
	@Autowired
	UserRepository userRepository;
	
	@Autowired UserCredentialRepository userCredRepo;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public User doRegister(User user) {
		UserCredential userCred = user.getUserCredential();
		userCred.setPassword(passwordEncoder.encode(userCred.getPassword()));
		userCred.setRoles(Roles.USER);

		user.setUserCredential(userCred);
		return userRepository.save(user);
	}

	public List<User> getUserDetails() {
		return userRepository.findAll();
	}

	
	// test apis
	public List<UserCredential> getFromUserDetails() {
		return userCredRepo.findAll();
	} 

	public User getUserProfile(String userName) {
		return userRepository.findByUserCredential_Username(userName);
	}

}
