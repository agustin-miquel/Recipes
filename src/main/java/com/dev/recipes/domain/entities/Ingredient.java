package com.dev.recipes.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Entity: Ingredient
 */
@Entity
public class Ingredient implements Comparable<Ingredient>, Serializable {
    
    @Id
    private long id;                    // Id is the FatSecret's Food Id.
    private String name;
    private int calories;
    
    @ManyToMany(mappedBy="ingredients") // Bidirectional child: MappedBy is required, in 1 side of the relation only.
    @JsonBackReference                  // For Jackson JSON: indicates this is the CHILD of the (many2many) relation, and will not be serialized.
    private List<Recipe> recipes;

    public Ingredient() {
    }

    public Ingredient(long id, String name, int calories) {
        this.id = id;
        this.name = name;
        this.calories = calories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    /**
     * Two ingredients are equal if their names are equal.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ingredient other = (Ingredient) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Comparable: use ingredient's name
     * @param i
     * @return 
     */
    @Override
    public int compareTo(Ingredient i) {
        return this.name.compareTo(i.name);
    }
}
