package com.example.finalyearproject.CustomAdapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.finalyearproject.Models.PantryIngredientModel;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class PantryCustomAdapter extends BaseAdapter {

    private ArrayList<PantryIngredientModel> dataSet;
    LayoutInflater layoutInflater;
    SparseBooleanArray checkStates;

    public PantryCustomAdapter(Context context, ArrayList<PantryIngredientModel> pantry){
        this.dataSet=pantry;
        this.layoutInflater= (LayoutInflater.from(context));
        checkStates = new SparseBooleanArray(dataSet.size());
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public PantryIngredientModel getItem(int position) {
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
            CheckBox checkBox = convertView.findViewById(R.id.removeFromPantryCheckbox);

            PantryIngredientModel currentIngredient = (PantryIngredientModel) getItem(position);

            ingredientName.setText(currentIngredient.getIngredientName());
            amount.setText(currentIngredient.getAmount());
            expiry.setText(currentIngredient.getExpiryDate());
            checkBox.setTag(position);
            setChecked(position);

            checkBox.setOnCheckedChangeListener(listener);
        }

        return convertView;
    }

    public boolean isChecked(int position){
        return checkStates.get(position,false);
    }

    public void setChecked(int position){
        checkStates.put(position, isChecked(position));
    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            checkStates.put((Integer)buttonView.getTag(), isChecked);
        }
    };

}
