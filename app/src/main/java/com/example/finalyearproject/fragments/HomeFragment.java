package com.example.finalyearproject.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.finalyearproject.CustomAdapters.RecipeGVAdapter;
import com.example.finalyearproject.CustomAdapters.RecipeRecyclerAdapter;
import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.MainActivity;
import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    View view;
    DatabaseMethods databaseMethods;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        databaseMethods=new DatabaseMethods(getContext());
        TextView welcomeMessage = view.findViewById(R.id.homeWelcomeMessage);
        welcomeMessage.setText("Welcome " + databaseMethods.getUsernameOfUser(MainActivity.uid) + "!");

        setRecipeRecycler(databaseMethods.getMostViewedRecipes(), R.id.homeRecipeRecycler1);
        setRecipeRecycler(databaseMethods.getMostFavouritedRecipes(), R.id.homeRecipeRecycler2);
        setRecipeRecycler(databaseMethods.getRecipesToMakeUnder20Minutes(), R.id.homeRecipeRecycler3);


        return view;
    }

    public void setRecipeRecycler(ArrayList<RecipeModel> recipes, int resourceId ){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecipeRecyclerAdapter recipeRecyclerAdapter = new RecipeRecyclerAdapter(getContext(),recipes);
        RecyclerView recipeRecycler = view.findViewById(resourceId);
        recipeRecycler.setLayoutManager(layoutManager);
        recipeRecycler.setAdapter(recipeRecyclerAdapter);

    }

}