package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

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

        for (int x = 0; x < 24; x++) {
            hourInformationLayout.addView(createHourInformationLayout(x, x, currentIcon));
        }

        final LinearLayout dayInformationLayout = findViewById(R.id.day_information_layout);
        for (int x = 0; x < 3; x++) {
            dayInformationLayout.addView(createDayInformationLayout(x + 9, x + 25, x + 15, currentIcon));
        }

    }


    public View createHourInformationLayout(int hour, int temperature, int icon) {
        final View hourWeatherView = getLayoutInflater().inflate(R.layout.weather_information_hour, null);
        TextView timeView = hourWeatherView.findViewById(R.id.hour_information_time);
        ImageView iconView = hourWeatherView.findViewById(R.id.hour_information_icon);
        TextView temperatureView = hourWeatherView.findViewById(R.id.hour_information_temperature);

        timeView.setText(hour + ":00");
        temperatureView.setText(temperature + "ºC");
        iconView.setImageResource(icon);
        return hourWeatherView;
    }

    public View createDayInformationLayout(int date, int temperatureDay, int temperatureNight, int icon) {
        final View dayWeatherView = getLayoutInflater().inflate(R.layout.weather_information_day, null);
        TextView dateView = dayWeatherView.findViewById(R.id.day_information_date);
        ImageView iconView = dayWeatherView.findViewById(R.id.day_information_icon);
        TextView temperatureDayView = dayWeatherView.findViewById(R.id.day_information_temperature_day);
        TextView temperatureNightView = dayWeatherView.findViewById(R.id.day_information_temperature_night);

        String currDate = date + " августа";

        dateView.setText(currDate);
        iconView.setImageResource(icon);
        temperatureDayView.setText(temperatureDay + "ºC");
        temperatureNightView.setText(temperatureNight + "ºC");

        return dayWeatherView;
    }
}