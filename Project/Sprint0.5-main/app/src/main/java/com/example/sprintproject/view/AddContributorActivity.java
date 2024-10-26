package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class AddContributorActivity extends AppCompatActivity {
    private final String tag = "AddContributor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contributor);

        Intent intent = getIntent();
    }
}
