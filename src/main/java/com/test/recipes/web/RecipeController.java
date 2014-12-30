package com.test.recipes.web;

import com.test.recipes.domain.entities.Recipe;
import com.test.recipes.service.RecipeServices;
import com.util.Util;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RecipeController {
    
    public static final String OK = "ok";
    public static final String CANCEL = "cancel";

    @Autowired
    RecipeServices recipeServices;

    /**
     * List recipes
     * @param model
     * @return 
     */
    @RequestMapping(value="/list_recipes", method=GET)
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeServices.getListOfRecipes());
        return "list_recipes";
    }

    /**
     * Go to maintenance page.
     * @param model
     * @return 
     */
    @RequestMapping(value="/maintenance_menu", method=GET)
    public String maintenanceMenu(Model model) {
        model.addAttribute("recipes", recipeServices.getListOfRecipes());
        return "maintenance_menu";
    }
    
    /**
     * Go to recipe search page.
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping(value="/recipe_finder", method=GET)
    public String recipeFinder(Model model, HttpServletRequest request) {
        model.addAttribute("recipes", request.getParameter("recipes"));
        return "recipe_finder";
    }

    /**
     * Select maintenance action.
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping(value="/maintenance", method=GET)
    public String accionMantenimiento(Model model, HttpServletRequest request) {
        String selectedRecipe = request.getParameter("selected_recipe");
        if (selectedRecipe != null && selectedRecipe.isEmpty()) {
            selectedRecipe = null;
        }
        
        switch(request.getParameter("action")) {
            case "view":
                if (selectedRecipe == null) {
                    return menuError(model, "Please select a recipe");
                }
                int id = Integer.parseInt(selectedRecipe);
                Recipe recipe = recipeServices.getRecipe(id);
                model.addAttribute(recipe);
                model.addAttribute("origin", "maintenance_menu");
                return "view_recipe";
                
            case "create":
                model.addAttribute(new Recipe());
                model.addAttribute("edit_caption", "New Recipe");
                return "edit_recipe";
                
            case "edit":
                if (selectedRecipe == null) {
                    return menuError(model, "Please select a recipe");
                }
                id = Integer.parseInt(selectedRecipe);
                recipe = recipeServices.getRecipe(id);
                model.addAttribute(recipe);
                model.addAttribute("edit_caption", "Edit Recipe");
                return "edit_recipe";
                
            case "delete":
                if (selectedRecipe == null) {
                    return menuError(model, "Please select a recipe");
                }
                id = Integer.parseInt(selectedRecipe);
                recipe = recipeServices.getRecipe(id);
                model.addAttribute(recipe);
                return "delete_recipe";
        }
        return null;
    }
    private String menuError(Model model, String error) {
        model.addAttribute("message", error);
        model.addAttribute("recipes", recipeServices.getListOfRecipes());
        return "maintenance_menu";
    }
    
    /**
     * Save changes: new and modified recipes.
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping(value="/save", method=POST)
    public String save(Model model, HttpServletRequest request) {
        
        if (request.getParameter("action").equals(OK)) {
            
            // Retrieve the data from the recipe:
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String ingredientsIdsString = request.getParameter("ingredients_ids");
            
            String[] ingredientsIds;
            if (ingredientsIdsString == null || ingredientsIdsString.isEmpty()) {
                ingredientsIds = new String[]{};
            } else {
                ingredientsIds = ingredientsIdsString.split(",");
            }
            
            // Save. If ID is null then it is a new recipe:
            if (id.isEmpty()) {
                recipeServices.createRecipe(name, description, ingredientsIds);
            } else {
                recipeServices.updateRecipe(Integer.parseInt(id), name, description, ingredientsIds);
            }
        }
        
        model.addAttribute("recipes", recipeServices.getListOfRecipes());
        
        // Redirect after POST:
        return "redirect:maintenance_menu";
    }
    
    /**
     * Show recipe detail (from home.jsp).
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/detail", method=GET)
    public String detail(Model model, HttpServletRequest request) {
        String recipeId = request.getParameter("selected_recipe");
        if (recipeId == null || recipeId.isEmpty()) {
            return null;
        }
    
        // Recipe can be searched by Id or by Name:
        Recipe recipe;
        try {
            Integer id = Integer.parseInt(recipeId);
            recipe = recipeServices.getRecipe(id);
        } catch(NumberFormatException e) {
            recipe = recipeServices.getRecipe(recipeId);
        }
        model.addAttribute("recipe", recipe);
        model.addAttribute("origin", "home");

        return "/recipe_detail";
    }
    
    /**
     * Delete.
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/delete", method=POST)
    public String delete(Model model, HttpServletRequest request) {
    
        if (request.getParameter("action").equals(OK)) {
            String sId = request.getParameter("id");
            Integer id = sId.isEmpty() ? null : Integer.parseInt(sId);

            // Business method: Delete from database:
            recipeServices.deleteRecipe(id);
        }

        // Redirect after POST:
        return "redirect:maintenance_menu";
    }
    
    /**
     * Find recipes.
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/search_recipes", method=GET)
    public String searchRecipes(Model model, HttpServletRequest request) {
    
        String name = request.getParameter("name");
        
        // Names of the ingredients, separated by spaces or commas:
        List<String> ingredients = Util.prepareList(request.getParameter("ingredients"));
        
        String calories = request.getParameter("calories");
        
        List<Recipe> recipes = recipeServices.searchRecipes(name, ingredients, calories);
        model.addAttribute("recipes", recipes);
        if (recipes.isEmpty()) {
            model.addAttribute("message", "No results.");
        } else {
            model.addAttribute("message", "Found " + recipes.size() + " results:");
        }
        
        return "/recipe_finder";
    }
    
    /**
     * Retrieve and return the requested recipe.
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/get_recipe", method=GET)
    public Recipe getRecipe(HttpServletRequest request) {
        
        Integer id = Integer.parseInt(request.getParameter("id"));
        Recipe recipe = recipeServices.getRecipe(id);
        return recipe;
    }
}
