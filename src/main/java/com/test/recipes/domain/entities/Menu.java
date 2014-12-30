package com.test.recipes.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Entity: menu
 * @author Gines Miquel
 */
@Entity
public class Menu {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    
    @ManyToMany             // Bidirectional: this side is the owner.
    @JsonManagedReference   // For Jackson JSON: indicates this is the PARENT of the (many2many) relation.
    private List<Recipe> recipes;

    public Menu() {
        recipes = new ArrayList<>();
    }

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
        recipes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return name;
    }
}
