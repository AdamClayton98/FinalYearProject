package com.example.finalyearproject.CustomAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeHolder> {

    private ArrayList<RecipeModel> dataSet;
    private Context mContext;

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
