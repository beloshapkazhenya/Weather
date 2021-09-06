package com.example.weather.ui.mainscreen.hourlyadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.models.HourWeather;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HourlyAdapter extends
        RecyclerView.Adapter<HourlyViewHolder> {

    private final List<HourWeather> mHourWeather;
    private static final String ICON_PATH = "https://openweathermap.org/img/wn/";
    private static final String ICON_EXTENSION = "@2x.png";

    public HourlyAdapter(List<HourWeather> hourWeathers) {
        mHourWeather = hourWeathers;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View hourWeatherView = inflater.inflate(R.layout.weather_information_hour, parent, false);

        return new HourlyViewHolder(hourWeatherView);
    }

    @Override
    public void onBindViewHolder(HourlyViewHolder holder, int position) {
        HourWeather hourWeather = mHourWeather.get(position);

        holder.hourTextView.setText(hourWeather.getCurrHour());
        holder.dateTextView.setText(hourWeather.getCurrDate());

        String path = ICON_PATH + hourWeather.getHourIconId() + ICON_EXTENSION;

        Picasso.get()
                .load(path)
                .error(R.drawable.unknown)
                .into(holder.weatherIcon);

        holder.hourTemperature.setText(hourWeather.getHourTemperature());
    }

    @Override
    public int getItemCount() {
        return mHourWeather.size();
    }
}
