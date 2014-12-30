package com.test.recipes.domain.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity: Chef
 */
@Entity
public class Chef {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(unique=true)
    private String name;
    private String password;
    @OneToMany
    private List<Recipe> recipes;

    protected Chef() {}

    public Chef(String name, String password) {
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }


    @Override
    public String toString() {
        return "Chef{" + "id=" + id + ", name=" + name + ", password=" + password + //", recipes=" + recipes.size() 
                + '}';
    }

}
