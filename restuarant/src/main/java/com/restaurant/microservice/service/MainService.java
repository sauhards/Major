package com.restaurant.microservice.service;


import com.restaurant.microservice.model.FoodItem;
import com.restaurant.microservice.model.Restaurant;
import com.restaurant.microservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class MainService {

	@Autowired
	PasswordEncoderService passwordEncoderService;

	@Autowired
	RestaurantRepository restaurantRepository;
		
	@Autowired
	MongoOperations mongoOperations;

	public String login(String id, String password) /*throws UsernameNotFoundException*/ {

//		String hashedPassword = passwordEncoderService.getEncodedPassword(password);

		Optional<Restaurant> user = restaurantRepository.findById(id);

		System.out.println(user);

		if (user.get() == null){
//			throw new UsernameNotFoundException("id is not correct!");
			return "no user found!";
		}

//		 todo if not approved can not login

		System.out.println(user.get().getPassword());
//		System.out.println(hashedPassword);
		if(user.get().getPassword().equals(password)){
			return "Successful login";
		}
		else{
			return "wrong user name or password!";
		}

	}

	public Object signup(Restaurant restaurant){



//		 id = email id logic


//		 mongodb create user
//		user.setPassword(passwordEncoderService.getEncodedPassword(user.getPassword()));
		restaurant.setStatus("NOT_APPROVED");
		return restaurantRepository.save(restaurant);

	}

	public Object addFoodItem(String id, FoodItem foodItem){
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		Restaurant restaurant1 = restaurant.get();
		restaurantRepository.deleteById(id);
		List<FoodItem> newFoodItemList;
		if(restaurant1.getFoodItemList() != null) {
			newFoodItemList = restaurant1.getFoodItemList();
		}
		else {
			newFoodItemList = new ArrayList<>();
		}
		newFoodItemList.add(foodItem);
		restaurant1.setFoodItemList(newFoodItemList);
		return restaurantRepository.save(restaurant1);
	}

	public Object getById(String id) {
		return restaurantRepository.findById(id).get();
	}
}