package com.order.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
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

