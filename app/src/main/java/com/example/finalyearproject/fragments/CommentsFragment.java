package com.example.finalyearproject.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.finalyearproject.CustomAdapters.CommentListAdapter;
import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.Models.CommentModel;
import com.example.finalyearproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentsFragment extends Fragment {


    String recipeId;
    View view;
    DatabaseMethods databaseMethods;

    public CommentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommentsFragment newInstance(String param1, String param2) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeId = getArguments().getString("recipeId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_comments, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Comments");

        databaseMethods = new DatabaseMethods(getContext());

        Button addCommentButton = view.findViewById(R.id.addCommentButton);
        ArrayList<CommentModel>  comments = databaseMethods.getComments(String.valueOf(recipeId));
        CommentListAdapter commentListAdapter = new CommentListAdapter(getContext(), comments, getFragmentManager());
        ListView commentList = view.findViewById(R.id.commentListLayout);
        commentList.setAdapter(commentListAdapter);

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new CommentDialogFragment();
                Bundle b = new Bundle();
                b.putString("recipeId", String.valueOf(recipeId));
                dialogFragment.setArguments(b);
                dialogFragment.show(getFragmentManager(), "commentsFragment");
            }
        });


        return view;
    }
}