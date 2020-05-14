package com.food.search.foodsearch.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.search.foodsearch.config.ESConfiguration;
import com.food.search.foodsearch.model.FoodItem;
import com.food.search.foodsearch.model.FoodItemDTO;
import com.food.search.foodsearch.model.Restaurant;
import com.food.search.foodsearch.repository.FoodRepository;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class SearchService {

	@Value("${elasticsearch.index}")
	private String ELASTIC_INDEX;

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	DTOConversionService dtoConversionService;

	@Autowired
	ESConfiguration esConfiguration;

	RestHighLevelClient client;

	public List<FoodItemDTO> searchItems(String searchQuery){

		client = esConfiguration.client();

		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(searchQuery));
		List<FoodItem> foodItemList = mongoOperations.find(query, FoodItem.class);

		List<FoodItemDTO> foodItemDTOList = dtoConversionService.convertToDTO(foodItemList);



		return foodItemDTOList;

	}

	public List<FoodItemDTO> querycategory(String category){

		Query query = new Query().addCriteria(Criteria.where("category").is(category));
		List<FoodItem> foodItemList = mongoOperations.find(query, FoodItem.class);

		List<FoodItemDTO> foodItemDTOList = dtoConversionService.convertToDTO(foodItemList);

		return foodItemDTOList;

	}

	public Object searchInElastic(String query) throws IOException {

		client = esConfiguration.client();
		SearchRequest searchRequest = new SearchRequest(ELASTIC_INDEX);

		searchRequest.requestCache(Boolean.TRUE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.query(
			QueryBuilders.multiMatchQuery(query,"name","foodItemList.name","foodItemList.category","foodItemList.name")
			.type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
		);


		searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		List<Restaurant> restaurantList = convertToRestaurants(searchResponse);
		return restaurantList;
	}

	public Object searchByCategoryElastic(String category) throws IOException {

		client = esConfiguration.client();
		SearchRequest searchRequest = new SearchRequest(ELASTIC_INDEX);

		searchRequest.requestCache(Boolean.TRUE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.query(
				QueryBuilders.fuzzyQuery("foodItemList.category", category)
		);

		searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		List<Restaurant> restaurantList = convertToRestaurants(searchResponse);
		return restaurantList;
	}

	private List<Restaurant> convertToRestaurants(SearchResponse response) {

        SearchHit[] searchHitList = response.getHits().getHits();
        List<Restaurant> restaurantList = new ArrayList<>();

        if (searchHitList.length > 0) {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Arrays.stream(searchHitList)
                    .forEach(hit -> restaurantList
                            .add(objectMapper
                                    .convertValue(hit.getSourceAsMap(),
                                                    Restaurant.class))
                    );
        }

        return restaurantList;
    }

    public Restaurant findById(String id) throws Exception {

	    SearchRequest searchRequest = new SearchRequest(ELASTIC_INDEX);

		searchRequest.requestCache(Boolean.TRUE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.query(
				QueryBuilders.fuzzyQuery("id", id)
		);

		searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		List<Restaurant> restaurantList = convertToRestaurants(searchResponse);
		return restaurantList.get(0);

    }

    // todo
	public Object addFoodElastic(String id, FoodItem foodItem) throws Exception {
		client = esConfiguration.client();

		Restaurant restaurant = this.findById(id);
		UpdateRequest updateRequest = new UpdateRequest(ELASTIC_INDEX, id);

//		ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> documentMapper = objectMapper.convertValue(foodItem, Map.class);

        List<FoodItem> foodItemList ;
        if(restaurant.getFoodItemList() == null){
        	foodItemList = new ArrayList<>();
        }
        else{
        	foodItemList = restaurant.getFoodItemList();
        }
        foodItemList.add(foodItem);
        updateRequest.doc(restaurant);

        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);

        return updateResponse.getResult();
	}
}
