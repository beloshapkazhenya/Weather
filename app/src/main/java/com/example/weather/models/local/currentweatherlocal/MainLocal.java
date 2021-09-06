package com.example.weather.models.local.currentweatherlocal;

import io.realm.RealmObject;

public class MainLocal extends RealmObject {

    private Double temp;
    private Double feelsLike;
    private Integer pressure;
    private Integer humidity;

    public MainLocal(Double temp, Double feelsLike, Integer pressure, Integer humidity) {
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public MainLocal() {
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

}
