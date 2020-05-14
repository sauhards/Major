package com.food.search.foodsearch.controller;


import com.food.search.foodsearch.model.FoodItem;
import com.food.search.foodsearch.model.FoodItemDTO;
import com.food.search.foodsearch.service.FoodCRUDService;
import com.food.search.foodsearch.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {


	@Autowired
	SearchService searchService;

	@Autowired
	FoodCRUDService foodCRUDServiceService;


	@GetMapping("/search")
	public List<FoodItemDTO> searchFoodItems(@RequestParam String query){

		List<FoodItemDTO> foodItems = searchService.searchItems(query);
		return foodItems;

	}


	@PostMapping("/add")
	public String addFoodItem(@RequestBody FoodItem foodItem){

		boolean result = foodCRUDServiceService.insertFood(foodItem);
		System.out.println("second" + foodItem);
		return "Food item added";

	}


	@GetMapping("/searchByCategory")
	public List<FoodItemDTO> searchByCategory(@RequestParam String category){

		List<FoodItemDTO> foodItemList = searchService.querycategory(category);
		System.out.println("second" + foodItemList);
		return foodItemList;

	}

	@GetMapping("/elastic/search")
	public Object searchFoodItemsElastic(@RequestParam String query) throws IOException {

		Object response = searchService.searchInElastic(query);
		return response;

	}

	@GetMapping("/elastic/category")
	public Object searchByCategoryElastic(@RequestParam String query) throws IOException {

		Object response = searchService.searchByCategoryElastic(query);
		return response;

	}

	@PostMapping("/elastic/addFood")
	public Object addFoodElastic(@RequestParam String id, @RequestBody FoodItem foodItem) throws Exception {

		Object response = searchService.addFoodElastic(id, foodItem);
		return response;

	}


}
