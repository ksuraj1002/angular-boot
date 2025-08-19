package com.angboot.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angboot.app.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserCredential_Username(String userName);

}
