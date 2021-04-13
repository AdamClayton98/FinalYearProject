package com.example.finalyearproject.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalyearproject.CustomAdapters.PantryCustomAdapter;
import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.Models.PantryIngredientModel;
import com.example.finalyearproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PantryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PantryFragment extends Fragment {

    View view;
    ListView pantryList;
    DatabaseMethods databaseMethods;
    Button removeButton;
    PantryCustomAdapter pantryCustomAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PantryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PantrtyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PantryFragment newInstance(String param1, String param2) {
        PantryFragment fragment = new PantryFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pantry, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Pantry");

        databaseMethods=new DatabaseMethods(getContext());

        createPantryListView();
        createRemoveListener();

        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createPantryListView(){
        ArrayList<PantryIngredientModel> ingredientModelList = databaseMethods.getPantryForUser();
        pantryList = view.findViewById(R.id.pantryList);
        pantryCustomAdapter = new PantryCustomAdapter(getContext(), ingredientModelList);
        pantryList.setAdapter(pantryCustomAdapter);
    }


    private void createRemoveListener(){
        removeButton = view.findViewById(R.id.removeFromPantryButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                for(int i=0;i<pantryCustomAdapter.getCount();i++){
                    if(pantryCustomAdapter.isChecked(i)){
                        String ingredientName = pantryCustomAdapter.getItem(i).getIngredientName();
                        databaseMethods.removeIngredient(ingredientName);
                        Toast.makeText(getContext(), ingredientName + " has been removed.", Toast.LENGTH_SHORT).show();
                    }
                }
                createPantryListView();
            }
        });
    }


}