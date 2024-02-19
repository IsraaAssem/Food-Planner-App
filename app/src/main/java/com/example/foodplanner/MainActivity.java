package com.example.foodplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.homeBottmNav){
                    navController.navigate(R.id.homeFragment);
                    return true;
                } else if (item.getItemId()==R.id.favouritesBottomNav) {
                    navController.navigate(R.id.favFragment);
                    return true;
                } else if (item.getItemId()==R.id.weekPlanBottomNav) {
                    navController.navigate(R.id.weekPlanFragment);
                    return true;
                } else if (item.getItemId()==R.id.searchBottomNav) {
                    navController.navigate(R.id.searchFragment);
                    return true;
                }else
                    return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
       // getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);

        navController= Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
       // NavigationUI.setupWithNavController(bottomNavigationView,navController);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.homeBottmNav){
                    navController.navigate(R.id.homeFragment);
                    return true;
                } else if (item.getItemId()==R.id.favouritesBottomNav) {
                    navController.navigate(R.id.favFragment);
                    return true;
                } else if (item.getItemId()==R.id.weekPlanBottomNav) {
                    navController.navigate(R.id.weekPlanFragment);
                    return true;
                } else if (item.getItemId()==R.id.searchBottomNav) {
                    navController.navigate(R.id.searchFragment);
                    return true;
                }else
                    return true;
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp()||super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,SplashActivity.class));
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
   }

}