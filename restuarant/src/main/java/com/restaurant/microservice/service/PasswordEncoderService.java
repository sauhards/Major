package com.restaurant.microservice.service;


import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class PasswordEncoderService {

	PasswordEncoder passwordEncoder;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	public String getEncodedPassword(String password){
		this.passwordEncoder = getPasswordEncoder();
		return passwordEncoder.encode(password);
	}

}
