package com.restaurant.microservice.controller;


import com.restaurant.microservice.model.FoodItem;
import com.restaurant.microservice.model.Restaurant;
import com.restaurant.microservice.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/addFoodItem")
	public Object addFodItem( @RequestParam String id, @RequestBody FoodItem foodItem){
		return mainService.addFoodItem(id, foodItem);
	}

	@PostMapping("/register")
	public Object register(@RequestBody Restaurant restaurant){

		Object obj = mainService.signup(restaurant);
		return obj;
	}

	@GetMapping("/getById")
	public Object getById(@RequestParam String id){

		Object obj = mainService.getById(id);
		return obj;
	}

}
