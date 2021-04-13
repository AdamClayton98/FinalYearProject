package com.example.finalyearproject.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.finalyearproject.CustomAdapters.RecipeGVAdapter;
import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouritesFragment extends Fragment {


    View view;
    GridView recipesGV;
    DatabaseMethods databaseMethods;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouritesFragment newInstance(String param1, String param2) {
        FavouritesFragment fragment = new FavouritesFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favourites, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Favourites");
        databaseMethods=new DatabaseMethods(getContext());
        getAndDisplayRecipes();
        return view;
    }

    public void getAndDisplayRecipes(){
        ArrayList<RecipeModel> recipes = databaseMethods.getAllFavouritesForUser();

        RecipeGVAdapter adapter = new RecipeGVAdapter(getContext(), recipes);

        recipesGV = view.findViewById(R.id.gv_favouriteRecipes);

        recipesGV.setAdapter(adapter);

        recipesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putString("recipeId", String.valueOf(adapter.getItem(position).getId()));
                Fragment viewRecipeFragment = new ViewRecipeFragment();
                viewRecipeFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
            }
        });
    }
}