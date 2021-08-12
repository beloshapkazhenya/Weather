package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout hourInformationLayout = findViewById(R.id.hour_information_layout);
        int currentIcon = R.drawable.test_icon;

        for (int x = 0; x < 25; x++) {
            hourInformationLayout.addView(ViewCreator.createHourInformationLayout(x, x, currentIcon, hourInformationLayout, this));
        }

        final LinearLayout dayInformationLayout = findViewById(R.id.day_information_layout);
        for (int x = 0; x < 3; x++) {
            dayInformationLayout.addView(ViewCreator.createDayInformationLayout(x + 9, x + 25, x + 15, currentIcon, dayInformationLayout, this));
        }

    }


}