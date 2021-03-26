package com.example.finalyearproject.Models;

public class AllergyModel {

    int id;
    String allergy;


    public AllergyModel(int id, String allergy) {
        this.id = id;
        this.allergy = allergy;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }
}
