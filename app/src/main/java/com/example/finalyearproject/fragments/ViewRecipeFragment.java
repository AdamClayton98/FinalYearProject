package com.example.finalyearproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewRecipeFragment extends Fragment {

    private String recipeId;
    View view;
    DatabaseMethods databaseMethods;
    TextView recipeName;

    public ViewRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewRecipeFragment newInstance(String param1, String param2) {
        ViewRecipeFragment fragment = new ViewRecipeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeId = getArguments().getString("recipeId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_recipe, container, false);
        databaseMethods=new DatabaseMethods(getContext());
        int recipeIdAsInt = Integer.parseInt(recipeId);
        RecipeModel recipeModel = databaseMethods.getIndividualRecipe(recipeIdAsInt);
        recipeName = view.findViewById(R.id.viewRecipeName);
        recipeName.setText(recipeModel.getRecipeName());


        return view;
    }
}