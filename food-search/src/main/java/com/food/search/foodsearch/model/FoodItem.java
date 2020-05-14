package com.food.search.foodsearch.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "Item")
public class FoodItem {

	@Id
	String id;
	String name;
	Double price;
	String category;
	List<String> offer;
	@JsonProperty
	boolean isVeg;

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public List<String> getOffer() {
		return offer;
	}

	public String getCategory() {
		return category;
	}

}
// TODO by category

