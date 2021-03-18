package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.finalyearproject.fragments.FavouritesFragment;
import com.example.finalyearproject.fragments.HomeFragment;
import com.example.finalyearproject.fragments.PantrtyFragment;
import com.example.finalyearproject.fragments.PlanningFragment;
import com.example.finalyearproject.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                            selectedFragment = new PantrtyFragment();
                            break;
                        case R.id.ic_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper, selectedFragment).commit();

                    return true;
                }
            };
}