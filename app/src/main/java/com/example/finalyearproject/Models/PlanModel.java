package com.example.finalyearproject.Models;

public class PlanModel {
    int id;
    String uid;
    int recipeId;
    int mealNumber;
    String dateOfPlan;

    public PlanModel(int id, String uid, int recipeId, int mealNumber, String dateOfPlan) {
        this.id = id;
        this.uid = uid;
        this.recipeId = recipeId;
        this.mealNumber = mealNumber;
        this.dateOfPlan = dateOfPlan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getMealNumber() {
        return mealNumber;
    }

    public void setMealNumber(int mealNumber) {
        this.mealNumber = mealNumber;
    }

    public String getDateOfPlan() {
        return dateOfPlan;
    }

    public void setDateOfPlan(String dateOfPlan) {
        this.dateOfPlan = dateOfPlan;
    }
}
