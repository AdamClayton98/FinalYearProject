package com.example.finalyearproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.LoginActivity;
import com.example.finalyearproject.MainActivity;
import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class ReauthenticateDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        DatabaseMethods databaseMethods=new DatabaseMethods(getContext());

        View view = inflater.inflate(R.layout.reauthenticate_dialog, null);

        EditText passwordInput = view.findViewById(R.id.reauthDialogInput);

        builder.setView(view).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String passwordText = passwordInput.getText().toString();
                if(passwordText.isEmpty()){
                    Toast.makeText(getContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                AuthCredential credential = EmailAuthProvider.getCredential(FirebaseAuth.getInstance().getCurrentUser().getEmail(), passwordText);
                FirebaseAuth.getInstance().getCurrentUser().reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String newEmail = getArguments().getString("email");
                        String newPassword=getArguments().getString("password");
                        String name=getArguments().getString("name");
                        String username=getArguments().getString("username");
                        if(!newEmail.isEmpty()){
                            FirebaseAuth.getInstance().getCurrentUser().updateEmail(newEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    System.out.println("E-Mail Updated");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Email update failed");
                                }
                            });
                        }else{
                            newEmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        }

                        if(!newPassword.isEmpty()){
                            FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    System.out.println("Password updated");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Password failed to update");
                                }
                            });
                        }else{
                            newPassword=passwordText;
                        }

                        if(!name.isEmpty()){
                            databaseMethods.updateUserInfo(name, "NAME");
                        }

                        if(!username.isEmpty()){
                            databaseMethods.updateUserInfo(username, "USERNAME");
                        }
//
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                    }
                });
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
