package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class SecondActivity extends AppCompatActivity {

    private final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        Button loginButton = findViewById(R.id.loginButton);
        Button createAccount = findViewById(R.id.createButton);

        loginButton.setOnClickListener(v -> loginUser());
        createAccount.setOnClickListener(v -> createAccount());
    }

    private void loginUser() {
    }

    private void createAccount() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart called");
    }
}
