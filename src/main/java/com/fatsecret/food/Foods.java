package com.fatsecret.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * FatSecret's food.search method base structure.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Foods {
    
    private Integer max_results;
    private Integer total_results;
    private Integer page_number;
    private Food[] food;

    public Integer getMax_results() {
        return max_results;
    }

    public void setMax_results(Integer max_results) {
        this.max_results = max_results;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public Integer getPage_number() {
        return page_number;
    }

    public void setPage_number(Integer page_number) {
        this.page_number = page_number;
    }

    public Food[] getFood() {
        return food;
    }

    public void setFood(Food[] food) {
        this.food = food;
    }

}
