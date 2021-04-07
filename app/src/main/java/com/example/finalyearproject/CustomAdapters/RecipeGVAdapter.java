package com.example.finalyearproject.CustomAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.Models.PantryIngredientModel;
import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecipeGVAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    private ArrayList<RecipeModel> dataSet;

    public RecipeGVAdapter(@NonNull Context context, ArrayList<RecipeModel> recipeArrayList) {
        this.dataSet=recipeArrayList;
        this.layoutInflater= (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public RecipeModel getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.layout_recipe_card, parent, false);
            RecipeModel recipeModel = (RecipeModel) getItem(position);
            TextView recipeName = convertView.findViewById(R.id.cardRecipeName);
            TextView rating = convertView.findViewById(R.id.cardRecipeRating);
            ImageView recipeImage = convertView.findViewById(R.id.cardRecipeImage);
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            final long MB=1024*1024;
            storageReference.child("images/" + recipeModel.getUuid()).getBytes(MB).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap imageBitmap=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    recipeImage.setImageBitmap(imageBitmap);
                }
            });

            recipeName.setText(recipeModel.getRecipeName());
            rating.setText(String.valueOf(recipeModel.getRating()));

        }

        return convertView;
    }
}
