package com.example.finalyearproject.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.LoginActivity;
import com.example.finalyearproject.Models.UserModel;
import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDetailsFragment extends Fragment {
    
    FirebaseAuth firebaseAuth;

    EditText updateName;
    EditText updateUsername;
    EditText updateEmail;
    EditText updatePassword;
    EditText updateConfirmPass;
    Button updateButton;
    View view;
    DatabaseMethods databaseMethods;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDetailsFragment newInstance(String param1, String param2) {
        MyDetailsFragment fragment = new MyDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_my_details, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Details");
        updateName = view.findViewById(R.id.updateName);
        updateUsername=view.findViewById(R.id.updateUsername);
        updateEmail=view.findViewById(R.id.updateEmail);
        updatePassword=view.findViewById(R.id.updatePassword);
        updateConfirmPass=view.findViewById(R.id.updateConfirmPassword);
        updateButton=view.findViewById(R.id.buttonupdate);
        databaseMethods = new DatabaseMethods(getContext());
        firebaseAuth=FirebaseAuth.getInstance();

        validateAndUpdateUser();

        return view;
    }

    public void validateAndUpdateUser(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=updateName.getText().toString();
                String username=updateUsername.getText().toString();
                String email=updateEmail.getText().toString();
                String password=updatePassword.getText().toString();
                String confirmPassword=updateConfirmPass.getText().toString();

                if(name.isEmpty() && username.isEmpty() && email.isEmpty() && password.isEmpty()){
                    Toast.makeText(getContext(), "You must enter at least one detail to update", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!confirmPassword.isEmpty() && !password.equals(confirmPassword)){
                    updateConfirmPass.setError("Confirmation password does not match password");
                    return;
                }
                //TODO Implement validation at some point - Above all statements that update if not empty

                if(!email.isEmpty() || !password.isEmpty()){
                    ReauthenticateDialogFragment reauthenticateDialogFragment = new ReauthenticateDialogFragment();
                    Bundle b = new Bundle();
                    b.putString("email", email);
                    b.putString("password", password);
                    b.putString("name", name);
                    b.putString("username", username);
                    reauthenticateDialogFragment.setArguments(b);
                    reauthenticateDialogFragment.show(getFragmentManager(), "myDetailsFragment");
                }
            }
        });
    }


}