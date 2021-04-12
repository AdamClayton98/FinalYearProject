package com.example.finalyearproject.Models;

public class BasicIngredientModel {
    String ingredientName;
    String amount;

    public BasicIngredientModel(String ingredientName, String amount) {
        this.ingredientName = ingredientName;
        this.amount = amount;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
