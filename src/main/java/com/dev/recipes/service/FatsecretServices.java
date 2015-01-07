package com.dev.recipes.service;

import org.springframework.stereotype.Service;
import com.dev.fatsecret.*;
import com.dev.fatsecret.food.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FatSecret services.
 * @author Gines
 */
@Service
public class FatsecretServices {

    public static final String GENERIC = "Generic";
    
    /**
     * Search ingredients by name.
     * @param search
     * @param max maximum number of results to be returned. If <= 0 everything is returned.
     * @return
     * @throws Exception 
     */
    public Foods getFoodInfo(String search, int max) throws Exception {
        if (search.isEmpty()) {
            throw new Exception("Food name is required");
        }
        
        FoodSearchInfo info = (FoodSearchInfo)new FatsecretAPI().service("foods.search", "search_expression", search, FoodSearchInfo.class);
        Foods foods = info.getFoods();
        if (foods.getTotal_results() == 0) {
            return foods;
        }
        
        // Remove commercial products (because are empty). Only generic products are managed:
        List<Food> generics = new ArrayList<>();
        Food[] foodList = foods.getFood();
        for (Food food : foodList) {
            if (food.getFood_type().equals(GENERIC)) {
                generics.add(food);
            }
        }
        foods.setFood(generics.toArray(new Food[]{}));
        foods.setTotal_results(generics.size());
        
        return foods;
    }

    /**
     * Get an ingredient by its ID.
     * @param id
     * @return 
     */
    public Food getFood(String id) {
        try {
            FoodGetInfo info = (FoodGetInfo)new FatsecretAPI().service("food.get", "food_id", id, FoodGetInfo.class);
            Food food = info.getFood();
            return food;
        } catch(Exception e) {
            return null;
        }
    }

}
