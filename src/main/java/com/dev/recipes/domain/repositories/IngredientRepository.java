package com.dev.recipes.domain.repositories;

import com.dev.recipes.domain.entities.Ingredient;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {

    /**
     * Search by name.
     * @param name
     * @return 
     */
    public List<Ingredient> findByName(String name);
    
    /**
     * Search ingredients whose ID is in the provided list (IN).
     * @param ids
     * @return 
     */
    public List<Ingredient> findByIdIn(List<Long> ids);
    
    /**
     * Search similar names (LIKE)
     * @param name
     * @return 
     */
    public List<Ingredient> findByNameLike(String name);
}
