package com.food.search.foodsearch.repository;


import com.food.search.foodsearch.model.FoodItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<FoodItem, String> {


}
