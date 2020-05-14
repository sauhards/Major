package com.order.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
@AllArgsConstructor
@Data
public class User {

	@Id
	String id;
	String firstName;
	String lastName;
	String email;
	String contact;
	String password;

	public double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(double walletAmount) {
		this.walletAmount = walletAmount;
	}

	double walletAmount;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	double latitude;

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	double longitude;


	public String getId() { return this.id; }
	public String getFirstName() { return this.firstName; }
	public String getLastName() { return this.lastName;	}
	public String getEmail() { return this.email;	}
	public String getContact() { return this.contact;	}
	public String getPassword() {
		return this.password;
	}

	public void setId(String password) {
		this.password = password;
	}
	public void setFirstName(String firstName) {
		this.password = firstName;
	}
	public void setLastName(String lastName) { this.password = lastName; }
	public void setEmail(String email) {
		this.password = email;
	}
	public void setContact(String contact) {
		this.password = contact;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
