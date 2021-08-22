package com.example.weather.ui.mainscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.weather.models.DayWeather;
import com.example.weather.ui.mainscreen.dailyadapter.DailyAdapter;
import com.example.weather.utils.HelperMethods;
import com.example.weather.ui.mainscreen.hourlyadapter.HourlyAdapter;
import com.example.weather.R;
import com.example.weather.models.HourWeather;
import com.example.weather.models.onecall.Daily;
import com.example.weather.models.onecall.Hourly;
import com.example.weather.repository.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private double latitude;
    private double longitude;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int REQUEST_LOCATION = 1;

        startLocationManager();

        locationListener = location -> {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //TODO Действие при получении координат, заменить на разовое определение
            stopLocationManager();
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        new WeatherRepository().getWeather(latitude,longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(oneCallModel -> {
                    updateHourlyRecyclerView(oneCallModel.getHourly());
                    updateDailyRecyclerView(oneCallModel.getDaily());
                }, throwable -> {

                });
    }

    private void startLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }


    private void stopLocationManager() {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
    }

    public void updateHourlyRecyclerView(List<Hourly> list) {
        RecyclerView rvHourWeather = findViewById(R.id.vRvHourlyInformation);
        ArrayList<HourWeather> hourWeathers = HelperMethods.createHourWeatherList(list);
        HourlyAdapter hourlyAdapter = new HourlyAdapter(hourWeathers);
        rvHourWeather.setAdapter(hourlyAdapter);
    }

    public void updateDailyRecyclerView(List<Daily> list){
        RecyclerView rvDailyWeather = findViewById(R.id.vRvDailyInformation);
        ArrayList<DayWeather> dayWeathers = HelperMethods.createDayWeatherList(list);
        DailyAdapter dailyAdapter = new DailyAdapter(dayWeathers);
        rvDailyWeather.setAdapter(dailyAdapter);

    }

}