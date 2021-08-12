package com.example.weather;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewCreator {
    public static View createHourInformationLayout(int hour, int temperature, int icon, LinearLayout linearLayout, Context context) {

        final View hourWeatherView = ((Activity) context).getLayoutInflater().inflate(R.layout.weather_information_hour, linearLayout, false);
        TextView timeView = hourWeatherView.findViewById(R.id.hour_information_time);
        ImageView iconView = hourWeatherView.findViewById(R.id.hour_information_icon);
        TextView temperatureView = hourWeatherView.findViewById(R.id.hour_information_temperature);

        String elementHour;
        if (hour < 10) {
            elementHour = "0" + hour + ":00";
        } else if (hour == 24) {
            elementHour = "00:00";
        } else {
            elementHour = hour + ":00";
        }

        timeView.setText(elementHour);
        temperatureView.setText(temperatureToString(temperature));
        iconView.setImageResource(icon);
        return hourWeatherView;
    }

    public static View createDayInformationLayout(int date, int temperatureDay, int temperatureNight, int icon, LinearLayout linearLayout, Context context) {
        final View dayWeatherView = ((Activity) context).getLayoutInflater().inflate(R.layout.weather_information_day, linearLayout, false);
        TextView dateView = dayWeatherView.findViewById(R.id.day_information_date);
        ImageView iconView = dayWeatherView.findViewById(R.id.day_information_icon);
        TextView temperatureDayView = dayWeatherView.findViewById(R.id.day_information_temperature_day);
        TextView temperatureNightView = dayWeatherView.findViewById(R.id.day_information_temperature_night);

        String currDate = date + " августа";

        dateView.setText(currDate);
        iconView.setImageResource(icon);
        temperatureDayView.setText(temperatureToString(temperatureDay));
        temperatureNightView.setText(temperatureToString(temperatureNight));

        return dayWeatherView;
    }

    private static String temperatureToString(int temperature) {
        String temperatureString;
        if (temperature < 0) {
            temperatureString = "-" + temperature + "ºC";
        } else if (temperature == 0) {
            temperatureString = temperature + "ºC";
        } else {
            temperatureString = "+" + temperature + "ºC";
        }
        return temperatureString;
    }
}
