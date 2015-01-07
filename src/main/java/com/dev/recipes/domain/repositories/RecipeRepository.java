package com.dev.recipes.domain.repositories;

import com.dev.recipes.domain.entities.Ingredient;
import com.dev.recipes.domain.entities.Recipe;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Integer> {

    /**
     * Search by name.
     * @param name
     * @return 
     */
    public List<Recipe> findByName(String name);
    
    /**
     * Search by similar name (LIKE)
     * @param name
     * @return 
     */
    @Query("select r from Recipe r where r.name like %?1%")
    public List<Recipe> findByNameLike(String name);

    /**
     * Search recipes WHERE its name is similar (LIKE) to the provided name
     *                OR some of its ingredients is in the provided list (IN).
     * @param name
     * @param ingredients
     * @return 
     */
    public List<Recipe> findDistinctByNameLikeOrIngredientsIn(String name, List<Ingredient> ingredients);
    
}
