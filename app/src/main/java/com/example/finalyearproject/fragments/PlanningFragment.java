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

import com.example.finalyearproject.R;

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

    Button addButton1;
    Button addButton2;
    Button addButton3;
    Button removeButton1;
    Button removeButton2;
    Button removeButton3;
    Button changeButton1;
    Button changeButton2;
    Button changeButton3;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_planning, container, false);

        dateHeader=view.findViewById(R.id.planningDateSelected);
        openDatePicker();

        dateHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        return view;
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

                dateHeader.setText(dayString+"/"+monthString+"/"+year);
            }
        }, year,month,day);

        datePickerDialog.show();
    }
}