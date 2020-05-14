package com.order.orderservice.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class OrderItem {

	@Id
	String id;
	String name;
	Double price;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	int quantity;
	@JsonProperty
	boolean isVeg;

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}



}


