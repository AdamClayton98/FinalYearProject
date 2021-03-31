package com.example.finalyearproject.Models;

public class PantryIngredientModel {

    String ingredientName;
    int amount;
    String measurementType;
    String expiryDate;

    public PantryIngredientModel(String ingredientName, int amount, String measurementType, String expiryDate) {
        this.ingredientName = ingredientName;
        this.amount = amount;
        this.measurementType = measurementType;
        this.expiryDate = expiryDate;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
