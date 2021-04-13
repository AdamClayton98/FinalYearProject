package com.example.finalyearproject.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
 * Use the {@link PlanningSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanningSelectionFragment extends Fragment {

    View view;
    GridView recipesGV;
    DatabaseMethods databaseMethods;
    String selectedDate;
    int mealNumber;

    public PlanningSelectionFragment() {
        // Required empty public constructor
    }


    public static PlanningSelectionFragment newInstance(String param1, String param2) {
        PlanningSelectionFragment fragment = new PlanningSelectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealNumber = getArguments().getInt("mealNumber");
        selectedDate = getArguments().getString("selectedDate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_planning_selection, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Planning");

        databaseMethods = new DatabaseMethods(getContext());

        ArrayList<RecipeModel> recipes = databaseMethods.getAllFavouritesForUser();

        RecipeGVAdapter adapter = new RecipeGVAdapter(getContext(), recipes);

        recipesGV = view.findViewById(R.id.planningSelectionGV);

        recipesGV.setAdapter(adapter);

        recipesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Add this recipe to selected meal plan?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseMethods.addPlan(String.valueOf(adapter.getItem(position).getId()), mealNumber, selectedDate);
                                Bundle b = new Bundle();
                                b.putString("selectedDate", selectedDate);
                                PlanningFragment planningFragment = new PlanningFragment();
                                planningFragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, planningFragment).addToBackStack(null).commit();
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


        return view;
    }
}