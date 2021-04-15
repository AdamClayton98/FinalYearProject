package com.example.finalyearproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalyearproject.CustomAdapters.RecipeRecyclerAdapter;
import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.Models.PantryIngredientModel;
import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.R;
import com.example.finalyearproject.Sorters.RatingSorter;
import com.example.finalyearproject.Sorters.RelevanceSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {

    View view;
    RecyclerView mostRelevantRecycler;
    RecyclerView topRecipeRecycler;
    RecyclerView favouritesRecycler;
    DatabaseMethods databaseMethods;
    ArrayList<RecipeModel> recipes;
    int noRecipesCount=0;

    //  Filters for the query
    String servingAmount;
    String cookingTime;
    String recipeType;
    boolean isHealthy;

    //  Filters for ArrayLists
    String keywords;
    boolean onlyPantryIngredients;
    boolean includeAllergies;

    public SearchResultFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchResultFragment newInstance(String param1, String param2) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        servingAmount = getArguments().getString("servingAmount");
        cookingTime = getArguments().getString("cookingTime");
        recipeType = getArguments().getString("recipeType");
        isHealthy = getArguments().getBoolean("isHealthy");
        keywords = getArguments().getString("keywords");
        onlyPantryIngredients = getArguments().getBoolean("onlyPantryIngredients");
        includeAllergies = getArguments().getBoolean("includeAllergies");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_result, container, false);
        databaseMethods = new DatabaseMethods(getContext());

        mostRelevantRecycler = view.findViewById(R.id.searchRecipeRecycler1);
        topRecipeRecycler = view.findViewById(R.id.searchRecipeRecycler2);
        favouritesRecycler = view.findViewById(R.id.searchRecipeRecycler3);

        ArrayList<RecipeModel> filteredRelevantRecipes = filterMostRelevantRecipes();
        ArrayList<RecipeModel> filteredTopRecipes = filterTopRecipesForSearch();
        ArrayList<RecipeModel> filteredFavouriteRecipes = filterFavouritesYouCanCook();


        if(noRecipesCount==3){
            getFragmentManager().popBackStack();
            Toast.makeText(getContext(), "There were no recipes that match your search requirements", Toast.LENGTH_SHORT).show();
        }else{
            if(!filteredRelevantRecipes.isEmpty()){
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                mostRelevantRecycler.setAdapter(new RecipeRecyclerAdapter(getContext(), filteredRelevantRecipes));
                mostRelevantRecycler.setLayoutManager(layoutManager);
            }

            if(!filteredTopRecipes.isEmpty()){
                LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                topRecipeRecycler.setAdapter(new RecipeRecyclerAdapter(getContext(), filteredTopRecipes));
                topRecipeRecycler.setLayoutManager(layoutManager2);
            }

            if(!filteredFavouriteRecipes.isEmpty()){
                LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                favouritesRecycler.setAdapter(new RecipeRecyclerAdapter(getContext(), filteredFavouriteRecipes));
                favouritesRecycler.setLayoutManager(layoutManager3);
            }
        }

        return view;
    }


    private ArrayList<RecipeModel> filterMostRelevantRecipes() {
        ArrayList<RecipeModel> recipesToReturn = databaseMethods.getSearchResultRecipes(servingAmount, cookingTime, recipeType, isHealthy);;
        List<String> allergies = databaseMethods.getAllergiesForUser();


        if (!includeAllergies) {
            for (RecipeModel recipe : recipesToReturn) {
                ArrayList<String> recipeIngredients=splitRecipeIngredientsFromDetails(recipesToReturn);
                for (String allergy : allergies) {
                    System.out.println(allergy);
                    System.out.println(recipeIngredients);
                    if (recipeIngredients.contains(allergy)) {
                        recipesToReturn.remove(recipe);
                        break;
                    }
                }
            }
        }

        if (onlyPantryIngredients) {
            ArrayList<String> pantryIngredients = splitPantryIngredients();
            for (RecipeModel recipe : recipesToReturn) {
                ArrayList<String> recipeIngredients = splitRecipeIngredientsFromDetails(recipesToReturn);
                for (String recipeIngredient : recipeIngredients) {
                    if (!pantryIngredients.contains(recipeIngredient)) {
                        recipesToReturn.remove(recipe);
                    }
                }
            }
        }

        ArrayList<String> keywordArray = splitCommaSeparatedString(keywords);

        for (RecipeModel recipe : recipesToReturn) {
            int numOfKeywords = 0;
            for (String keyword : keywordArray) {
                if (recipe.getIngredients().contains(keyword)) {
                    numOfKeywords++;
                }
            }
            recipe.setNumOfKeywords(numOfKeywords);

            if (isHealthy) {
                if (!recipe.isHealthy()) {
                    recipesToReturn.remove(recipe);
                }
            }
        }


        recipesToReturn.sort(new RelevanceSorter());

        if(recipesToReturn.isEmpty()){
            noRecipesCount++;
        }

        try {
            return (ArrayList<RecipeModel>) recipesToReturn.subList(0, 30);
        } catch (IndexOutOfBoundsException e) {
            return recipesToReturn;
        }
    }

    private ArrayList<RecipeModel> filterTopRecipesForSearch() {
        ArrayList<RecipeModel> recipesToReturn = databaseMethods.getSearchResultRecipes(servingAmount, cookingTime, recipeType, isHealthy);;
        List<String> allergies = databaseMethods.getAllergiesForUser();

        if (!includeAllergies) {
            for (RecipeModel recipe : recipesToReturn) {
                ArrayList<String> recipeIngredients=splitRecipeIngredientsFromDetails(recipesToReturn);
                for (String allergy : allergies) {
                    if (recipeIngredients.contains(allergy)) {
                        recipesToReturn.remove(recipe);
                        break;
                    }
                }
            }
        }

        if (onlyPantryIngredients) {
            ArrayList<String> pantryIngredients = splitPantryIngredients();
            for (RecipeModel recipe : recipesToReturn) {
                ArrayList<String> recipeIngredients = splitRecipeIngredientsFromDetails(recipesToReturn);
                for (String recipeIngredient : recipeIngredients) {
                    if (!pantryIngredients.contains(recipeIngredient)) {
                        recipesToReturn.remove(recipe);
                    }
                }
            }
        }

        for (RecipeModel recipe : recipesToReturn) {
            System.out.println(recipe.isHealthy());
            if (isHealthy) {
                if (!recipe.isHealthy()) {
                    recipesToReturn.remove(recipe);
                }
            }
        }

        recipesToReturn.sort(new RatingSorter());

        if(recipesToReturn.isEmpty()){
            noRecipesCount++;
        }

        try {
            return (ArrayList<RecipeModel>) recipesToReturn.subList(0, 30);
        } catch (IndexOutOfBoundsException e) {
            return recipesToReturn;
        }
    }

    private ArrayList<RecipeModel> filterFavouritesYouCanCook() {
        ArrayList<RecipeModel> recipesToReturn = databaseMethods.getSearchResultRecipes(servingAmount, cookingTime, recipeType, isHealthy);;
        List<String> allergies = databaseMethods.getAllergiesForUser();

        if (!includeAllergies) {
            for (RecipeModel recipe : recipesToReturn) {
                ArrayList<String> recipeIngredients=splitRecipeIngredientsFromDetails(recipesToReturn);
                for (String allergy : allergies) {
                    if (recipeIngredients.contains(allergy)) {
                        recipesToReturn.remove(recipe);
                        break;
                    }
                }
            }
        }


        ArrayList<String> pantryIngredients = splitPantryIngredients();
        for (RecipeModel recipe : recipesToReturn) {
            ArrayList<String> recipeIngredients = splitRecipeIngredientsFromDetails(recipesToReturn);
            for (String recipeIngredient : recipeIngredients) {
                if (!pantryIngredients.contains(recipeIngredient)) {
                    recipesToReturn.remove(recipe);
                    break;
                }
            }

            if (isHealthy) {
                if (!recipe.isHealthy()) {
                    recipesToReturn.remove(recipe);
                }
            }
        }

        for(RecipeModel recipeModel:recipesToReturn){
            if(!databaseMethods.isFavourite(String.valueOf(recipeModel.getId()))){
                recipesToReturn.remove(recipeModel);
            }
        }

        if(recipesToReturn.isEmpty()){
            noRecipesCount++;
        }

        try {
            return (ArrayList<RecipeModel>) recipesToReturn.subList(0, 30);
        } catch (IndexOutOfBoundsException e) {
            return recipesToReturn;
        }
    }


    private ArrayList<String> splitCommaSeparatedString(String csvString) {
        String[] csvStringArray = csvString.replace(" ,", "").replace(", ", ",").split(",");
        return new ArrayList<>(Arrays.asList(csvStringArray));

    }

    private ArrayList<String> splitRecipeIngredientsFromDetails(ArrayList<RecipeModel> recipesToReturn) {
        ArrayList<String> recipeIngredients = new ArrayList<>();
        for (RecipeModel recipe : recipesToReturn) {
            for (String ingredient : recipe.getIngredients()) {
                String[] splitIngredientDetails = ingredient.replace(" - ", "-").split("-");
                recipeIngredients.add(splitIngredientDetails[0]);
            }
        }
        return recipeIngredients;
    }

    private ArrayList<String> splitPantryIngredients() {
        ArrayList<String> pantryIngredients = new ArrayList<>();
        for (PantryIngredientModel ingredient : databaseMethods.getPantryForUser()) {
            pantryIngredients.add(ingredient.getIngredientName());
        }
        return pantryIngredients;
    }
}