package com.example.finalyearproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalyearproject.CustomAdapters.RecipeRecyclerAdapter;
import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {

    View view;
    RecyclerView mostRelevantRecycler;
    RecyclerView topRecipeRecycler;
    RecyclerView favouritesRecycler;
    DatabaseMethods databaseMethods;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultFragment newInstance(String param1, String param2) {
        SearchResultFragment fragment = new SearchResultFragment();
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
        view= inflater.inflate(R.layout.fragment_search_result, container, false);
        databaseMethods= new DatabaseMethods(getContext());

        mostRelevantRecycler = view.findViewById(R.id.searchRecipeRecycler1);
        topRecipeRecycler = view.findViewById(R.id.searchRecipeRecycler2);
        favouritesRecycler = view.findViewById(R.id.searchRecipeRecycler3);

        //TODO Database methods to get corresponding recipe datasets
//        mostRelevantRecycler.setAdapter(new RecipeRecyclerAdapter(getContext(), databaseMethods.))
//        topRecipeRecycler.setAdapter(new RecipeRecyclerAdapter(getContext(), databaseMethods.));
//        favouritesRecycler.setAdapter(new RecipeRecyclerAdapter(getContext(), databaseMethods.));
        return view;
    }
}