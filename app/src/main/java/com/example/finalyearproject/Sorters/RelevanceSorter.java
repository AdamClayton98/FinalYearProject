package com.example.finalyearproject.Sorters;


import com.example.finalyearproject.Models.RecipeModel;

import java.util.Comparator;

public class RelevanceSorter implements Comparator<RecipeModel>{

    @Override
    public int compare(RecipeModel o1, RecipeModel o2) {
        Integer numOfKeywordsO1=o1.getNumOfKeywords();
        Integer numOfKeywordsO2=o2.getNumOfKeywords();
        return numOfKeywordsO2.compareTo(numOfKeywordsO1);
    }
}
