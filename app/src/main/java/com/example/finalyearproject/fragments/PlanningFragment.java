package com.example.finalyearproject.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
    Button cookButton1;
    Button cookButton2;
    Button cookButton3;
    TextView mealSubtitle1;
    TextView mealSubtitle2;
    TextView mealSubtitle3;
    TextView mealTitle1;
    TextView mealTitle2;
    TextView mealTitle3;


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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Planning");

        addButton1=view.findViewById(R.id.planningAddButton1);
        addButton2=view.findViewById(R.id.planningAddButton2);
        addButton3=view.findViewById(R.id.planningAddButton3);
        removeButton1=view.findViewById(R.id.planningRemoveButton1);
        removeButton2=view.findViewById(R.id.planningRemoveButton2);
        removeButton3=view.findViewById(R.id.planningRemoveButton3);
        mealSubtitle1=view.findViewById(R.id.planningMealOneRecipe);
        mealSubtitle2=view.findViewById(R.id.planningMealTwoRecipe);
        mealSubtitle3=view.findViewById(R.id.planningMealThreeRecipe);
        cookButton1=view.findViewById(R.id.planningCookButton1);
        cookButton2=view.findViewById(R.id.planningCookButton2);
        cookButton3=view.findViewById(R.id.planningCookButton3);
        mealTitle1=view.findViewById(R.id.planningMealOneTitle);
        mealTitle2=view.findViewById(R.id.planningMealTwoTitle);
        mealTitle3=view.findViewById(R.id.planningMealThreeTitle);

        dateHeader=view.findViewById(R.id.planningDateSelected);
        dateHeader.setText(selectedDate);

        databaseMethods = new DatabaseMethods(getContext());

        ArrayList<PlanModel> plansForSelectedDate = databaseMethods.getPlanOnDate(selectedDate);

        for(PlanModel plan:plansForSelectedDate){
            if(plan.getMealNumber() == 1){
                addButton1.setVisibility(View.INVISIBLE);
                removeButton1.setVisibility(View.VISIBLE);
                cookButton1.setVisibility(View.VISIBLE);
                recipeId1=plan.getRecipeId();
                planId1=plan.getId();
                mealSubtitle1.setText(databaseMethods.getIndividualRecipe(recipeId1).getRecipeName());
                mealTitle1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewRecipeFragment viewRecipeFragment=new ViewRecipeFragment();
                        Bundle b = new Bundle();
                        b.putString("recipeId", String.valueOf(recipeId1));
                        viewRecipeFragment.setArguments(b);
                        getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
                    }
                });
            }else if(plan.getMealNumber() == 2){
                addButton2.setVisibility(View.INVISIBLE);
                removeButton2.setVisibility(View.VISIBLE);
                cookButton2.setVisibility(View.VISIBLE);
                recipeId2=plan.getRecipeId();
                planId2=plan.getId();
                mealSubtitle2.setText(databaseMethods.getIndividualRecipe(recipeId2).getRecipeName());
                mealTitle2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewRecipeFragment viewRecipeFragment=new ViewRecipeFragment();
                        Bundle b = new Bundle();
                        b.putString("recipeId", String.valueOf(recipeId2));
                        viewRecipeFragment.setArguments(b);
                        getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
                    }
                });
            }else if(plan.getMealNumber() == 3){
                addButton3.setVisibility(View.INVISIBLE);
                removeButton3.setVisibility(View.VISIBLE);
                cookButton3.setVisibility(View.VISIBLE);
                recipeId3=plan.getRecipeId();
                planId3=plan.getId();
                mealSubtitle3.setText(databaseMethods.getIndividualRecipe(recipeId3).getRecipeName());
                mealTitle3.setOnClickListener(new View.OnClickListener() {
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
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, fragment).addToBackStack(null).commit();
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
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, fragment).addToBackStack(null).commit();
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
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, fragment).addToBackStack(null).commit();
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
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, planningFragment).addToBackStack(null).commit();
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
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, planningFragment).addToBackStack(null).commit();
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
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, planningFragment).addToBackStack(null).commit();
            }
        });

        cookButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Ingredients will be removed from your pantry if cooked.");
                builder.setMessage("Do you want to continue?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseMethods.cookRecipeAndRemoveFromPantry(recipeId1);
                                ViewRecipeFragment viewRecipeFragment=new ViewRecipeFragment();
                                Bundle b = new Bundle();
                                b.putString("recipeId", String.valueOf(recipeId1));
                                viewRecipeFragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        cookButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Ingredients will be removed from your pantry if cooked.");
                builder.setMessage("Do you want to continue?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseMethods.cookRecipeAndRemoveFromPantry(recipeId2);
                                ViewRecipeFragment viewRecipeFragment=new ViewRecipeFragment();
                                Bundle b = new Bundle();
                                b.putString("recipeId", String.valueOf(recipeId2));
                                viewRecipeFragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        cookButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Ingredients will be removed from your pantry if cooked.");
                builder.setMessage("Do you want to continue?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseMethods.cookRecipeAndRemoveFromPantry(recipeId3);
                                ViewRecipeFragment viewRecipeFragment=new ViewRecipeFragment();
                                Bundle b = new Bundle();
                                b.putString("recipeId", String.valueOf(recipeId3));
                                viewRecipeFragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, planningFragment).addToBackStack(null).commit();
            }
        }, year,month,day);

        datePickerDialog.show();
    }
}