package com.test.recipes.web;

import com.fatsecret.food.Food;
import com.fatsecret.food.Foods;
import com.test.recipes.domain.entities.Ingredient;
import com.test.recipes.service.FatsecretServices;
import com.test.recipes.service.RecipeServices;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Ingredient pages Controller.
 * @author Gines
 */
@Controller
public class IngredientController {

    @Autowired
    RecipeServices recipeServices;
    
    @Autowired
    FatsecretServices fatSecret;
    
    /**
     * View ingredient.
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping(value="/ingredient_detail", method=GET)
    public String ingredientDetail(Model model, HttpServletRequest request) {
    
        String ingredientName = request.getParameter("ingredient");
        
        Ingredient ingredient = recipeServices.getIngredient(ingredientName);
        model.addAttribute(ingredient);

        Food food = fatSecret.getFood("" + ingredient.getId());
        model.addAttribute("info_ingredient", food.getIngredientInfo());
        
        return "ingredient_detail";
    }
    
    /**
     * Go to ingredient search page.
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping(value="/ingredient_finder", method=GET)
    public String ingredientFinder(Model model, HttpServletRequest request) {
        return "ingredient_finder";
    }
    
    /**
     * Find ingredients.
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/search_ingredients", method=GET)
    public String searchIngredients(Model model, HttpServletRequest request) {
        try {
            String name = request.getParameter("name");

            Foods foods = fatSecret.getFoodInfo(name, 0);
            
            if (foods.getTotal_results() == 0) {
                model.addAttribute("message", "No results");
            } else {
                model.addAttribute("message", "Found " + foods.getTotal_results() + " results:");
            }
            
            model.addAttribute("ingredients", foods.getFood());
        }
        catch(Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "/ingredient_finder";
    }
    
    /**
     * AJAX:
     * Find and return the requested ingredient.
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/get_ingredient", method=GET)
    public Food getIngredient(HttpServletRequest request) {
        
        String id = request.getParameter("id_ingredient");
        Food ingredient = fatSecret.getFood(id);
        return ingredient;
    }

    /**
     * AJAX:
     * Find ingredients by name.
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/get_ingredients_list", method=GET)
    public Food[] getListOfIngredients(HttpServletRequest request) {
        try {
            String name = request.getParameter("name");
            Foods foods = fatSecret.getFoodInfo(name, 0);
            
            if (foods == null || foods.getFood() == null) {
                return new Food[]{};
            }
            return foods.getFood();
        } 
        catch(Exception e) {
            return new Food[]{};
        }
    }
    
    /**
     * AJAX:
     * Calculate total calories in a list of ingredients.
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/calculate_calories", method=GET)
    public Integer calculate_calories(HttpServletRequest request) {
        System.out.println("HERE");
        Object o = request.getParameter("ingredients");
        System.out.println("*********" + o.getClass().getName());
        System.out.println("*********" + o);
        return 33;
    }
}
