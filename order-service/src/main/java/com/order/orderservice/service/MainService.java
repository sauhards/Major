package com.order.orderservice.service;


import com.order.orderservice.model.*;
import com.order.orderservice.repository.OrderRepository;
import com.order.orderservice.repository.RestaurantRepository;
import com.order.orderservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MainService {


	@Autowired
	RestaurantRepository restaurantRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	MongoOperations mongoOperations;


	private double findDistance(double lat1, double lon1, double lat2, double lon2, char unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
            dist = dist * 1.609344;
        }
		else if (unit == 'N') {
            dist = dist * 0.8684;
		}
        return (dist);
    }

    private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
    }

	public OrderResponse order(String userId, String restaurantId, List<OrderItem> orderItems) throws Exception {

		double total = 0;
		for(OrderItem foodItem : orderItems){
			total += foodItem.getPrice() * foodItem.getQuantity();
		}
		User user = userRepository.findById(userId).get();
		Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
		double distance = findDistance(user.getLatitude(), user.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude(), 'K');
		Order order = new Order();
		order.setOrderItemList(orderItems);
		order.setUserId(userId);
		order.setRestaurantId(restaurantId);
		order.setDate(new Date().toString());
		order.setTotal(total);
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setTotalAmount(String.valueOf(total));
		orderResponse.setUserId(userId);
		orderResponse.setRestaurantId(restaurantId);
		orderResponse.setDate(new Date().toString());
		if(distance <= 10){
			if(user.getWalletAmount() < total){
				orderResponse.setOrderStatus("AmountNotSufficient");
				order.setOrderStatus("AmountNotSufficient");
//				throw new Exception("Wallet amount not sufficient to take Order! Please add money to wallet first...");
			}
			else {
				orderResponse.setOrderStatus("Placed");
				order.setOrderStatus("Placed");
				user.setWalletAmount(user.getWalletAmount() - total);
				orderResponse.setTimeToDeliver((int) (15.0 * Math.random() + 30) + " minutes!");
			}
		}
		else{
			orderResponse.setOrderStatus("DoNotDeliver");
			order.setOrderStatus("DoNotDeliver");
//			throw new Exception("Can not take order. We can not deliver if you are away from restaurant by more than 10 Kms...");
		}
		orderRepository.save(order);
		userRepository.save(user);
		return orderResponse;
	}


	public List<Order> getAllOrdersByRestaurant(String restaurantId){
		Query query = new Query();
		query.addCriteria(Criteria.where("restaurantId").is(restaurantId));
		List<Order> orderList = mongoOperations.find(query, Order.class);
		return orderList;
	}

	public List<Order> getAllOrdersByuser(String userId){
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		List<Order> orderList = mongoOperations.find(query, Order.class);
		return orderList;
	}
}