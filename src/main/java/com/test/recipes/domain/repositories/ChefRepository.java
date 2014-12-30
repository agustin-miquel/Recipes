package com.test.recipes.domain.repositories;

import com.test.recipes.domain.entities.Chef;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ChefRepository extends CrudRepository<Chef, Integer> {
    /**
     * Search by name.
     * @param name
     * @return 
     */
    public List<Chef> findByName(String name);

}
