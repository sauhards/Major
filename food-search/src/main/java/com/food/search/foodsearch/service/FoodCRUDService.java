package com.food.search.foodsearch.service;


import com.food.search.foodsearch.model.FoodItem;
import com.food.search.foodsearch.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodCRUDService {


	@Autowired
	FoodRepository foodRepository;

	public boolean insertFood(FoodItem foodItem){

		Object obj = foodRepository.save(foodItem);

		if(obj != null) return true;
		else return false;

	}



}
