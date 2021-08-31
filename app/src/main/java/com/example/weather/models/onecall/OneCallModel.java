
package com.example.weather.models.onecall;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class OneCallModel {

    @SerializedName("current")
    private Current mCurrent;
    @SerializedName("daily")
    private List<Daily> mDaily;
    @SerializedName("hourly")
    private List<Hourly> mHourly;
    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lon")
    private Double mLon;
    @SerializedName("timezone")
    private String mTimezone;
    @SerializedName("timezone_offset")
    private Long mTimezoneOffset;

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public List<Daily> getDaily() {
        return mDaily;
    }

    public void setDaily(List<Daily> daily) {
        mDaily = daily;
    }

    public List<Hourly> getHourly() {
        return mHourly;
    }

    public void setHourly(List<Hourly> hourly) {
        mHourly = hourly;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public Double getLon() {
        return mLon;
    }

    public void setLon(Double lon) {
        mLon = lon;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public Long getTimezoneOffset() {
        return mTimezoneOffset;
    }

    public void setTimezoneOffset(Long timezoneOffset) {
        mTimezoneOffset = timezoneOffset;
    }

}
