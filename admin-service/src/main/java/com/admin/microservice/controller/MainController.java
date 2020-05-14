package com.admin.microservice.controller;


import com.admin.microservice.model.Restaurant;
import com.admin.microservice.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {

	@Autowired
	MainService mainService;


	@GetMapping("/login")
	public String login(@RequestParam String id, @RequestParam String password)/* throws UsernameNotFoundException*/ {

		String resp =  mainService.login(id, password);

		return resp;
	}


	@GetMapping("/pending-restaurants")
	public List<Restaurant> getPendingRestaurants(){
		return mainService.getAllPendingRestaurants();
	}

	@GetMapping("/all-restaurants")
	public Object getAllRestaurants(){

		return mainService.getAllRestaurants();
	}

	@PostMapping("/approve")
	public Restaurant approveRestaurant(@RequestParam String id){

		return mainService.approveRestaurant(id);
	}

	@PostMapping("/reject")
	public Restaurant rejectRestaurant(@RequestParam String id){

		return mainService.rejectRestaurant(id);
	}
}
