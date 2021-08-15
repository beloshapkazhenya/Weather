package com.example.weather;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppController {

    public static void updateInformation(double latitude, double longitude,LinearLayout mainLayout, Context context){
        TextView currLocationView = mainLayout.findViewById(R.id.current_location);
        LinearLayout currentWeatherView = mainLayout.findViewById(R.id.current_weather);
        LinearLayout currentWeatherParametersView = mainLayout.findViewById(R.id.current_weather_parameters);
        LinearLayout hourInformationLayout = mainLayout.findViewById(R.id.hour_information_layout);
        LinearLayout dailyLinearLayout = mainLayout.findViewById(R.id.day_information_layout);

        GetWeatherAPI.getCurrentWeather(latitude,longitude,currentWeatherView,currentWeatherParametersView,currLocationView, context);
        GetWeatherAPI.getWeather(latitude,longitude,hourInformationLayout,dailyLinearLayout,context);
    }
}
