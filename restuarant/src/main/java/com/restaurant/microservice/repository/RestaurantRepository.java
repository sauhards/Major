package com.restaurant.microservice.repository;

import com.restaurant.microservice.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

	// public User findbyId(String id);

	// public User findbyEmail(String email);

}