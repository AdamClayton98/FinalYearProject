package com.example.finalyearproject.CustomAdapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.MainActivity;
import com.example.finalyearproject.Models.CommentModel;
import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.R;
import com.example.finalyearproject.fragments.CommentsFragment;


import java.util.ArrayList;

public class CommentListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    private ArrayList<CommentModel> dataSet;
    DatabaseMethods databaseMethods;
    FragmentManager fragmentManager;

    public CommentListAdapter(@NonNull Context context, ArrayList<CommentModel> commentList, FragmentManager fragmentManager) {
        this.dataSet=commentList;
        this.layoutInflater= (LayoutInflater.from(context));
        this.fragmentManager = fragmentManager;
    }


    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public CommentModel getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.row_comment_item, parent, false);
            CommentModel commentModel = (CommentModel) getItem(position);
            TextView commentPublisher = convertView.findViewById(R.id.commentPublisher);
            TextView commentText = convertView.findViewById(R.id.commentText);
            TextView reportButton = convertView.findViewById(R.id.commentReportText);
            TextView removeButton = convertView.findViewById(R.id.commentRemoveText);
            if(commentModel.getUid().equals(MainActivity.uid)){
                removeButton.setVisibility(View.VISIBLE);
            }
            commentPublisher.setText(commentModel.getPublisher());
            commentText.setText(commentModel.getCommentText());

            reportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseMethods = new DatabaseMethods(context);
                    databaseMethods.deleteComment(commentModel.getId());
                    Bundle b=new Bundle();
                    b.putString("recipeId", String.valueOf(commentModel.getRecipeId()));
                    Fragment fragment = new CommentsFragment();
                    fragment.setArguments(b);
                    fragmentManager.beginTransaction().replace(R.id.fl_wrapper, fragment).commit();
                }
            });
        }
        return convertView;
    }
}
