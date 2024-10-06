package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity {

    private final String TAG = "SecondActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        Button loginButton = findViewById(R.id.loginButton);
        Button createAccount = findViewById(R.id.createButton);
        EditText username = findViewById(R.id.editTextUsername);
        EditText password = findViewById(R.id.editTextPassword);

        loginButton.setOnClickListener(v -> loginUser(username.getText().toString(), password.getText().toString()));

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });

        Button button_quit_login = findViewById(R.id.button_quit_login);
        button_quit_login.setOnClickListener(new View.OnClickListener() {
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
    private void loginUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // First, check if the username exists in the Firebase Realtime Database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Username exists, proceed with Firebase Authentication
                    mAuth.signInWithEmailAndPassword(username + "@wandersync.com", password)
                            .addOnCompleteListener(SecondActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign-in success
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(SecondActivity.this, "Login successful! Welcome, " + user.getEmail(), Toast.LENGTH_SHORT).show();

                                    // Navigate to Logistics Activity
                                    Intent intent = new Intent(SecondActivity.this, NavigationActivity.class);
                                    intent.putExtra("KEY", "Logistics Page");
                                    startActivity(intent);
                                    finish(); // Close the login activity
                                } else {
                                    // Sign-in failure
                                    Toast.makeText(SecondActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // Username does not exist
                    Toast.makeText(SecondActivity.this, "Username not found in the database.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors
                Log.w("LoginActivity", "Database error: ", databaseError.toException());
                Toast.makeText(SecondActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
