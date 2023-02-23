package com.example.fragmenttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    NavController userNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTopBarColor("#FFCE00");

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);

//        BottomNavigationView userSectionNavView = findViewById(R.id.user_section_navbar);
//        NavigationUI.setupWithNavController(userSectionNavView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            navController.popBackStack();
        } else if(item.getItemId() == R.id.main_menu_add) {
            navController.navigate(R.id.action_global_feedFragment);
        } else if(item.getItemId() == R.id.main_menu_user) {
            navController.navigate(R.id.action_global_userSectionFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTopBarColor(String color) {
        ActionBar actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor(color));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

    }
}