package com.example.finalyearproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.R;

public class ReportDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        boolean isRecipe = getArguments().getBoolean("isRecipe");
        String id = getArguments().getString("Id");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.report_dialog, null);

        EditText reportInput = view.findViewById(R.id.reportDialogInput);

        builder.setView(view).setPositiveButton("Report", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseMethods databaseMethods=new DatabaseMethods(getContext());
                String reportText = reportInput.getText().toString();
                if(reportText.isEmpty()){
                    Toast.makeText(getContext(), "Report reason cannot be empty", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                if(isRecipe){
                    databaseMethods.reportRecipe(id, reportText);
                    Bundle b = new Bundle();
                    b.putString("recipeId", id);
                    Fragment viewRecipeFragment = new ViewRecipeFragment();
                    viewRecipeFragment.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, viewRecipeFragment).addToBackStack("recipeFragment").addToBackStack(null).commit();
                }else{
                    databaseMethods.reportComment(id,reportText);
                    Bundle b = new Bundle();
                    b.putString("recipeId", databaseMethods.getRecipeIdForCommentId(id));
                    CommentsFragment commentsFragment = new CommentsFragment();
                    commentsFragment.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, commentsFragment).addToBackStack("commentsFragment").addToBackStack(null).commit();
                }
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
