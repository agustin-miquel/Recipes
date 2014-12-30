package com.test.recipes.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 * Entity: Recipe
 */
@Entity
public class Recipe implements Comparable<Recipe> {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @ManyToMany                         // Bidirectional: this side is the owner.
    @JsonManagedReference               // For Jackson JSON: indicates this is the PARENT of the (many2many) relation.
    private List<Ingredient> ingredients;
    
    @ManyToOne
    private Chef chef;
    
    @ManyToMany(mappedBy="recipes")     // Bidirectional child: MappedBy is required, in 1 side of the relation only.
    @JsonBackReference                  // For Jackson JSON: indicates this is the CHILD of the (many2many) relation, and will not be serialized.
    private List<Menu> menus;
    
    public Recipe() {
        ingredients = new ArrayList<>();
        menus = new ArrayList<>();
    }

    public Recipe(String nombre, String descripcion, Chef autor, List<Ingredient> ingredientes) {
        this(null, nombre, descripcion, autor, ingredientes);
    }
    
    public Recipe(Integer id, String name, String description, Chef chef, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.chef = chef;
        this.ingredients = ingredients;

        this.creationDate = new Date();
        menus = new ArrayList<>();
    }

    /**
     * Get food calories (adding all ingredients)
     * @return 
     */
    public int getCalories() {
        int calories = 0;
        for (Ingredient i : ingredients) {
            calories += i.getCalories();
        }
        return calories;
    }

    // Getters & setters ------------------
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Chef getChef() {
        return chef;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }
    
    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Equals: two recipes are equal if their id's are equal.
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
        final Recipe other = (Recipe) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Comparable implementation:  use recipe's name
     * @param r
     * @return 
     */
    @Override
    public int compareTo(Recipe r) {
        return this.name.compareTo(r.name);
    }
}
