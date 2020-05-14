package com.admin.microservice.service;


import com.admin.microservice.model.Restaurant;
import com.admin.microservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class MainService {

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	RestaurantRepository restaurantRepository;

	public String login(String id, String password) /*throws UsernameNotFoundException*/ {

//		String hashedPassword = passwordEncoderService.getEncodedPassword(password);

		Optional<Restaurant> user = restaurantRepository.findById(id);

		System.out.println(user);

		if (user.get() == null){
//			throw new UsernameNotFoundException("id is not correct!");
			return "no user found!";
		}


		System.out.println(user.get().getPassword());
		if(user.get().getPassword().equals(password)){
			return "Successful login! Welcome Admin";
		}
		else{
			return "wrong user name or password!";
		}

	}

	public List<Restaurant> getAllPendingRestaurants(){
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is("NOT_APPROVED"));
		List<Restaurant> restaurants = mongoOperations.find(query, Restaurant.class);
		return restaurants;
	}

	public Object getAllRestaurants(){
		// todo food is missing
		return restaurantRepository.findAll();
	}


	public Restaurant approveRestaurant(String id) {

		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		restaurantRepository.deleteById(id);
		Restaurant restaurant1 = restaurant.get();
		restaurant1.setStatus("APPROVED");
		return restaurantRepository.save(restaurant1);
	}


	public Restaurant rejectRestaurant(String id) {

		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		restaurantRepository.deleteById(id);
		Restaurant restaurant1 = restaurant.get();
		restaurant1.setStatus("REJECTED");
		return restaurantRepository.save(restaurant1);
	}
}