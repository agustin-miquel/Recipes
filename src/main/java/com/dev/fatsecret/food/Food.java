package com.dev.fatsecret.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Wrapper for FatSecret's Food data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Food {

    private Long food_id;
    private String food_name;
    private String food_type;
    private String brand_name;
    private String food_url;
    private Servings servings;

    /**
     * FULL info for the Ingredient finder
     * Takes the default data from first serving
     * @return 
     */
    public String getInfo() {
        try {
            StringBuilder sb = new StringBuilder();
            
            sb.append("<b>").append(food_name).append("</b><br/>");
            sb.append(this.getIngredientInfo());
            sb.append("FatSecret Id: ").append(this.food_id);
            
            return sb.toString();
        } 
        catch(Exception e) {
            return "Not available.";
        }
    }
            
    /**
     * Info for the Ingredient details
     * Takes the default data from first serving
     * @return 
     */
    public String getIngredientInfo() {
        try {
            StringBuilder sb = new StringBuilder();
            Serving serving = servings.getServing()[0];
            
            sb.append("Amount per serving: ").append(serving.getMedida()).append("<br/>");
            sb.append("<br/>");
            
            sb.append("Calories: ").append(serving.getCalories()).append("<br/>");
            sb.append("Carbohydrate: ").append(serving.getCarbohydrate()).append("<br/>");
            sb.append("Sugar: ").append(serving.getSugar()).append("<br/>");
            sb.append("Protein: ").append(serving.getProtein()).append("<br/>");
            sb.append("Total Fat: ").append(serving.getFat()).append("<br/>");
            sb.append("Saturated Fat: ").append(serving.getSaturated_fat()).append("<br/>");
            sb.append("Polyunsaturated Fat: ").append(serving.getPolyunsaturated_fat()).append("<br/>");
            sb.append("Monounsaturated Fat: ").append(serving.getMonounsaturated_fat()).append("<br/>");
            sb.append("Cholesterol: ").append(serving.getCholesterol()).append("<br/>");
            sb.append("Fiber: ").append(serving.getFiber()).append("<br/>");
            sb.append("<br/>");
            
            sb.append("Sodium: ").append(serving.getSodium()).append("<br/>");
            sb.append("Potassium: ").append(serving.getPotassium()).append("<br/>");
            sb.append("Calcium: ").append(serving.getCalcium()).append("<br/>");
            sb.append("Iron: ").append(serving.getIron()).append("<br/>");
            sb.append("<br/>");
            
            sb.append("Vitamin A: ").append(serving.getVitamin_a()).append("<br/>");
            sb.append("Vitamin C: ").append(serving.getVitamin_c()).append("<br/>");
            sb.append("<br/>");
            
            return sb.toString();
        } 
        catch(Exception e) {
            return "Not available.";
        }
    }
       
    /**
     * Food calories
     * @return 
     */
    public int getCalories() {
        try {
            return servings.getServing()[0].getCalories();
        } catch(Exception e) {
            return 0;
        }
    }

    // Getters & setters ----------------------------------------------
    
    public Long getFood_id() {
        return food_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getFood_url() {
        return food_url;
    }

    public void setFood_url(String food_url) {
        this.food_url = food_url;
    }

    public Servings getServings() {
        return servings;
    }

    public void setServings(Servings servings) {
        this.servings = servings;
    }

    @Override
    public String toString() {
        return "Food id: " + food_id + ", name: " + food_name;
    }
}
