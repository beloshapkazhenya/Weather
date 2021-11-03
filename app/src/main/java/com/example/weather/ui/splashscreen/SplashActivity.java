package com.example.weather.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.R;
import com.example.weather.ui.mainscreen.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            Thread.sleep(DELAY);
            startMainActivity();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finish();
    }

    private void startMainActivity() {
        startActivity(
                new Intent(
                        this,
                        MainActivity.class
                )
        );
    }
}