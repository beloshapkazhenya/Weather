package com.example.weather.ui.mainscreen.hourlyadapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;

class HourlyViewHolder extends RecyclerView.ViewHolder {
    public TextView hourTextView;
    public TextView dateTextView;
    public ImageView weatherIcon;
    public TextView hourTemperature;

    public HourlyViewHolder(View itemView) {
        super(itemView);

        hourTextView = itemView.findViewById(R.id.vTvHourTime);
        dateTextView = itemView.findViewById(R.id.vTvHourDate);
        weatherIcon = itemView.findViewById(R.id.vIvHourIcon);
        hourTemperature = itemView.findViewById(R.id.vTvHourTemperature);
    }
}