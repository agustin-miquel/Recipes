package com.dev.recipes.service;

import com.dev.recipes.domain.entities.Chef;
import com.dev.recipes.domain.repositories.ChefRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * User management services
 * @author Gines
 */
@Service
public class UserServices {

    @Autowired
    ChefRepository chefRepository;

    /**
     * Create a new user (chef)
     * @param name
     * @param password 
     * @throws java.lang.Exception 
     */
    public void createUser(String name, String password) throws Exception {
        Chef chef = new Chef(name, password);
        chefRepository.save(chef);
    }
    
    /**
     * Check if a user name already exists.
     * @param name
     * @return 
     */
    public boolean exists(String name) {
        List<Chef> list = chefRepository.findByName(name);
        return list.size() > 0;
    }
}
