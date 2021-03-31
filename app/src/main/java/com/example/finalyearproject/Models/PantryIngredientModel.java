package com.example.finalyearproject.Models;

public class PantryIngredientModel {

    String ingredientName;
    String amount;
    String measurementType;
    String expiryDate;

    public PantryIngredientModel(String ingredientName, String amount, String measurementType, String expiryDate) {
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

    public String getAmount() {
        return amount + " " + measurementType;
    }

    public void setAmount(String amount) {
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
