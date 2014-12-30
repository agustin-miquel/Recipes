package com.fatsecret.food;

/**
 * Information returned by FatSecret's food.search method.
 */
public class FoodSearchInfo {
    private Foods foods;

    public Foods getFoods() {
        return foods;
    }

    public void setFoods(Foods foods) {
        this.foods = foods;
    }
}
