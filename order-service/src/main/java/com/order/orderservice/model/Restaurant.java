package com.order.orderservice.model;


import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "restaurantApplication")
@AllArgsConstructor
public class Restaurant {

	@Id
	String id;
	String name;
	String address;
	String email;
	String contact;
	String password;
	String status;
	double latitude;
	double longitude;
	List<FoodItem> foodItemList;

	public String getId() { return this.id; }
	public String getName() { return this.name; }
	public String getAddress() { return this.address;	}
	public String getEmail() { return this.email;	}
	public String getContact() { return this.contact;	}
	public String getPassword() {
		return this.password;
	}
	public double getLatitude() {
		return latitude;
	}
	public String getStatus() {
		return status;
	}
	public double getLongitude() { return longitude; }
	public List<FoodItem> getFoodItemList() { return foodItemList;	}

	public void setId(String password) {
		this.password = password;
	}
	public void setName(String name) {
		this.password = name;
	}
	public void setAddress(String address) { this.password = address; }
	public void setEmail(String email) {
		this.password = email;
	}
	public void setContact(String contact) {
		this.password = contact;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setFoodItemList(List<FoodItem> foodItemList) { this.foodItemList = foodItemList; }

}
