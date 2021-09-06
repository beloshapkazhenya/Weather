package com.example.weather.ui.mainscreen.dailyadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.models.DayWeather;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DailyAdapter extends
        RecyclerView.Adapter<DailyViewHolder> {

    private final List<DayWeather> mDayWeather;
    private static final String ICON_PATH = "https://openweathermap.org/img/wn/";
    private static final String ICON_EXTENSION = "@2x.png";

    public DailyAdapter(List<DayWeather> dayWeathers) {
        mDayWeather = dayWeathers;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View dayWeatherView = inflater.inflate(R.layout.weather_information_day, parent, false);

        return new DailyViewHolder(dayWeatherView);
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {
        DayWeather dayWeather = mDayWeather.get(position);
        String path = ICON_PATH + dayWeather.getCurrIconId() + ICON_EXTENSION;

        Picasso.get()
                .load(path)
                .error(R.drawable.unknown)
                .into(holder.dayWeatherIcon);

        holder.dateTextView.setText(dayWeather.getCurrDate());
        holder.dayTemperatureTextView.setText(dayWeather.getCurrDayTemperature());
        holder.nightTemperatureTextView.setText(dayWeather.getCurrNightTemperature());
    }

    @Override
    public int getItemCount() {
        return mDayWeather.size();
    }
}
