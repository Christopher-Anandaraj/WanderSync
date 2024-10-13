package com.example.sprintproject.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityMainBinding;
import com.example.sprintproject.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private final String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(tag, "onCreate called");
        //create viewmodel
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //bind viewmodel to layout
        binding.setVariable(BR.viewModel, viewModel);
        binding.setLifecycleOwner(this);
        Log.d(tag, "onCreate called");

        Button start = findViewById(R.id.button_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        //Creates a onClickListener for buttonQuit.
        Button buttonQuit = findViewById(R.id.button_quit);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();
            }
        });
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