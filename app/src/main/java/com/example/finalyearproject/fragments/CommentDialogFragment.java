package com.example.finalyearproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.R;

import org.w3c.dom.Text;

public class CommentDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        String recipeId = getArguments().getString("recipeId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.comment_dialog, null);

        EditText commentInput = view.findViewById(R.id.commentDialogInput);

        builder.setView(view).setPositiveButton("Comment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseMethods databaseMethods=new DatabaseMethods(getContext());
                String comment = commentInput.getText().toString();
                if(comment.isEmpty()){
                    Toast.makeText(getContext(), "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                databaseMethods.addComment(recipeId,comment);
                Bundle b = new Bundle();
                b.putString("recipeId", recipeId);
                Fragment commentsFragment = new CommentsFragment();
                commentsFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, commentsFragment).addToBackStack(null).commit();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }


}
