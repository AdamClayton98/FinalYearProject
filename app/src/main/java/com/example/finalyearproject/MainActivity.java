package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalyearproject.Models.AllergyModel;
import com.example.finalyearproject.fragments.AllergiesFragment;
import com.example.finalyearproject.fragments.FavouritesFragment;
import com.example.finalyearproject.fragments.HomeFragment;
import com.example.finalyearproject.fragments.MyRecipesFragment;
import com.example.finalyearproject.fragments.PantryFragment;
import com.example.finalyearproject.fragments.PlanningFragment;
import com.example.finalyearproject.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView signoutText;

    EditText allergyInput;

    Button addAllergyButton;

    public static String uid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.ic_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.ic_plan:
                            selectedFragment = new PlanningFragment();
                            break;
                        case R.id.ic_fave:
                            selectedFragment = new FavouritesFragment();
                            break;
                        case R.id.ic_pantry:
                            selectedFragment = new PantryFragment();
                            break;
                        case R.id.ic_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper, selectedFragment).commit();

                    return true;
                }
            };

    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }


    public void addAllergy(View view){
        allergyInput=findViewById(R.id.allergyInput);

        addAllergyButton=findViewById(R.id.addButton);
        String allergy = allergyInput.getText().toString();

        DatabaseMethods databaseMethods=new DatabaseMethods(MainActivity.this);
        databaseMethods.addAllergyToDb(allergy);

    }

    public void toAllergies(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper, new AllergiesFragment()).commit();

    }

    public void toMyRecipes(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,new MyRecipesFragment()).commit();
    }

}
