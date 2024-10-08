package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityNavigationBinding;
import com.example.sprintproject.model.CustomBottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class NavigationActivity extends AppCompatActivity {

    private ActivityNavigationBinding binding;
    private final String tag = "LogisticsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CustomBottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_logistics);
        NavigationUI.setupActionBarWithNavController(this,
                navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        Intent intent = getIntent();
        String message = intent.getStringExtra("KEY");
        TextView textView = findViewById(R.id.logistics_welcome);
        textView.setText(message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag, "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, "onDestroy called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(tag, "onRestart called");
    }
}
