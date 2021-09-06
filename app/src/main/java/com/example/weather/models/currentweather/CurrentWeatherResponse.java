package com.example.weather.models.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherResponse {

    @SerializedName("coord")
    @Expose
    private CoordResponse coordResponse;
    @SerializedName("weather")
    @Expose
    private List<WeatherResponse> weatherResponse = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private MainResponse mainResponse;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private WindResponse windResponse;
    @SerializedName("clouds")
    @Expose
    private CloudsResponse cloudsResponse;
    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("sys")
    @Expose
    private SysResponse sysResponse;
    @SerializedName("timezone")
    @Expose
    private Integer timezone;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private Integer cod;

    public CoordResponse getCoordResponse() {
        return coordResponse;
    }

    public void setCoordResponse(CoordResponse coordResponse) {
        this.coordResponse = coordResponse;
    }

    public List<WeatherResponse> getWeather() {
        return weatherResponse;
    }

    public void setWeather(List<WeatherResponse> weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MainResponse getMain() {
        return mainResponse;
    }

    public void setMain(MainResponse mainResponse) {
        this.mainResponse = mainResponse;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public WindResponse getWind() {
        return windResponse;
    }

    public void setWind(WindResponse windResponse) {
        this.windResponse = windResponse;
    }

    public CloudsResponse getClouds() {
        return cloudsResponse;
    }

    public void setClouds(CloudsResponse cloudsResponse) {
        this.cloudsResponse = cloudsResponse;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public SysResponse getSys() {
        return sysResponse;
    }

    public void setSys(SysResponse sysResponse) {
        this.sysResponse = sysResponse;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

}
