
package com.example.weather.models.onecall;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Daily {

    @SerializedName("clouds")
    private Long mClouds;
    @SerializedName("dew_point")
    private Double mDewPoint;
    @SerializedName("dt")
    private Long mDt;
    @SerializedName("feels_like")
    private FeelsLike mFeelsLike;
    @SerializedName("humidity")
    private Long mHumidity;
    @SerializedName("moon_phase")
    private Double mMoonPhase;
    @SerializedName("moonrise")
    private Long mMoonrise;
    @SerializedName("moonset")
    private Long mMoonset;
    @SerializedName("pop")
    private float mPop;
    @SerializedName("pressure")
    private Long mPressure;
    @SerializedName("sunrise")
    private Long mSunrise;
    @SerializedName("sunset")
    private Long mSunset;
    @SerializedName("temp")
    private Temp mTemp;
    @SerializedName("uvi")
    private Double mUvi;
    @SerializedName("weather")
    private List<Weather> mWeather;
    @SerializedName("wind_deg")
    private Long mWindDeg;
    @SerializedName("wind_gust")
    private Double mWindGust;
    @SerializedName("wind_speed")
    private Double mWindSpeed;

    public Long getClouds() {
        return mClouds;
    }

    public void setClouds(Long clouds) {
        mClouds = clouds;
    }

    public Double getDewPoint() {
        return mDewPoint;
    }

    public void setDewPoint(Double dewPoint) {
        mDewPoint = dewPoint;
    }

    public Long getDt() {
        return mDt;
    }

    public void setDt(Long dt) {
        mDt = dt;
    }

    public FeelsLike getFeelsLike() {
        return mFeelsLike;
    }

    public void setFeelsLike(FeelsLike feelsLike) {
        mFeelsLike = feelsLike;
    }

    public Long getHumidity() {
        return mHumidity;
    }

    public void setHumidity(Long humidity) {
        mHumidity = humidity;
    }

    public Double getMoonPhase() {
        return mMoonPhase;
    }

    public void setMoonPhase(Double moonPhase) {
        mMoonPhase = moonPhase;
    }

    public Long getMoonrise() {
        return mMoonrise;
    }

    public void setMoonrise(Long moonrise) {
        mMoonrise = moonrise;
    }

    public Long getMoonset() {
        return mMoonset;
    }

    public void setMoonset(Long moonset) {
        mMoonset = moonset;
    }

    public float getPop() {
        return mPop;
    }

    public void setPop(float pop) {
        mPop = pop;
    }

    public Long getPressure() {
        return mPressure;
    }

    public void setPressure(Long pressure) {
        mPressure = pressure;
    }

    public Long getSunrise() {
        return mSunrise;
    }

    public void setSunrise(Long sunrise) {
        mSunrise = sunrise;
    }

    public Long getSunset() {
        return mSunset;
    }

    public void setSunset(Long sunset) {
        mSunset = sunset;
    }

    public Temp getTemp() {
        return mTemp;
    }

    public void setTemp(Temp temp) {
        mTemp = temp;
    }

    public Double getUvi() {
        return mUvi;
    }

    public void setUvi(Double uvi) {
        mUvi = uvi;
    }

    public List<Weather> getWeather() {
        return mWeather;
    }

    public void setWeather(List<Weather> weather) {
        mWeather = weather;
    }

    public Long getWindDeg() {
        return mWindDeg;
    }

    public void setWindDeg(Long windDeg) {
        mWindDeg = windDeg;
    }

    public Double getWindGust() {
        return mWindGust;
    }

    public void setWindGust(Double windGust) {
        mWindGust = windGust;
    }

    public Double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        mWindSpeed = windSpeed;
    }

}
