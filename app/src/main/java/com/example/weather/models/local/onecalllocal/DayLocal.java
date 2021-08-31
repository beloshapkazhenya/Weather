package com.example.weather.models.local.onecalllocal;

import io.realm.RealmObject;

public class DayLocal extends RealmObject {
    private long dt;
    private String iconId;
    private double temperatureDay;
    private double temperatureNight;

    public DayLocal(long dt, String iconId, double temperatureDay, double temperatureNight) {
        this.dt = dt;
        this.iconId = iconId;
        this.temperatureDay = temperatureDay;
        this.temperatureNight = temperatureNight;
    }

    public DayLocal() {

    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getDt() {
        return dt;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getIconId() {
        return iconId;
    }

    public void setTemperatureDay(double temperatureDay) {
        this.temperatureDay = temperatureDay;
    }

    public double getTemperatureDay() {
        return temperatureDay;
    }

    public void setTemperatureNight(double temperatureNight) {
        this.temperatureNight = temperatureNight;
    }

    public double getTemperatureNight() {
        return temperatureNight;
    }
}
