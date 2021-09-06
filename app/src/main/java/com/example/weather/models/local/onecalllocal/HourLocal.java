package com.example.weather.models.local.onecalllocal;

import io.realm.RealmObject;

public class HourLocal extends RealmObject {
    private long dt;
    private double temperature;
    private String iconId;

    public HourLocal(long dt, double temperature, String iconId) {
        this.dt = dt;
        this.temperature = temperature;
        this.iconId = iconId;
    }

    public HourLocal() {
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getDt() {
        return dt;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getIconId() {
        return iconId;
    }
}
