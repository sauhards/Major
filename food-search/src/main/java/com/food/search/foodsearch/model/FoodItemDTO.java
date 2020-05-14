package com.food.search.foodsearch.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Item")
public class FoodItemDTO {

	String name;
	Double price;
	String category;
	List<String> offer;
	@JsonProperty
	boolean isVeg;


	public FoodItemDTO(FoodItem foodItem){

		this.name = foodItem.getName();
		this.category = foodItem.getCategory();
		this.offer = foodItem.getOffer();
		this.isVeg = foodItem.isVeg;
		this.price = foodItem.getPrice();

	}

}
