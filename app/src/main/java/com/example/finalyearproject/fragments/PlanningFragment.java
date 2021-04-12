package com.example.finalyearproject.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.Models.PlanModel;
import com.example.finalyearproject.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanningFragment extends Fragment {

    View view;
    DatePickerDialog datePickerDialog;
    TextView dateHeader;
    String selectedDate;
    DatabaseMethods databaseMethods;
    int recipeId1;
    int recipeId2;
    int recipeId3;
    int planId1;
    int planId2;
    int planId3;

    Button addButton1;
    Button addButton2;
    Button addButton3;
    Button removeButton1;
    Button removeButton2;
    Button removeButton3;
    Button viewButton1;
    Button viewButton2;
    Button viewButton3;
    TextView mealSubtitle1;
    TextView mealSubtitle2;
    TextView mealSubtitle3;


    public PlanningFragment() {
        // Required empty public constructor
    }


    public static PlanningFragment newInstance() {
        PlanningFragment fragment = new PlanningFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedDate=getArguments().getString("selectedDate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_planning, container, false);

        addButton1=view.findViewById(R.id.planningAddButton1);
        addButton2=view.findViewById(R.id.planningAddButton2);
        addButton3=view.findViewById(R.id.planningAddButton3);
        removeButton1=view.findViewById(R.id.planningRemoveButton1);
        removeButton2=view.findViewById(R.id.planningRemoveButton2);
        removeButton3=view.findViewById(R.id.planningRemoveButton3);
        mealSubtitle1=view.findViewById(R.id.planningMealOneRecipe);
        mealSubtitle2=view.findViewById(R.id.planningMealTwoRecipe);
        mealSubtitle3=view.findViewById(R.id.planningMealThreeRecipe);
        viewButton1=view.findViewById(R.id.planningViewButton1);
        viewButton2=view.findViewById(R.id.planningViewButton2);
        viewButton3=view.findViewById(R.id.planningViewButton3);

        dateHeader=view.findViewById(R.id.planningDateSelected);
        dateHeader.setText(selectedDate);

        databaseMethods = new DatabaseMethods(getContext());

        ArrayList<PlanModel> plansForSelectedDate = databaseMethods.getPlanOnDate(selectedDate);

        for(PlanModel plan:plansForSelectedDate){
            if(plan.getMealNumber() == 1){
                addButton1.setVisibility(View.INVISIBLE);
                removeButton1.setVisibility(View.VISIBLE);
                viewButton1.setVisibility(View.VISIBLE);
                recipeId1=plan.getRecipeId();
                planId1=plan.getId();
                mealSubtitle1.setText(databaseMethods.getIndividualRecipe(recipeId1).getRecipeName());
            }else if(plan.getMealNumber() == 2){
                addButton2.setVisibility(View.INVISIBLE);
                removeButton2.setVisibility(View.VISIBLE);
                viewButton2.setVisibility(View.VISIBLE);
                recipeId2=plan.getRecipeId();
                planId2=plan.getId();
                mealSubtitle2.setText(databaseMethods.getIndividualRecipe(recipeId2).getRecipeName());
            }else if(plan.getMealNumber() == 3){
                addButton3.setVisibility(View.INVISIBLE);
                removeButton3.setVisibility(View.VISIBLE);
                viewButton3.setVisibility(View.VISIBLE);
                recipeId3=plan.getRecipeId();
                planId3=plan.getId();
                mealSubtitle3.setText(databaseMethods.getIndividualRecipe(recipeId3).getRecipeName());
            }
        }

        createButtonListeners();

        dateHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        return view;
    }


    public void createButtonListeners(){
        addButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanningSelectionFragment fragment = new PlanningSelectionFragment();
                Bundle b = new Bundle();
                b.putInt("mealNumber", 1);
                b.putString("selectedDate", selectedDate);
                fragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, fragment).commit();
            }
        });

        addButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanningSelectionFragment fragment = new PlanningSelectionFragment();
                Bundle b = new Bundle();
                b.putInt("mealNumber", 2);
                b.putString("selectedDate", selectedDate);
                fragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, fragment).commit();
            }
        });

        addButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanningSelectionFragment fragment = new PlanningSelectionFragment();
                Bundle b = new Bundle();
                b.putInt("mealNumber", 3);
                b.putString("selectedDate", selectedDate);
                fragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, fragment).commit();
            }
        });


        removeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseMethods.removePlan(planId1);
                Bundle b = new Bundle();
                b.putString("selectedDate", selectedDate);
                PlanningFragment planningFragment = new PlanningFragment();
                planningFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, planningFragment).commit();
            }
        });

        removeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseMethods.removePlan(planId2);
                Bundle b = new Bundle();
                b.putString("selectedDate", selectedDate);
                PlanningFragment planningFragment = new PlanningFragment();
                planningFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, planningFragment).commit();
            }
        });


        removeButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseMethods.removePlan(planId3);
                Bundle b = new Bundle();
                b.putString("selectedDate", selectedDate);
                PlanningFragment planningFragment = new PlanningFragment();
                planningFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, planningFragment).commit();
            }
        });

        viewButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewRecipeFragment viewRecipeFragment=new ViewRecipeFragment();
                Bundle b = new Bundle();
                b.putString("recipeId", String.valueOf(recipeId1));
                viewRecipeFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
            }
        });

        viewButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewRecipeFragment viewRecipeFragment=new ViewRecipeFragment();
                Bundle b = new Bundle();
                b.putString("recipeId", String.valueOf(recipeId2));
                viewRecipeFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
            }
        });

        viewButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewRecipeFragment viewRecipeFragment=new ViewRecipeFragment();
                Bundle b = new Bundle();
                b.putString("recipeId", String.valueOf(recipeId3));
                viewRecipeFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
            }
        });
    }

    public void openDatePicker(){
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dayString = String.valueOf(dayOfMonth);
                String monthString = String.valueOf(month+1);

                if(dayString.length()==1){
                    dayString="0"+dayString;
                }

                if(monthString.length()==1){
                    monthString="0"+monthString;
                }
                Bundle b = new Bundle();
                b.putString("selectedDate", dayString+"/"+monthString+"/"+year);
                PlanningFragment planningFragment = new PlanningFragment();
                planningFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, planningFragment).commit();
            }
        }, year,month,day);

        datePickerDialog.show();
    }
}