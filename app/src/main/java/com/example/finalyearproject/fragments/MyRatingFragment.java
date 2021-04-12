package com.example.finalyearproject.fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRatingFragment extends Fragment {

    View view;
    DatabaseMethods databaseMethods;
    int myRating;

    public MyRatingFragment() {
        // Required empty public constructor
    }


    public static MyRatingFragment newInstance() {
        MyRatingFragment fragment = new MyRatingFragment();
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
        view = inflater.inflate(R.layout.fragment_my_rating, container, false);
        databaseMethods=new DatabaseMethods(getContext());
        myRating = databaseMethods.getMyRating();
        myRating = Math.round(myRating);

        TextView noRating = view.findViewById(R.id.noRatingText);
        ImageView star1 = view.findViewById(R.id.rateStar1);
        ImageView star2 = view.findViewById(R.id.rateStar2);
        ImageView star3 = view.findViewById(R.id.rateStar3);
        ImageView star4 = view.findViewById(R.id.rateStar4);
        ImageView star5 = view.findViewById(R.id.rateStar5);

        if(myRating==0){
            noRating.setVisibility(View.VISIBLE);
            star1.setVisibility(View.INVISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
            star4.setVisibility(View.INVISIBLE);
            star5.setVisibility(View.INVISIBLE);
        }

        if(myRating==1){
            star1.setImageResource(R.drawable.ic_large_solid_star);
        }else if(myRating==2){
            star1.setImageResource(R.drawable.ic_large_solid_star);
            star2.setImageResource(R.drawable.ic_large_solid_star);
        }else if(myRating==3){
            star1.setImageResource(R.drawable.ic_large_solid_star);
            star2.setImageResource(R.drawable.ic_large_solid_star);
            star3.setImageResource(R.drawable.ic_large_solid_star);
        }else if(myRating==4){
            star1.setImageResource(R.drawable.ic_large_solid_star);
            star2.setImageResource(R.drawable.ic_large_solid_star);
            star3.setImageResource(R.drawable.ic_large_solid_star);
            star4.setImageResource(R.drawable.ic_large_solid_star);
        }else if(myRating==5){
            star1.setImageResource(R.drawable.ic_large_solid_star);
            star2.setImageResource(R.drawable.ic_large_solid_star);
            star3.setImageResource(R.drawable.ic_large_solid_star);
            star4.setImageResource(R.drawable.ic_large_solid_star);
            star5.setImageResource(R.drawable.ic_large_solid_star);
        }

        return view;
    }
}