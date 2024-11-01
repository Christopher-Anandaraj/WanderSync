package com.example.sprintproject.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    private final String tag = "CreateAccount";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_create);

        Button createAccountButton = findViewById(R.id.createButton);
        EditText usernameField = findViewById(R.id.editTextUsername);
        EditText passwordField = findViewById(R.id.editTextPassword);
        Button loginBackButton = findViewById(R.id.loginBackButton);

        VideoView videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnCompletionListener(mp -> videoView.start());

        loginBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // Create Account Button Action
        createAccountButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            createAccount(username, password);
        });

        Button buttonQuitRegister = findViewById(R.id.button_quit_register);
        buttonQuitRegister.setOnClickListener(new View.OnClickListener() {
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

    private void createAccount(String username, String password) {
        if (username.isEmpty() || password.isEmpty() || username.contains(" ")
                || password.contains(" ")) {
            Toast.makeText(this, "Invalid input. Please ensure "
                    + "fields are not empty and contain no spaces.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create user in Firebase Authentication
        mAuth.createUserWithEmailAndPassword(username + "@wandersync.com", password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        saveUserToDatabase(user.getUid(), username);
                        Toast.makeText(this, "Account created successfully!",
                                Toast.LENGTH_SHORT).show();
                        // Optionally navigate to login activity
                        startActivity(new Intent(CreateAccount.this, SecondActivity.class));
                    } else {
                        Toast.makeText(this, "Account creation failed: "
                                + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Save username in Firebase Realtime Database
    private void saveUserToDatabase(String uid, String username) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Map<String, String> userMap = new HashMap<>();
        userMap.put("username", username);
        databaseReference.child(uid).setValue(userMap);
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
