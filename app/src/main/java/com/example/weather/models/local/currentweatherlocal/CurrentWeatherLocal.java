package com.example.weather.models.local.currentweatherlocal;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class CurrentWeatherLocal extends RealmObject {

    private RealmList<WeatherLocal> weather;
    private MainLocal mainLocal;
    private Double windSpeed;
    private Integer dt;
    private String name;
    private Integer sunrise;
    private Integer sunset;

    public CurrentWeatherLocal(RealmList<WeatherLocal> weathers,
                               MainLocal mainLocal,
                               double windSpeed,
                               Integer dt,
                               Integer sunrise,
                               Integer sunset,
                               String name) {
        this.weather = weathers;
        this.mainLocal = mainLocal;
        this.windSpeed = windSpeed;
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.name = name;
    }

    public CurrentWeatherLocal() {
    }


    public List<WeatherLocal> getWeather() {
        return weather;
    }

    public void setWeather(RealmList<WeatherLocal> weather) {
        this.weather = weather;
    }

    public MainLocal getMain() {
        return mainLocal;
    }

    public void setMain(MainLocal mainLocal) {
        this.mainLocal = mainLocal;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double speed) {
        this.windSpeed = speed;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

}
