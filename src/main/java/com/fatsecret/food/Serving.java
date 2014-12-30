package com.fatsecret.food;

/**
 * FatSecret's Food's Serving data wrapper.
 */
public class Serving {

    private Long serving_id;
    private String serving_description;
    private String serving_url;
    private Double metric_serving_amount;
    private String metric_serving_unit;
    private Double number_of_units;
    private String measurement_description;

    private Integer calories;
    private Double carbohydrate;
    private Double protein;
    private Double fat;
    private Double saturated_fat;
    private Double polyunsaturated_fat;
    private Double monounsaturated_fat;
    private Double cholesterol;
    private Double sodium;
    private Double potassium;
    private Double fiber;
    private Double sugar;
    private Double vitamin_a;
    private Double vitamin_c;
    private Double calcium;
    private Double iron;

    // SERVICES ---------------------------------
    
    /**
     * Serving size expression.
     * @return 
     */
    public String getMedida() {
        return this.number_of_units + " " + this.measurement_description + 
                " (" + this.metric_serving_amount + " " + this.metric_serving_unit + ")";
    }
    
    // GETTERS & SETTERS -----------------------
    
    public Long getServing_id() {
        return serving_id;
    }

    public void setServing_id(Long serving_id) {
        this.serving_id = serving_id;
    }

    public String getServing_description() {
        return serving_description;
    }

    public void setServing_description(String serving_description) {
        this.serving_description = serving_description;
    }

    public String getServing_url() {
        return serving_url;
    }

    public void setServing_url(String serving_url) {
        this.serving_url = serving_url;
    }

    public Double getMetric_serving_amount() {
        return metric_serving_amount;
    }

    public void setMetric_serving_amount(Double metric_serving_amount) {
        this.metric_serving_amount = metric_serving_amount;
    }

    public String getMetric_serving_unit() {
        return metric_serving_unit;
    }

    public void setMetric_serving_unit(String metric_serving_unit) {
        this.metric_serving_unit = metric_serving_unit;
    }

    public Double getNumber_of_units() {
        return number_of_units;
    }

    public void setNumber_of_units(Double number_of_units) {
        this.number_of_units = number_of_units;
    }

    public String getMeasurement_description() {
        return measurement_description;
    }

    public void setMeasurement_description(String measurement_description) {
        this.measurement_description = measurement_description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getSaturated_fat() {
        return saturated_fat;
    }

    public void setSaturated_fat(Double saturated_fat) {
        this.saturated_fat = saturated_fat;
    }

    public Double getPolyunsaturated_fat() {
        return polyunsaturated_fat;
    }

    public void setPolyunsaturated_fat(Double polyunsaturated_fat) {
        this.polyunsaturated_fat = polyunsaturated_fat;
    }

    public Double getMonounsaturated_fat() {
        return monounsaturated_fat;
    }

    public void setMonounsaturated_fat(Double monounsaturated_fat) {
        this.monounsaturated_fat = monounsaturated_fat;
    }

    public Double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Double getSodium() {
        return sodium;
    }

    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }

    public Double getPotassium() {
        return potassium;
    }

    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }

    public Double getFiber() {
        return fiber;
    }

    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    public Double getSugar() {
        return sugar;
    }

    public void setSugar(Double sugar) {
        this.sugar = sugar;
    }

    public Double getVitamin_a() {
        return vitamin_a;
    }

    public void setVitamin_a(Double vitamin_a) {
        this.vitamin_a = vitamin_a;
    }

    public Double getVitamin_c() {
        return vitamin_c;
    }

    public void setVitamin_c(Double vitamin_c) {
        this.vitamin_c = vitamin_c;
    }

    public Double getCalcium() {
        return calcium;
    }

    public void setCalcium(Double calcium) {
        this.calcium = calcium;
    }

    public Double getIron() {
        return iron;
    }

    public void setIron(Double iron) {
        this.iron = iron;
    }

}
