package com.test.recipes.service;

import com.fatsecret.food.Food;
import com.test.recipes.domain.entities.Ingredient;
import com.test.recipes.domain.repositories.IngredientRepository;
import com.test.recipes.domain.entities.Recipe;
import com.test.recipes.domain.repositories.RecipeRepository;
import com.test.recipes.domain.entities.Chef;
import com.test.recipes.domain.repositories.ChefRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Recipe services
 * @author Gines
 */
@Service
public class RecipeServices {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    ChefRepository chefRepository;
    
    @Autowired
    FatsecretServices fatsecret;

    public Iterable<Recipe> getRecentRecipes(int num) {

        // Fake data: to be run, one time only, after database creation:
        //populateDatabase();
        
        List<Recipe> recent = new ArrayList<>();
        Iterator<Recipe> it = recipeRepository.findAll(new Sort(Sort.Direction.DESC, "creationDate")).iterator();
        int i = 0;
        while (it.hasNext()) {
            Recipe r = it.next();
            recent.add(r);
            if (++i >= num) {
                break;
            }
        }
        return recent;
    }

    public Iterable<Recipe> getListOfRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipe(int id) {
        return recipeRepository.findOne(id);
    }

    public Recipe getRecipe(String name) {
        List<Recipe> recipes = recipeRepository.findByName(name);
        if (!recipes.isEmpty()) {
            return recipes.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * Get an ingredient, by name
     * @param name
     * @return 
     */
    public Ingredient getIngredient(String name) {
        List<Ingredient> ingredients = ingredientRepository.findByName(name);
        if (!ingredients.isEmpty()) {
            return ingredients.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * Create a new recipe.
     * @param name
     * @param description 
     * @param fatsecretIds 
     */
    public void createRecipe(String name, String description, String[] fatsecretIds) {
        
        Chef chef = this.getChef();
        
        List<Ingredient> ingredients  = new ArrayList<>();
        for (String id : fatsecretIds) {
            Food food = fatsecret.getFood(id);
            
            // Check first if the ingredient already exists (in another recipe):
            Ingredient ingredient = ingredientRepository.findOne(food.getFood_id());
            if (ingredient == null) {
                ingredient = new Ingredient(food.getFood_id(), food.getFood_name(), food.getCalories());
                
                // Create the new ingredients:
                ingredientRepository.save(ingredient);
            }
            ingredients.add(ingredient);
        }

        Recipe recipe = new Recipe(name, description, chef, ingredients);
        recipeRepository.save(recipe);
    }
    
    /**
     * Update an existent recipe.
     * @param id
     * @param name
     * @param description
     * @param fatsecretIds 
     */
    public void updateRecipe(Integer id, String name, String description, String[] fatsecretIds) {
        
        List<Ingredient> ingredients  = new ArrayList<>();
        for (String fsId : fatsecretIds) {
            Food food = fatsecret.getFood(fsId);
            
            // Check first if the ingredient already exists (in another recipe):
            Ingredient ingredient = ingredientRepository.findOne(food.getFood_id());
            if (ingredient == null) {
                ingredient = new Ingredient(food.getFood_id(), food.getFood_name(), food.getCalories());
                
                // Create the new ingredients:
                ingredientRepository.save(ingredient);
            }
            ingredients.add(ingredient);
        }

        // Update:
        Recipe recipe = recipeRepository.findOne(id);
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setIngredients(ingredients);
        recipeRepository.save(recipe);
    }
    
    /**
     * Delete a recipe.
     * @param id 
     */
    public void deleteRecipe(int id) {
        recipeRepository.delete(id);
    }
    
    /**
     * Search recipes by its name, the names of its ingredients and a maximum of calories
     * @param name
     * @param ingredientNames
     * @param calories
     * @return 
     */
    public List<Recipe> searchRecipes(String name, List<String> ingredientNames, String calories) {
        Integer maxCalories;
        try {
            maxCalories = Integer.parseInt(calories);
            if (maxCalories <= 0) {
                maxCalories = null;
            }
        } catch(NumberFormatException e) {
            maxCalories = null;
        }
        
        List<Recipe> recipes;
        
        if (ingredientNames.isEmpty()) {
            recipes = recipeRepository.findByNameLike(name);
        } 
        else {
            // Retrieve ingredients whose name is LIKE one of the list of ingredients:
            List<Ingredient> ingredients = new ArrayList<>();
            for (String ingredientName : ingredientNames) {
                List<Ingredient> ing = ingredientRepository.findByNameLike(ingredientName);
                if (ing != null && ing.size() > 0) {
                    ingredients.addAll(ing);
                }
            }

            if (!ingredients.isEmpty()) {
                recipes = recipeRepository.findDistinctByNameLikeOrIngredientsIn(name, ingredients);
            } else {
                recipes = recipeRepository.findByNameLike(name);
            }
        }
        
        if (maxCalories != null) {
            List<Recipe> recipes2 = new ArrayList<>();
            for (Recipe recipe : recipes) {
                if (recipe.getCalories() <= maxCalories) {
                    recipes2.add(recipe);
                }
            }
            recipes = new ArrayList<>(recipes2);
        } 

        // Sort by name:
        Collections.sort(recipes);
        
        return recipes;
    }
    
    /**
     * Check if the chef is logged in.
     * @return 
     */
    public boolean isChefLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null &&
               auth.isAuthenticated() &&
               auth.getPrincipal() instanceof User;
    }
    
    /**
     * Return the logged user (Chef) name or empty String;
     * @return 
     */
    public String getChefName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null &&
            auth.isAuthenticated() &&
            auth.getPrincipal() instanceof User) {
            
            return ((User)auth.getPrincipal()).getUsername();
        } else {
            return "";
        }
    }
    
    /**
     * Return a user (Chef) by its name.
     * @return 
     */
    public Chef getChef() {
        List<Chef> found = chefRepository.findByName(this.getChefName());
        if (found.isEmpty()) {
            return null;
        } else {
            return found.get(0);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="DB creation method. Click on the + sign on the left to edit the code.">
    private void populateDatabase() {
        
        // ONE TIME ONLY DB CREATION: 
        // Check if there are already data:
        Iterable<Chef> users = chefRepository.findAll();
        Iterator<Chef> it = users.iterator();
        if (it.hasNext()) {
            System.out.println("********** Already exists user: " + it.next().getName());
            return;
        }
        System.out.println("*********** Populating DB...");
        
        Chef chef = new Chef("test", "test");
        chefRepository.save(chef);

        Ingredient tomato = new Ingredient(6138, "Tomato", 16);
        Ingredient salt = new Ingredient(33908, "Salt", 0);
        Ingredient rice = new Ingredient(4501, "Rice", 204);
        Ingredient bellPepper = new Ingredient(285829, "Bell Pepper", 19);
        Ingredient onion = new Ingredient(36442, "Onion", 67);
        Ingredient bread = new Ingredient(38820, "Bread", 120);
        Ingredient cucumber = new Ingredient(36376, "Cucumber", 8);

        List<Ingredient> ingredientes = new ArrayList<>();
        ingredientes.add(tomato);
        ingredientes.add(cucumber);
        ingredientes.add(salt);
        ingredientes.add(rice);
        ingredientes.add(bellPepper);
        ingredientes.add(onion);
        ingredientes.add(bread);
        ingredientRepository.save(ingredientes);

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Negative salad", "The lightest.", chef, Arrays.asList(new Ingredient[]{tomato, cucumber, salt})));
        recipes.add(new Recipe("Vegetable rice", "Healthy and nourishing.", chef, Arrays.asList(new Ingredient[]{rice, bellPepper})));
        recipes.add(new Recipe("Bread and onion", "Not very sophisticated.", chef, Arrays.asList(new Ingredient[]{bread, onion})));
        recipeRepository.save(recipes);
    }
// </editor-fold>    
}
