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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.models.DayWeather;
import com.example.weather.models.HourWeather;
import com.example.weather.models.local.currentweatherlocal.CurrentWeatherLocal;
import com.example.weather.models.local.onecalllocal.DayLocal;
import com.example.weather.models.local.onecalllocal.HourLocal;
import com.example.weather.models.local.onecalllocal.OnecallLocal;
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

    private static final int REQUEST_LOCATION = 1;
    private static final String CURRENT_WEATHER_DATABASE_NAME = "oneCallWeather";
    private static final String ONE_CALL_DATABASE_NAME = "oneCallWeather";
    private static final String ICON_PATH = "https://openweathermap.org/img/wn/";
    private static final String ICON_EXTENSION = "@2x.png";

    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MIN_VELOCITY = 200;

    private double latitude;
    private double longitude;

    private WeatherRepository weatherRepository;

    Realm currentWeatherLocalDatabase;
    Realm oneCallWeatherDatabase;

    LocationManager locationManager;
    LocationListener locationListener;

    TextView windTextView;
    TextView sunsetTextView;
    TextView sunriseTextView;
    TextView feelsLikeTextView;
    TextView currentTemperature;
    TextView humidityTextView;
    TextView pressureTextView;
    TextView descriptionTextView;
    ImageView currentWeatherIconImageView;
    TextView locationTextView;
    RecyclerView rvDailyWeather;
    RecyclerView rvHourWeather;

    FrameLayout progressBar;
    LinearLayout mainLayout;
    GestureDetectorCompat swipeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllView();
        setSwipeDetector();
        weatherRepository = new WeatherRepository();
        getRealmConfiguration();
        updateUiFromLocal();
        updateUIFromAPI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentWeatherLocalDatabase.close();
        oneCallWeatherDatabase.close();
    }

    private void findAllView() {
        progressBar = findViewById(R.id.vFrLtProgressBar);
        mainLayout = findViewById(R.id.vLnLtMainView);
        windTextView = findViewById(R.id.vTvWindSpeed);
        sunsetTextView = findViewById(R.id.vTvSunsetTime);
        sunriseTextView = findViewById(R.id.vTvSunriseTime);
        feelsLikeTextView = findViewById(R.id.vTvCurrentWeatherFeelsLike);
        currentTemperature = findViewById(R.id.vTvCurrentTemperature);
        humidityTextView = findViewById(R.id.vTvHumidity);
        pressureTextView = findViewById(R.id.vTvPressure);
        descriptionTextView = findViewById(R.id.vTvCurrentWeatherDescription);
        currentWeatherIconImageView = findViewById(R.id.vIvCurrentWeatherIcon);
        locationTextView = findViewById(R.id.vTvCurrentLocationTitle);
        rvDailyWeather = findViewById(R.id.vRvDailyInformation);
        rvHourWeather = findViewById(R.id.vRvHourlyInformation);
    }

    //Realm configuration
    private void getRealmConfiguration() {
        Realm.init(this);

        getCurrentWeatherRealmConfiguration();
        getOneCallWeatherRealmConfiguration();
    }

    private void getCurrentWeatherRealmConfiguration() {
        RealmConfiguration currentWeatherConfiguration = new RealmConfiguration
                .Builder()
                .name(CURRENT_WEATHER_DATABASE_NAME)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        currentWeatherLocalDatabase = Realm.getInstance(currentWeatherConfiguration);
    }

    private void getOneCallWeatherRealmConfiguration() {
        RealmConfiguration oneCallConfiguration = new RealmConfiguration
                .Builder()
                .name(ONE_CALL_DATABASE_NAME)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        oneCallWeatherDatabase = Realm.getInstance(oneCallConfiguration);
    }

    //updating UI from local database
    private void updateUiFromLocal() {
        updateCurrentWeatherFromLocal();
        updateOneCallWeatherFromLocal();
    }

    private void updateCurrentWeatherFromLocal() {
        CurrentWeatherLocal currentWeatherLocal = getCurrentWeatherData();

        if (currentWeatherLocal != null) {
            updateCurrentWeather(currentWeatherLocal);
        }
    }

    private CurrentWeatherLocal getCurrentWeatherData() {
        CurrentWeatherLocal currentWeatherLocal = currentWeatherLocalDatabase
                .where(CurrentWeatherLocal.class)
                .findFirst();

        return currentWeatherLocal != null ? currentWeatherLocalDatabase.copyFromRealm(currentWeatherLocal) : null;
    }

    private void updateOneCallWeatherFromLocal() {
        OnecallLocal onecallLocal = getOneCallWeatherData();

        if (onecallLocal != null) {
            updateOneCallWeather(onecallLocal);
        }
    }

    private OnecallLocal getOneCallWeatherData() {
        OnecallLocal onecallLocal = oneCallWeatherDatabase
                .where(OnecallLocal.class)
                .findFirst();

        return onecallLocal != null ? oneCallWeatherDatabase.copyFromRealm(onecallLocal) : null;
    }

    //updating UI from API
    private void updateUIFromAPI() {
        showLoader();
        checkLocationPermission();
        startLocationManager();

        locationListener = location -> {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            getCurrentWeather();
            getOneCallWeather();
            stopLocationManager();
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    //location manager
    private void checkLocationPermission() {
        if (checkSelfPermissionFineLocation() && checkSelfPermissionCoarseLocation()) {
            requestPermissions();
        }
    }

    private boolean checkSelfPermissionFineLocation() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkSelfPermissionCoarseLocation() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_LOCATION);
    }

    private void startLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void stopLocationManager() {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
        hideLoader();
    }

    //loader
    private void showLoader() {
        progressBar.setVisibility(FrameLayout.VISIBLE);
    }

    private void hideLoader() {
        progressBar.setVisibility(FrameLayout.INVISIBLE);
    }

    //receiving data from API
    private void getOneCallWeather() {
        weatherRepository.getWeather(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processWhenReceivingOneCallResponse, Throwable::printStackTrace);
    }

    private void getCurrentWeather() {
        weatherRepository.getCurrent(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processWhenReceivingCurrentWeatherResponse, Throwable::printStackTrace);
    }

    //response actions
    private void processWhenReceivingOneCallResponse(OnecallLocal onecallLocal) {
        saveOneCallWeatherData(onecallLocal);
        updateOneCallWeather(onecallLocal);
    }

    private void processWhenReceivingCurrentWeatherResponse(CurrentWeatherLocal currentWeatherModel) {
        saveCurrentWeatherData(currentWeatherModel);
        updateCurrentWeather(currentWeatherModel);
    }

    //updating UI
    private void updateOneCallWeather(OnecallLocal onecallLocal) {

        if (!onecallLocal.getHourLocals().isEmpty()) {
            updateHourlyRecyclerView(onecallLocal.getHourLocals());
        }

        if (!onecallLocal.getDayLocals().isEmpty()) {
            updateDailyRecyclerView(onecallLocal.getDayLocals());
        }
    }

    private void updateHourlyRecyclerView(List<HourLocal> list) {
        ArrayList<HourWeather> hourWeathers = HelperMethods.createHourWeatherList(list, this);

        HourlyAdapter hourlyAdapter = new HourlyAdapter(hourWeathers);

        rvHourWeather.setAdapter(hourlyAdapter);
    }

    private void updateDailyRecyclerView(List<DayLocal> list) {
        ArrayList<DayWeather> dayWeathers = HelperMethods.createDayWeatherList(list, this);

        DailyAdapter dailyAdapter = new DailyAdapter(dayWeathers);

        rvDailyWeather.setAdapter(dailyAdapter);
    }

    private void updateCurrentWeather(CurrentWeatherLocal currentWeatherLocal) {

        if (currentWeatherLocal.getName() != null) {
            updateCurrentLocation(currentWeatherLocal.getName());
        }

        if (!currentWeatherLocal.getWeather().isEmpty()) {
            updateWeather(
                    currentWeatherLocal.getWeather().get(0).getIcon(),
                    currentWeatherLocal.getWeather().get(0).getDescription()
            );
        }

        if (currentWeatherLocal.getMain() != null) {
            updateCurrentWeatherParameters(
                    currentWeatherLocal.getMain().getPressure(),
                    currentWeatherLocal.getMain().getHumidity(),
                    currentWeatherLocal.getMain().getTemp(),
                    currentWeatherLocal.getMain().getFeelsLike()
            );
        }

        if (currentWeatherLocal.getSunrise() != null) {
            updateSunrise(currentWeatherLocal.getSunrise());
        }

        if (currentWeatherLocal.getSunset() != null) {
            updateSunset(currentWeatherLocal.getSunset());
        }

        if (currentWeatherLocal.getWindSpeed() != null) {
            updateWindSpeed(currentWeatherLocal.getWindSpeed());
        }

    }

    private void updateCurrentWeatherParameters(int pressure,
                                                int humidity,
                                                double currentTemperature,
                                                double feelsLike) {
        updatePressure(pressure);
        updateHumidity(humidity);
        updateCurrentTemperature(currentTemperature);
        updateCurrentWeatherFeelsLike(feelsLike);
    }

    private void updateWeather(String icon, String description) {
        updateCurrentWeatherIcon(icon);
        updateCurrentWeatherDescription(description);
    }

    private void updateCurrentLocation(String location) {
        locationTextView.setText(location);
    }

    private void updateCurrentWeatherIcon(String iconId) {
        String path = ICON_PATH + iconId + ICON_EXTENSION;

        Picasso.get().load(path).error(R.drawable.unknown).into(currentWeatherIconImageView);
    }

    private void updateCurrentWeatherDescription(String description) {
        descriptionTextView.setText(description);
    }

    private void updatePressure(int pressure) {
        pressureTextView.setText(HelperMethods.pressureToString(pressure, this));
    }

    private void updateHumidity(int humidity) {
        String humidityString = humidity + getString(R.string.humidity);

        humidityTextView.setText(humidityString);
    }

    private void updateCurrentTemperature(double temperature) {
        currentTemperature.setText(HelperMethods.temperatureToString(temperature, this));
    }

    private void updateCurrentWeatherFeelsLike(double feelsLike) {
        feelsLikeTextView.setText(
                String.format(
                        getString(R.string.feels_like),
                        HelperMethods.temperatureToString(feelsLike, this)
                )
        );
    }

    private void updateSunrise(long sunrise) {
        sunriseTextView.setText(HelperMethods.getTime(sunrise));
    }

    private void updateSunset(long sunset) {
        sunsetTextView.setText(HelperMethods.getTime(sunset));
    }

    private void updateWindSpeed(double windSpeed) {
        windTextView.setText(
                HelperMethods
                        .windSpeedToString(
                                windSpeed,
                                this
                        )
        );
    }

    //saving data
    private void saveCurrentWeatherData(CurrentWeatherLocal currentWeatherLocal) {
        currentWeatherLocalDatabase
                .executeTransaction(realm ->
                        realm.insertOrUpdate(currentWeatherLocal)
                );
    }

    private void saveOneCallWeatherData(OnecallLocal onecallLocal) {
        oneCallWeatherDatabase
                .executeTransaction(realm ->
                        realm.insertOrUpdate(onecallLocal)
                );
    }

    //swipe detector
    @SuppressLint("ClickableViewAccessibility")
    private void setSwipeDetector() {
        swipeDetector = new GestureDetectorCompat(this, new SwipeGestureListener());
        mainLayout.setOnTouchListener((v, event) -> swipeDetector.onTouchEvent(event));
    }

    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (Math.abs(e1.getY() - e2.getY()) < SWIPE_MIN_DISTANCE) {
                return false;
            }
            if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_MIN_VELOCITY) {
                updateUIFromAPI();
            }
            return false;
        }
    }
}

