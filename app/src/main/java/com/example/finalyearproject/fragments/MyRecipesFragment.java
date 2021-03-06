package com.example.finalyearproject.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.finalyearproject.CustomAdapters.RecipeGVAdapter;
import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRecipesFragment extends Fragment {

    View view;
    FloatingActionButton toAddRecipeButton;
    DatabaseMethods databaseMethods;
    GridView recipesGV;


    public MyRecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyRecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyRecipesFragment newInstance(String param1, String param2) {
        MyRecipesFragment fragment = new MyRecipesFragment();
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
        view= inflater.inflate(R.layout.fragment_my_recipes, container, false);
        databaseMethods = new DatabaseMethods(getContext());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Recipes");

        setListenerToAddRecipeButton();
        getAndDisplayRecipes();


        return view;
    }

    public void setListenerToAddRecipeButton(){
        toAddRecipeButton = view.findViewById(R.id.toAddRecipeButton);
        toAddRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper,new AddRecipeFragment()).addToBackStack(null).commit();
            }
        });
    }

    public void getAndDisplayRecipes(){
        ArrayList<RecipeModel> recipes = databaseMethods.getMyRecipes();

        RecipeGVAdapter adapter = new RecipeGVAdapter(getContext(), recipes);

        recipesGV = view.findViewById(R.id.gv_myrecipes);

        recipesGV.setAdapter(adapter);

        recipesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!adapter.isItemSelected){
                    Bundle b = new Bundle();
                    b.putString("recipeId", String.valueOf(adapter.getItem(position).getId()));
                    Fragment viewRecipeFragment = new ViewRecipeFragment();
                    viewRecipeFragment.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
                }
            }
        });
    }

}