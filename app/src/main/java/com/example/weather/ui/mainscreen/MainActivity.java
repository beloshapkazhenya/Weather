package com.example.weather.ui.mainscreen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.models.DayWeather;
import com.example.weather.models.HourWeather;
import com.example.weather.models.local.currentweatherlocal.CurrentWeatherLocalModel;
import com.example.weather.models.local.onecalllocal.DayLocal;
import com.example.weather.models.local.onecalllocal.HourLocal;
import com.example.weather.models.local.onecalllocal.OnecallLocalModel;
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

public class MainActivity extends AppCompatActivity {

    private double latitude;
    private double longitude;

    Realm currentWeatherLocalDatabase;
    Realm oneCallWeatherDatabase;

    LocationManager locationManager;
    LocationListener locationListener;

    ProgressBar progressBar;

    LinearLayout mainLayout;
    GestureDetectorCompat swipeDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.vPbProgressBar);
        mainLayout = findViewById(R.id.vLnLtMainView);

        swipeDetector = new GestureDetectorCompat(this, new SwipeGestureListener());
        mainLayout.setOnTouchListener((v, event) -> swipeDetector.onTouchEvent(event));

        Realm.init(this);
        getRealmConfiguration();

        updateUiFromLocal();

        updateWeatherFromAPI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentWeatherLocalDatabase.close();
    }

    public void startLoadingDataFromApi() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    public void stopLoadingDataFromApi() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void updateWeatherFromAPI() {
        startLoadingDataFromApi();
        checkLocationPermission();
        startLocationManager();

        locationListener = location -> {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            getCurrentWeather();
            getWeather();
            stopLocationManager();
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    public void checkLocationPermission() {
        int REQUEST_LOCATION = 1;
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

    public void getCurrentWeatherRealmConfiguration() {
        String currentWeatherDatabaseName = "currentWeather";
        RealmConfiguration currentWeatherConfiguration = new RealmConfiguration
                .Builder()
                .name(currentWeatherDatabaseName)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        currentWeatherLocalDatabase = Realm.getInstance(currentWeatherConfiguration);
    }

    public void getOneCallWeatherRealmConfiguration() {
        String oneCallDatabaseName = "oneCallWeather";
        RealmConfiguration oneCallConfiguration = new RealmConfiguration
                .Builder()
                .name(oneCallDatabaseName)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        oneCallWeatherDatabase = Realm.getInstance(oneCallConfiguration);
    }

    public void getRealmConfiguration() {
        getCurrentWeatherRealmConfiguration();
        getOneCallWeatherRealmConfiguration();
    }

    //TODO первоначальная отрисовка из realm с параллельным запросом, loader на время запроса

    public void getWeather() {
        new WeatherRepository().getWeather(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetOneCallWeatherResponse, throwable -> Toast.makeText(this,
                        throwable.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show());
    }

    public void getCurrentWeather() {
        new WeatherRepository().getCurrent(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetCurrentWeatherResponse, throwable -> Toast.makeText(this,
                        throwable.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show());
    }

    public void updateUiFromLocal() {
        updateCurrentWeatherFromLocal();
        updateOneCallWeatherFromLocal();
    }

    public void onGetOneCallWeatherResponse(OnecallLocalModel onecallLocalModel) {
        saveOneCallWeatherData(onecallLocalModel);
        updateOneCallWeather(onecallLocalModel);
    }

    public void updateOneCallWeather(OnecallLocalModel onecallLocalModel) {
        updateHourlyRecyclerView(onecallLocalModel.getHourLocals());
        updateDailyRecyclerView(onecallLocalModel.getDayLocals());
    }

    public void updateCurrentWeatherFromLocal() {
        CurrentWeatherLocalModel currentWeatherLocalModel = getCurrentWeatherData();
        if (currentWeatherLocalModel != null) {
            updateCurrentWeather(currentWeatherLocalModel);
        }
    }

    public void updateOneCallWeatherFromLocal() {
        OnecallLocalModel onecallLocalModel = getOneCallWeatherData();
        if (onecallLocalModel != null) {
            updateOneCallWeather(onecallLocalModel);
        }
    }

    public void onGetCurrentWeatherResponse(CurrentWeatherLocalModel currentWeatherModel) {
        saveCurrentWeatherData(currentWeatherModel);
        updateCurrentWeather(currentWeatherModel);
    }

    public void updateCurrentWeather(CurrentWeatherLocalModel currentWeatherLocalModel) {

        if (currentWeatherLocalModel.getName() != null) {
            updateCurrentLocation(currentWeatherLocalModel.getName());
        }

        if (currentWeatherLocalModel.getWeather().size() > 0) {
            updateCurrentWeatherIcon(currentWeatherLocalModel.getWeather().get(0).getIcon());
            updateCurrentWeatherDescription(currentWeatherLocalModel.getWeather().get(0).getDescription());
        }

        if (currentWeatherLocalModel.getMain() != null) {
            updatePressure(currentWeatherLocalModel.getMain().getPressure());
            updateHumidity(currentWeatherLocalModel.getMain().getHumidity());
            updateCurrentTemperature(currentWeatherLocalModel.getMain().getTemp());
            updateCurrentWeatherFeelsLike(currentWeatherLocalModel.getMain().getFeelsLike());
        }

        if (currentWeatherLocalModel.getSunrise() != null) {
            updateSunrise(currentWeatherLocalModel.getSunrise());
        }

        if (currentWeatherLocalModel.getSunset() != null) {
            updateSunset(currentWeatherLocalModel.getSunset());
        }

        if (currentWeatherLocalModel.getWindSpeed() != null) {
            updateWindSpeed(currentWeatherLocalModel.getWindSpeed());
        }

    }

    private void startLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void stopLocationManager() {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
        stopLoadingDataFromApi();
    }

    public void saveCurrentWeatherData(CurrentWeatherLocalModel currentWeatherLocalModels) {
        currentWeatherLocalDatabase.executeTransaction(realm -> realm.insertOrUpdate(currentWeatherLocalModels));
    }

    public void saveOneCallWeatherData(OnecallLocalModel onecallLocalModel) {
        oneCallWeatherDatabase.executeTransaction(realm -> realm.insertOrUpdate(onecallLocalModel));
    }

    public CurrentWeatherLocalModel getCurrentWeatherData() {
        CurrentWeatherLocalModel currentWeatherLocal = currentWeatherLocalDatabase.where(CurrentWeatherLocalModel.class).findFirst();
        return currentWeatherLocal != null ? currentWeatherLocalDatabase.copyFromRealm(currentWeatherLocal) : null;
    }

    public OnecallLocalModel getOneCallWeatherData() {
        OnecallLocalModel onecallLocalModel = oneCallWeatherDatabase.where(OnecallLocalModel.class).findFirst();
        return onecallLocalModel != null ? oneCallWeatherDatabase.copyFromRealm(onecallLocalModel) : null;
    }

    public void updateHourlyRecyclerView(List<HourLocal> list) {
        RecyclerView rvHourWeather = findViewById(R.id.vRvHourlyInformation);
        ArrayList<HourWeather> hourWeathers = HelperMethods.createHourWeatherList(list);
        HourlyAdapter hourlyAdapter = new HourlyAdapter(hourWeathers);
        rvHourWeather.setAdapter(hourlyAdapter);
    }

    public void updateDailyRecyclerView(List<DayLocal> list) {
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

    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int SWIPE_MIN_DISTANCE = 100;
            int SWIPE_MIN_VELOCITY = 200;

            if (Math.abs(e1.getY() - e2.getY()) < SWIPE_MIN_DISTANCE) {
                return false;
            }
            if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_MIN_VELOCITY) {
                updateWeatherFromAPI();
            }
            return false;
        }
    }
}

