package com.example.finalyearproject.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.finalyearproject.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanningDateSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanningDateSelectionFragment extends Fragment {

    String dayString;
    String monthString;
    TextView dateText;
    View view;

    public PlanningDateSelectionFragment() {
        // Required empty public constructor
    }

    public static PlanningDateSelectionFragment newInstance(String param1, String param2) {
        PlanningDateSelectionFragment fragment = new PlanningDateSelectionFragment();
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
        view = inflater.inflate(R.layout.fragment_planning_date_selection, container, false);

        dateText=view.findViewById(R.id.planningDateSelectionText);

        setDateDialog();

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateDialog();
            }
        });

        return view;
    }

    public void setDateDialog(){
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dayString = String.valueOf(dayOfMonth);
                monthString = String.valueOf(month+1);

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
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }
}