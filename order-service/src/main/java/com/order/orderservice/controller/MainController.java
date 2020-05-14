package com.order.orderservice.controller;


import com.order.orderservice.model.Order;
import com.order.orderservice.model.OrderItem;
import com.order.orderservice.model.OrderResponse;
import com.order.orderservice.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {

	@Autowired
	MainService mainService;



	@PostMapping("/order")
	public OrderResponse order(@RequestParam String userId, @RequestParam String restaurantId, @RequestBody List<OrderItem> orders) throws Exception {
		return mainService.order(userId, restaurantId, orders);
	}

	@GetMapping("/restaurant/orders")
	public List<Order> getOrder(@RequestParam String id){
		return mainService.getAllOrdersByRestaurant(id);
	}

	@GetMapping("/user/orders")
	public List<Order> getOrderUser(@RequestParam String id){
		return mainService.getAllOrdersByuser(id);
	}
}