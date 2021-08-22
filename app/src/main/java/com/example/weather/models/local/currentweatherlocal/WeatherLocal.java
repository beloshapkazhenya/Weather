package com.example.weather.models.local.currentweatherlocal;

import io.realm.RealmObject;

public class WeatherLocal extends RealmObject {

    private String description;
    private String icon;

    public WeatherLocal(){
        description = "";
        icon = "";
    }

    public WeatherLocal(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
