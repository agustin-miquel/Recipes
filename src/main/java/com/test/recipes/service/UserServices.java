package com.test.recipes.service;

import com.test.recipes.domain.entities.Chef;
import com.test.recipes.domain.repositories.ChefRepository;
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
     */
    public void createUser(String name, String password) throws DuplicateKeyException {
        Chef chef = new Chef(name, password);
        chefRepository.save(chef);
    }
}
