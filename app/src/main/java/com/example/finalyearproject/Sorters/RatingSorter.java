package com.example.finalyearproject.Sorters;

import com.example.finalyearproject.Models.RecipeModel;

import java.util.Comparator;

public class RatingSorter implements Comparator<RecipeModel> {

    @Override
    public int compare(RecipeModel o1, RecipeModel o2) {
        Integer ratingo1=o1.getRating();
        Integer ratingo2=o2.getRating();
        return ratingo2.compareTo(ratingo1);
    }
}
