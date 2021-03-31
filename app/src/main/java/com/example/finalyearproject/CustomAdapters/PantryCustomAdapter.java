package com.example.finalyearproject.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalyearproject.Models.PantryIngredientModel;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class PantryCustomAdapter extends BaseAdapter {

    private ArrayList<PantryIngredientModel> dataSet;
    LayoutInflater layoutInflater;

    public PantryCustomAdapter(Context context, ArrayList<PantryIngredientModel> pantry){
        this.dataSet=pantry;
        this.layoutInflater= (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=this.layoutInflater.inflate(R.layout.pantry_row_item, parent, false);

            TextView ingredientName=convertView.findViewById(R.id.ingredientName);
            TextView amount=convertView.findViewById(R.id.amountText);
            TextView expiry = convertView.findViewById(R.id.expiryDateText);

            PantryIngredientModel currentIngredient = (PantryIngredientModel) getItem(position);

            ingredientName.setText(currentIngredient.getIngredientName());
            amount.setText(currentIngredient.getAmount());
            expiry.setText(currentIngredient.getExpiryDate());

        }


        return convertView;
    }

    private static class ViewHolder {
        TextView txtIngredient;
        TextView txtAmount;
        TextView txtExpiry;
    }

}
