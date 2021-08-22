package com.example.weather.ui.mainscreen.dailyadapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;

class DailyViewHolder extends RecyclerView.ViewHolder {
    public TextView dateTextView;
    public ImageView dayWeatherIcon;
    public TextView dayTemperatureTextView;
    public TextView nightTemperatureTextView;

    public DailyViewHolder(View itemView) {
        super(itemView);

        dateTextView = itemView.findViewById(R.id.vTvDayDate);
        dayWeatherIcon = itemView.findViewById(R.id.vIvDayIcon);
        dayTemperatureTextView = itemView.findViewById(R.id.vTvDayTemperatureDay);
        nightTemperatureTextView = itemView.findViewById(R.id.vTvDayTemperatureNight);
    }
}