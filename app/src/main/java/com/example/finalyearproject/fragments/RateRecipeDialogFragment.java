package com.example.finalyearproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.R;

public class RateRecipeDialogFragment extends DialogFragment {
    int rating=0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        String recipeId = getArguments().getString("recipeId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.rate_dialog, null);

        builder.setView(view).setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseMethods databaseMethods=new DatabaseMethods(getContext());
                databaseMethods.addRating(recipeId,rating);
                Bundle b = new Bundle();
                b.putString("recipeId", recipeId);
                Fragment viewRecipeFragment = new ViewRecipeFragment();
                viewRecipeFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        ImageView star1 = view.findViewById(R.id.rateStar1);
        ImageView star2 = view.findViewById(R.id.rateStar2);
        ImageView star3 = view.findViewById(R.id.rateStar3);
        ImageView star4 = view.findViewById(R.id.rateStar4);
        ImageView star5 = view.findViewById(R.id.rateStar5);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star2.setImageResource(R.drawable.ic_baseline_star_outline_24);
                star3.setImageResource(R.drawable.ic_baseline_star_outline_24);
                star4.setImageResource(R.drawable.ic_baseline_star_outline_24);
                star5.setImageResource(R.drawable.ic_baseline_star_outline_24);
                rating=1;
            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star2.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star3.setImageResource(R.drawable.ic_baseline_star_outline_24);
                star4.setImageResource(R.drawable.ic_baseline_star_outline_24);
                star5.setImageResource(R.drawable.ic_baseline_star_outline_24);
                rating=2;
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star2.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star3.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star4.setImageResource(R.drawable.ic_baseline_star_outline_24);
                star5.setImageResource(R.drawable.ic_baseline_star_outline_24);
                rating=3;
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star2.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star3.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star4.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star5.setImageResource(R.drawable.ic_baseline_star_outline_24);
                rating=4;
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star2.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star3.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star4.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                star5.setImageResource(R.drawable.ic_baseline_star_large_black_100);
                rating=5;
            }
        });

        return builder.create();
    }


}
