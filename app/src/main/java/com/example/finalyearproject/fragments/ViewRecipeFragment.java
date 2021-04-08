package com.example.finalyearproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.R;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewRecipeFragment extends Fragment {

    private String recipeId;
    View view;
    DatabaseMethods databaseMethods;
    ImageView recipeImage;
    TextView recipeName;
    TextView recipeCookingTime;
    TextView recipeServing;
    TextView recipeRating;
    Button rateButton;
    Button commentButton;


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
        recipeImage = view.findViewById(R.id.viewRecipeImage);
        recipeCookingTime = view.findViewById(R.id.viewRecipeCookingTime);
        recipeServing = view.findViewById(R.id.viewRecipeServing);
        recipeRating = view.findViewById(R.id.viewRecipeRating);
        rateButton = view.findViewById(R.id.rateRecipeButton);
        commentButton = view.findViewById(R.id.commentRecipeButton);

        databaseMethods.setRecipeImage(recipeImage, recipeModel);
        recipeName.setText(recipeModel.getRecipeName());
        recipeCookingTime.setText(recipeModel.getCookingTime());
        recipeServing.setText(recipeModel.getServes());
        recipeRating.setText(String.valueOf(recipeModel.getRating()));
        LayoutInflater textInflater = LayoutInflater.from(getContext());

        LinearLayout ingredientsLayout = view.findViewById(R.id.viewRecipeIngredientsLayout);
        for (String ingredient:recipeModel.getIngredients()) {
            View view = textInflater.inflate(android.R.layout.simple_list_item_1, ingredientsLayout, false);
            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(ingredient);
            ingredientsLayout.addView(view);
        }

        LinearLayout stepsLayout = view.findViewById(R.id.viewRecipeStepsLayout);
        for(String step:recipeModel.getSteps()){
            View view = textInflater.inflate(android.R.layout.simple_list_item_1, stepsLayout, false);
            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(step);
            stepsLayout.addView(view);
        }

        setButtonOnClickListeners();


        return view;
    }

    public void setButtonOnClickListeners(){
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new RateRecipeDialogFragment();
                Bundle b = new Bundle();
                b.putString("recipeId", recipeId);
                dialogFragment.setArguments(b);
                dialogFragment.show(getFragmentManager(), "viewRecipeFragment");
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("recipeId", String.valueOf(recipeId));
                Fragment commentsFragment = new CommentsFragment();
                commentsFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, commentsFragment).addToBackStack(null).commit();
            }
        });
    }
}