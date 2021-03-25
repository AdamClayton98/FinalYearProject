package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {


    public static String uid;

    EditText allergyInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        uid= FirebaseAuth.getInstance().getUid();

        allergyInput = findViewById(R.id.inputAllergy);

        Button addAllergyButton = findViewById(R.id.buttonAdd);

        addAllergyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAllergy(v);
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.ic_home:
                            selectedFragment=new HomeFragment();
                            break;
                        case R.id.ic_plan:
                            selectedFragment=new PlanningFragment();
                            break;
                        case R.id.ic_fave:
                            selectedFragment=new FavouritesFragment();
                            break;
                        case R.id.ic_pantry:
                            selectedFragment=new PantryFragment();
                            break;
                        case R.id.ic_profile:
                            selectedFragment=new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,selectedFragment);

                    return true;
                }
            };


    public void addAllergy(View view){
        String allergy = allergyInput.getText().toString();

        DatabaseMethods databaseMethods=new DatabaseMethods(HomeActivity.this);
        databaseMethods.addAllergyToDb(allergy);
    }

    public void toAllergies(View view){



    }
}