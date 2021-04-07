package com.example.finalyearproject.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddToPantryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddToPantryFragment extends Fragment {

    Spinner measurementDropdown;
    Button addButton;
    DatabaseMethods databaseMethods;
    EditText ingredientInput;
    EditText amountInput;
    EditText expiryInput;
    DatePickerDialog datePickerDialog;
    String dateRegex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";


    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddToPantryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddToPantryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddToPantryFragment newInstance(String param1, String param2) {
        AddToPantryFragment fragment = new AddToPantryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_to_pantry, container, false);

        ingredientInput=view.findViewById(R.id.ingredientInput);
        amountInput=view.findViewById(R.id.amountInput);
        expiryInput = view.findViewById(R.id.expiryDateInput);
        databaseMethods=new DatabaseMethods(getContext());
        measurementDropdown = view.findViewById(R.id.measurementTypeDropdownPantry);
        String[] measurementTypes = new String[]{"ML", "Grams", "Tbsp", "oz", "lb", "Unit(s)"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, measurementTypes);
        measurementDropdown.setAdapter(arrayAdapter);
        addButton = view.findViewById(R.id.addToPantryAddButton);

        expiryInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                        expiryInput.setText(dayString+"/"+monthString+"/"+year);
                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient = ingredientInput.getText().toString();
                if(ingredient.isEmpty()){
                    Toast.makeText(getContext(), "Ingredient name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(amountInput.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                int amount = Integer.parseInt(amountInput.getText().toString());

                String expiryDate = expiryInput.getText().toString();

                String measurementType = measurementDropdown.getSelectedItem().toString();


                if(expiryDate.isEmpty()){
                    Toast.makeText(getContext(), "Expiry date cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!expiryDate.matches(dateRegex)){
                    Toast.makeText(getContext(), "Date must be in dd/mm/yyyy format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(measurementType.isEmpty()){
                    Toast.makeText(getContext(), "Measurement type cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseMethods.addIngredientToPantry(ingredient,amount,measurementType,expiryDate);

                Toast.makeText(getContext(), "Added " + ingredient + " to pantry.", Toast.LENGTH_SHORT).show();

                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, new PantryFragment()).commit();
            }
        });

        return view;
    }
}