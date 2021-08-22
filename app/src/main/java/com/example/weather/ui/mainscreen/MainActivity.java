package com.example.weather.ui.mainscreen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.models.DayWeather;
import com.example.weather.models.HourWeather;
import com.example.weather.models.currentweather.CurrentWeatherModel;
import com.example.weather.models.local.currentweatherlocal.CurrentWeatherLocalModel;
import com.example.weather.models.local.currentweatherlocal.MainLocal;
import com.example.weather.models.local.currentweatherlocal.WeatherLocal;
import com.example.weather.models.onecall.Daily;
import com.example.weather.models.onecall.Hourly;
import com.example.weather.models.onecall.Weather;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.ui.mainscreen.dailyadapter.DailyAdapter;
import com.example.weather.ui.mainscreen.hourlyadapter.HourlyAdapter;
import com.example.weather.utils.HelperMethods;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;


public class MainActivity extends AppCompatActivity {

    private double latitude;
    private double longitude;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name("currentWeatherData").build();
        Realm.setDefaultConfiguration(config);


        int REQUEST_LOCATION = 1;

        startLocationManager();

        locationListener = location -> {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            getCurrentWeather();
            getWeather();
            stopLocationManager();
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    //TODO первоначальная отрисовка из realm с параллельным запросом, loader на время запроса

    public void getWeather() {
        new WeatherRepository().getWeather(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(oneCallModel -> {
                    updateHourlyRecyclerView(oneCallModel.getHourly());
                    updateDailyRecyclerView(oneCallModel.getDaily());

                }, throwable -> {

                });
    }

    //TODO переписать под realm

    public void getCurrentWeather() {
        new WeatherRepository().getCurrent(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currentWeatherModel -> {
                    updateCurrentWeather(currentWeatherModel);
                    saveCurrentWeatherData(currentWeatherModel);
                }, throwable -> {

                });
    }

    public void updateCurrentWeather(CurrentWeatherLocalModel currentWeatherLocalModel) {
        updateCurrentLocation(currentWeatherLocalModel.getName());
        updateCurrentWeatherIcon(currentWeatherLocalModel.getWeather().get(0).getIcon());
        updateSunrise(currentWeatherLocalModel.getSunrise());
        updateCurrentTemperature(currentWeatherLocalModel.getMain().getTemp());
        updateSunset(currentWeatherLocalModel.getSunset());
        updateCurrentWeatherDescription(currentWeatherLocalModel.getWeather().get(0).getDescription());
        updateCurrentWeatherFeelsLike(currentWeatherLocalModel.getMain().getFeelsLike());
        updateWindSpeed(currentWeatherLocalModel.getWindSpeed());
        updatePressure(currentWeatherLocalModel.getMain().getPressure());
        updateHumidity(currentWeatherLocalModel.getMain().getHumidity());
    }

    private void startLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void stopLocationManager() {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
    }

    public void saveCurrentWeatherData(CurrentWeatherLocalModel currentWeatherLocalModels) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realmTransaction -> {
            CurrentWeatherLocalModel currentWeatherData = realmTransaction.createObject(CurrentWeatherLocalModel.class);
            MainLocal mainLocal = realmTransaction.createObject(MainLocal.class);
            currentWeatherData.setDt(currentWeatherLocalModels.getDt());
            currentWeatherData.setName(currentWeatherLocalModels.getName());
            currentWeatherData.setSunrise(currentWeatherLocalModels.getSunrise());
            currentWeatherData.setSunset(currentWeatherLocalModels.getSunset());
            currentWeatherData.setWindSpeed(currentWeatherLocalModels.getWindSpeed());
            mainLocal.setHumidity(currentWeatherLocalModels.getMain().getHumidity());
            mainLocal.setPressure(currentWeatherLocalModels.getMain().getPressure());
            mainLocal.setTemp(currentWeatherLocalModels.getMain().getTemp());
            mainLocal.setFeelsLike(currentWeatherLocalModels.getMain().getFeelsLike());
            currentWeatherData.setMain(mainLocal);
            RealmList<WeatherLocal> weatherLocals = new RealmList<WeatherLocal>();
            WeatherLocal weatherLocal = realmTransaction.createObject(WeatherLocal.class);
            weatherLocal.setDescription(currentWeatherLocalModels.getWeather().get(0).getDescription());
            weatherLocal.setIcon(currentWeatherLocalModels.getWeather().get(0).getIcon());
            weatherLocals.add(0, weatherLocal);


        });

    }

    public void updateHourlyRecyclerView(List<Hourly> list) {
        RecyclerView rvHourWeather = findViewById(R.id.vRvHourlyInformation);
        ArrayList<HourWeather> hourWeathers = HelperMethods.createHourWeatherList(list);
        HourlyAdapter hourlyAdapter = new HourlyAdapter(hourWeathers);
        rvHourWeather.setAdapter(hourlyAdapter);
    }

    public void updateDailyRecyclerView(List<Daily> list) {
        RecyclerView rvDailyWeather = findViewById(R.id.vRvDailyInformation);
        ArrayList<DayWeather> dayWeathers = HelperMethods.createDayWeatherList(list);
        DailyAdapter dailyAdapter = new DailyAdapter(dayWeathers);
        rvDailyWeather.setAdapter(dailyAdapter);

    }

    public void updateWindSpeed(double windSpeed) {
        TextView windTextView = findViewById(R.id.vTvWindSpeed);
        windTextView.setText(HelperMethods.windSpeedToString(windSpeed));
    }

    public void updatePressure(int pressure) {
        TextView pressureTextView = findViewById(R.id.vTvPressure);
        pressureTextView.setText(HelperMethods.pressureToString(pressure));
    }

    public void updateHumidity(int humidity) {
        TextView humidityTextView = findViewById(R.id.vTvHumidity);
        String humidityString = humidity + "%";
        humidityTextView.setText(humidityString);
    }

    public void updateCurrentLocation(String location) {
        TextView locationTextView = findViewById(R.id.vTvCurrentLocationTitle);
        locationTextView.setText(location);
    }

    public void updateSunrise(long sunrise) {
        TextView sunriseTextView = findViewById(R.id.vTvSunriseTime);
        sunriseTextView.setText(HelperMethods.getTime(sunrise));
    }

    public void updateSunset(long sunset) {
        TextView sunriseTextView = findViewById(R.id.vTvSunsetTime);
        sunriseTextView.setText(HelperMethods.getTime(sunset));
    }

    public void updateCurrentWeatherIcon(String iconId) {
        ImageView currentWeatherIconImageView = findViewById(R.id.vIvCurrentWeatherIcon);
        String path = "https://openweathermap.org/img/wn/" + iconId + "@2x.png";
        Picasso.get().load(path).error(R.drawable.unknown).into(currentWeatherIconImageView);
    }

    public void updateCurrentWeatherDescription(String description) {
        TextView descriptionTextView = findViewById(R.id.vTvCurrentWeatherDescription);
        descriptionTextView.setText(description);
    }

    public void updateCurrentWeatherFeelsLike(double feelsLike) {
        TextView feelsLikeTextView = findViewById(R.id.vTvCurrentWeatherFeelsLike);
        String feelsLikeString = "Ощущается как: " + HelperMethods.temperatureToString(feelsLike);
        feelsLikeTextView.setText(feelsLikeString);
    }

    public void updateCurrentTemperature(double temperature) {
        TextView currentTemperature = findViewById(R.id.vTvCurrentTemperature);
        currentTemperature.setText(HelperMethods.temperatureToString(temperature));
    }
}

