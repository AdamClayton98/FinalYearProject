package com.example.finalyearproject.Models;

import java.util.ArrayList;
import java.util.List;

public class RecipeModel {

    int id;
    String recipeName;
    ArrayList<String> ingredients;
    ArrayList<String> steps;
    String cookingTime;
    String serves;

    public RecipeModel(int id,String recipeName, ArrayList<String> ingredients, ArrayList<String> steps, String cookingTime, String serves) {
        this.id = id;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.steps = steps;
        this.cookingTime = cookingTime;
        this.serves = serves;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getServes() {
        return serves;
    }

    public void setServes(String serves) {
        this.serves = serves;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
