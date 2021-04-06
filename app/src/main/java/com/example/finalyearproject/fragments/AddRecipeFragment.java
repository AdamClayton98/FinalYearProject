package com.example.finalyearproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.R;
import com.google.android.gms.common.SignInButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecipeFragment extends Fragment {

    View view;
    EditText recipeNameInput;
    Spinner cookingTimeDropdown;
    Spinner servingDropdown;
    LinearLayout ingredientListLayout;
    Button addIngredientButton;
    String[] measurementTypes = new String[]{"ML", "Grams", "Tbsp", "oz", "lb", "Unit(s)"};
    String[] cookingTimes = new String[]{"5 Minutes", "10 Minutes", "15 Minutes", "20 Minutes", "30 Minutes", "45 Minutes", "60 Minutes", "Over 60 Minutes"};
    String[] serves = new String[]{"1 Person", "2 People", "1-2 People", "2-4 People", "4-6 People", "6+ People"};
    Button addStepButton;
    LinearLayout stepListLayout;
    Button uploadRecipeButton;
    DatabaseMethods databaseMethods;


    public AddRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecipeFragment newInstance(String param1, String param2) {
        AddRecipeFragment fragment = new AddRecipeFragment();
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

        view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        databaseMethods = new DatabaseMethods(getContext());

        recipeNameInput = view.findViewById(R.id.addRecipeNameInput);

        ingredientListLayout = view.findViewById(R.id.layout_ingredient_list);
        addIngredientButton = view.findViewById(R.id.addIngredientToFormButton);

        stepListLayout = view.findViewById(R.id.layout_step_list);
        addStepButton = view.findViewById(R.id.addStepToFormButton);

        uploadRecipeButton = view.findViewById(R.id.uploadRecipeButton);

        servingDropdown = view.findViewById(R.id.servingDropdown);
        ArrayAdapter servingAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, serves);
        servingDropdown.setAdapter(servingAdapter);

        cookingTimeDropdown = view.findViewById(R.id.cookingTimeSpinner);
        ArrayAdapter cookingTimeAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, cookingTimes);
        cookingTimeDropdown.setAdapter(cookingTimeAdapter);


        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientView();
            }
        });

        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStepView();
            }
        });

        uploadRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRecipe();
            }
        });

        return view;
    }

    private void addIngredientView() {
        View ingredientView = getLayoutInflater().inflate(R.layout.row_add_ingredient, null, false);

        Spinner measurementType = ingredientView.findViewById(R.id.measurementTypeDropdownRecipe);
        ImageView removeImage = ingredientView.findViewById(R.id.imageRemove);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, measurementTypes);
        measurementType.setAdapter(arrayAdapter);

        ingredientListLayout.addView(ingredientView);

        removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIngredientView(ingredientView);
            }
        });
    }

    private void removeIngredientView(View v) {
        ingredientListLayout.removeView(v);
    }

    private void addStepView() {
        View stepView = getLayoutInflater().inflate(R.layout.row_add_step, null, false);

        ImageView removeStepImage = stepView.findViewById(R.id.imageRemoveStep);

        stepListLayout.addView(stepView);

        removeStepImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStepView(stepView);
            }
        });
    }

    private void removeStepView(View v) {
        stepListLayout.removeView(v);
    }


    private ArrayList<String> getAllIngredients() {
        ArrayList<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientListLayout.getChildCount(); i++) {
            EditText ingredientInput = ingredientListLayout.getChildAt(i).findViewById(R.id.ingredientNameInput);
            EditText amountInput = ingredientListLayout.getChildAt(i).findViewById(R.id.ingredientAmountInput);
            Spinner measurementTypeDropdown = ingredientListLayout.getChildAt(i).findViewById(R.id.measurementTypeDropdownRecipe);
            String ingredient = ingredientInput.getText().toString();
            String amount = amountInput.getText().toString();
            String measurementType = measurementTypeDropdown.getSelectedItem().toString();
            if (ingredient.isEmpty()) {
                Toast.makeText(getContext(), "You cannot leave any fields blank", Toast.LENGTH_SHORT).show();
                return null;
            } else if (amount.isEmpty() || amount.equals("0")) {
                Toast.makeText(getContext(), "You cannot leave any fields blank", Toast.LENGTH_SHORT).show();
                return null;
            }
            ingredients.add(ingredient + "," + amount + "," + measurementType);
        }
        return ingredients;
    }

    private ArrayList<String> getAllSteps() {
        ArrayList<String> steps = new ArrayList<>();
        for (int i = 0; i < stepListLayout.getChildCount(); i++) {
            EditText stepInput = stepListLayout.getChildAt(i).findViewById(R.id.stepInput);
            String step = stepInput.getText().toString();
            steps.add(step);
        }
        return steps;
    }


    private void uploadRecipe() {
        if (ingredientListLayout.getChildCount() == 0) {
            Toast.makeText(getContext(), "You must add at least 1 ingredient", Toast.LENGTH_SHORT).show();
            return;
        }

        if (stepListLayout.getChildCount() == 0) {
            Toast.makeText(getContext(), "You must add at least 1 step", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> ingredients = getAllIngredients();
        ArrayList<String> steps = getAllSteps();

        String recipeName = recipeNameInput.getText().toString();
        String cookingTime = cookingTimeDropdown.getSelectedItem().toString();
        String serves = servingDropdown.getSelectedItem().toString();


        if (ingredients.isEmpty()) {
            return;
        } else if (steps.isEmpty()) {
            return;
        }

        if (recipeName.isEmpty()) {
            Toast.makeText(getContext(), "You must add a recipe name", Toast.LENGTH_SHORT).show();
            return;
        }

        String ingredientsForDb = "";
        String stepsForDb = "";

        for(int i=0;i<ingredients.size();i++){
            if(i!=ingredients.size()-1){
                ingredientsForDb = ingredientsForDb + (ingredients.get(i)) + "|";
            }else{
                ingredientsForDb = ingredientsForDb + (ingredients.get(i));
            }
        }


        for(int i=0;i<steps.size();i++){
            if(i!=steps.size()-1){
                stepsForDb = stepsForDb + (steps.get(i)) + "|";
            }else{
                stepsForDb = stepsForDb + (steps.get(i));
            }
        }

        databaseMethods.addRecipe(recipeName, cookingTime, serves, ingredientsForDb, stepsForDb);
        getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, new MyRecipesFragment()).commit();
    }

}