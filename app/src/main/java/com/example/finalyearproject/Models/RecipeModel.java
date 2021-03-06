package com.example.finalyearproject.Models;

import java.io.Serializable;
import java.util.List;

public class RecipeModel implements Serializable {

    int id;
    String recipeName;
    List<String> ingredients;
    List<String> steps;
    String cookingTime;
    String serves;
    int rating;
    String url;
    boolean isHealthy;
    String recipeType;
    int numOfKeywords;

    public RecipeModel(int id,String recipeName, List<String> ingredients, List<String> steps, String cookingTime, String serves, int rating, String url, boolean isHealthy, String recipeType) {
        this.id = id;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.steps = steps;
        this.cookingTime = cookingTime;
        this.serves = serves;
        this.rating = rating;
        this.url=url;
        this.isHealthy=isHealthy;
        this.recipeType=recipeType;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHealthy() {
        return isHealthy;
    }

    public void setHealthy(boolean healthy) {
        isHealthy = healthy;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public int getNumOfKeywords() {
        return numOfKeywords;
    }

    public void setNumOfKeywords(int numOfKeywords) {
        this.numOfKeywords = numOfKeywords;
    }
}
