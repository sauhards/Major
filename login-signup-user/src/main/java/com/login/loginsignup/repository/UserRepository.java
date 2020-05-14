package com.login.loginsignup.repository;

import com.login.loginsignup.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface UserRepository extends MongoRepository<User, String> {

	// public User findbyId(String id);

	// public User findbyEmail(String email);

}