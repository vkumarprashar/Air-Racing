package com.marathon.airracing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    MotionLayout motionLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        motionLayout = findViewById(R.id.motion_layout);

        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });
    }
}