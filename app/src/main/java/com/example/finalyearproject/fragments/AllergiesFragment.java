package com.example.finalyearproject.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.MainActivity;
import com.example.finalyearproject.Models.AllergyModel;
import com.example.finalyearproject.R;

import java.util.List;
import java.util.Objects;

import static com.example.finalyearproject.DatabaseMethods.COLUMN_ALLERGY_NAME;
import static com.example.finalyearproject.DatabaseMethods.COLUMN_USERID;
import static com.example.finalyearproject.DatabaseMethods.TABLE_ALLERGIES;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllergiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllergiesFragment extends Fragment {

    ArrayAdapter<String> allergyModelArrayAdapter;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllergiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllergiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllergiesFragment newInstance(String param1, String param2) {
        AllergiesFragment fragment = new AllergiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        view = inflater.inflate(R.layout.fragment_allergies, container,false);
        refreshFragmentListView();
        // Inflate the layout for this fragment
        return view;
    }

    public List<String> getAllergiesInFragment(){
        DatabaseMethods databaseMethods = new DatabaseMethods(getContext());
        return databaseMethods.getAllergiesForUser();
    }

    public void refreshFragmentListView(){
        allergyModelArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getAllergiesInFragment());
        ListView listView= view.findViewById(R.id.allergyListView);
        listView.setAdapter(allergyModelArrayAdapter);
    }

    public boolean checkAllergyExists(String allergyName, SQLiteDatabase readDb) { ;
        String query = "SELECT " + COLUMN_ALLERGY_NAME + " FROM " + TABLE_ALLERGIES + " WHERE " + COLUMN_ALLERGY_NAME + " = '" + allergyName + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "'";
        Cursor cursor = readDb.rawQuery(query, null);
        boolean exists;
        exists = cursor.moveToFirst();
        cursor.close();
        readDb.close();
        return exists;
    }
}