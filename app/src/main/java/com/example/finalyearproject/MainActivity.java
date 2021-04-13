package com.example.finalyearproject;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalyearproject.Models.AllergyModel;
import com.example.finalyearproject.fragments.AddToPantryFragment;
import com.example.finalyearproject.fragments.AllergiesFragment;
import com.example.finalyearproject.fragments.FavouritesFragment;
import com.example.finalyearproject.fragments.HomeFragment;
import com.example.finalyearproject.fragments.MyDetailsFragment;
import com.example.finalyearproject.fragments.MyRatingFragment;
import com.example.finalyearproject.fragments.MyRecipesFragment;
import com.example.finalyearproject.fragments.PantryFragment;
import com.example.finalyearproject.fragments.PlanningDateSelectionFragment;
import com.example.finalyearproject.fragments.PlanningFragment;
import com.example.finalyearproject.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText allergyInput;
    Fragment selectedFragment = null;
    Toolbar toolbar;

    Button addAllergyButton;

    public static String uid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,new HomeFragment()).addToBackStack(null).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.ic_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.ic_plan:
                            selectedFragment = new PlanningDateSelectionFragment();
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

                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper, selectedFragment).addToBackStack(null).commit();

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

        AllergiesFragment currentFragment = (AllergiesFragment) getSupportFragmentManager().getFragments().get(0);
        currentFragment.refreshFragmentListView();

    }

    public void toAllergies(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper, new AllergiesFragment()).addToBackStack(null).commit();

    }

    public void toMyDetails(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper, new MyDetailsFragment()).addToBackStack(null).commit();
    }

    public void toMyRecipes(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,new MyRecipesFragment()).addToBackStack(null).commit();
    }

    public void toAddToPantry(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper, new AddToPantryFragment()).addToBackStack(null).commit();
    }

    public void toMyRating(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper, new MyRatingFragment()).addToBackStack(null).commit();
    }


}
