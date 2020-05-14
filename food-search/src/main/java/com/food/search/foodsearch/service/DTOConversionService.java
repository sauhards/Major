package com.food.search.foodsearch.service;


import com.food.search.foodsearch.model.FoodItem;
import com.food.search.foodsearch.model.FoodItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOConversionService {



	public List<FoodItemDTO> convertToDTO(List<FoodItem> foodItemList){

		List<FoodItemDTO> foodItemDTOList = new ArrayList<>();

		for(FoodItem foodItem : foodItemList){
			FoodItemDTO foodItemDTO = new FoodItemDTO(foodItem);
			foodItemDTOList.add(foodItemDTO);
		}

		return foodItemDTOList;
	}



}
