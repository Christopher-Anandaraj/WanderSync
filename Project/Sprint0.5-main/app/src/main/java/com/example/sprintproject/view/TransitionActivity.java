package com.example.sprintproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.widget.VideoView;

import com.example.sprintproject.R;


public class TransitionActivity extends AppCompatActivity {
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition_layout);

        videoView = findViewById(R.id.videoViewTransition);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.takeoff);
        videoView.setVideoURI(videoUri);

        videoView.setOnCompletionListener(mp -> {
            startActivity(new Intent(TransitionActivity.this, NavigationActivity.class));
            finish(); // Close Transition Activity
        });

        videoView.start(); // Start playing the video
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause(); // Pause video if the activity is paused
        }
    }
}

