package com.example.finalyearproject.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.finalyearproject.R;
import com.google.android.material.slider.Slider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    View view;

    Button searchButton;
    TextView servingText;
    TextView cookingTimeText;
    Slider servingSlider;
    Spinner recipeTypeSpinner;
    Slider cookingTimeSlider;
    CheckBox healthyCheckBox;
    EditText keywordsInput;
    CheckBox onlyPantryIngredientsCheck;
    CheckBox allergyCheckbox;
    String[] recipeTypes = {"All", "Vegetarian", "Vegan"};

    ListView pantryIngredientList;



    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        view = inflater.inflate(R.layout.fragment_search, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search");

        servingText = view.findViewById(R.id.searchServingText);
        cookingTimeText = view.findViewById(R.id.searchCookTimeText);
        servingSlider = view.findViewById(R.id.searchServingSlider);
        recipeTypeSpinner = view.findViewById(R.id.searchTypeSpinner);
        cookingTimeSlider = view.findViewById(R.id.searchCookTimeSlider);
        healthyCheckBox = view.findViewById(R.id.searchHealthyCheckbox);
        keywordsInput = view.findViewById(R.id.searchKeywords);
        onlyPantryIngredientsCheck = view.findViewById(R.id.searchPantryIngredientCheckbox);
        recipeTypeSpinner.setAdapter(new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, recipeTypes));
        allergyCheckbox = view.findViewById(R.id.searchAllergyCheckbox);

        setOnSlideListeners();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClicked();
            }
        });

        return view;
    }


    private void setOnSlideListeners(){
        servingSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int valueOfFloat = ((int) value);
                switch (valueOfFloat){
                    case 1:
                        servingText.setText("1 Person");
                        break;
                    case 2:
                        servingText.setText("2 People");
                        break;
                    case 3:
                        servingText.setText("1-2 People");
                        break;
                    case 4:
                        servingText.setText("2-4 People");
                        break;
                    case 5:
                        servingText.setText("4-6 People");
                        break;
                    case 6:
                        servingText.setText("6+ People");
                        break;
                }
            }
        });
        cookingTimeSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                String textToSet=null;
                switch ((int)value){
                    case 1:
                        textToSet=("5 Minutes");
                        break;
                    case 2:
                        textToSet=("10 Minutes");
                        break;
                    case 3:
                        textToSet=("15 Minutes");
                        break;
                    case 4:
                        textToSet=("20 Minutes");
                        break;
                    case 5:
                        textToSet=("30 Minutes");
                        break;
                    case 6:
                        textToSet=("45 Minutes");
                        break;
                    case 7:
                        textToSet=("60 Minutes");
                        break;
                    case 8:
                        textToSet=("Over 60 Minutes");
                        break;
                }
                cookingTimeText.setText(textToSet);
            }
        });
    }


    private void searchClicked(){
        String servingAmount = servingText.getText().toString();
        String cookingTime = cookingTimeText.getText().toString();
        String recipeType = recipeTypeSpinner.getSelectedItem().toString();
        boolean isHealthy = healthyCheckBox.isChecked();
        String keywords = keywordsInput.getText().toString();
        boolean onlyPantryIngredients = onlyPantryIngredientsCheck.isChecked();
        boolean includeAllergies = allergyCheckbox.isChecked();

        Bundle b = new Bundle();
        b.putString("servingAmount", servingAmount);
        b.putString("cookingTime", cookingTime);
        b.putString("recipeType", recipeType);
        b.putBoolean("isHealthy", isHealthy);
        b.putString("keywords", keywords);
        b.putBoolean("onlyPantryIngredients", onlyPantryIngredients);
        b.putBoolean("includeAllergies", includeAllergies);

        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, fragment).addToBackStack(null).commit();
    }

}