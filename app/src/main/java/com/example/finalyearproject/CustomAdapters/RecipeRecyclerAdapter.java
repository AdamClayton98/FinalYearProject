package com.example.finalyearproject.CustomAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.R;
import com.example.finalyearproject.fragments.ViewRecipeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeHolder> {

    private ArrayList<RecipeModel> dataSet;
    private Context mContext;
    public boolean isItemSelected;

    public RecipeRecyclerAdapter(Context context, ArrayList<RecipeModel> recipes) {
        mContext = context;
        dataSet = recipes;
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        TextView recipeName = itemView.findViewById(R.id.cardRecipeName);
        TextView rating = itemView.findViewById(R.id.cardRecipeRating);
        ImageView recipeImage = itemView.findViewById(R.id.cardRecipeImage);

        public RecipeHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
        }


        public void bindRecipe(RecipeModel recipeModel) {
            recipeName.setText(recipeModel.getRecipeName());
            rating.setText(String.valueOf(recipeModel.getRating()));
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            final long MB=1024*1024;
            storageReference.child("images/" + recipeModel.getUuid()).getBytes(MB).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap imageBitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    recipeImage.setImageBitmap(imageBitmap);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putString("recipeId", String.valueOf(recipeModel.getId()));
                    Fragment viewRecipeFragment = new ViewRecipeFragment();
                    viewRecipeFragment.setArguments(b);
                    FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack(null).commit();
                }
            });
            recipeImage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        recipeName.setSelected(true);
                        isItemSelected=true;
                    } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                        recipeName.setSelected(false);
                        isItemSelected=false;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        recipeName.setSelected(false);
                        isItemSelected=false;
                    }
                    return true;
                }
            });

        }
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_card, parent, false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        holder.bindRecipe(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
