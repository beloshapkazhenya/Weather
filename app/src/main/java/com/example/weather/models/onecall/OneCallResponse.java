
package com.example.weather.models.onecall;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class OneCallResponse {

    @SerializedName("current")
    private CurrentResponse mCurrentResponse;
    @SerializedName("daily")
    private List<DailyResponse> mDailyResponse;
    @SerializedName("hourly")
    private List<HourlyResponse> mHourlyResponse;
    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lon")
    private Double mLon;
    @SerializedName("timezone")
    private String mTimezone;
    @SerializedName("timezone_offset")
    private Long mTimezoneOffset;

    public CurrentResponse getCurrent() {
        return mCurrentResponse;
    }

    public void setCurrent(CurrentResponse currentResponse) {
        mCurrentResponse = currentResponse;
    }

    public List<DailyResponse> getDaily() {
        return mDailyResponse;
    }

    public void setDaily(List<DailyResponse> dailyResponse) {
        mDailyResponse = dailyResponse;
    }

    public List<HourlyResponse> getHourly() {
        return mHourlyResponse;
    }

    public void setHourly(List<HourlyResponse> hourlyResponse) {
        mHourlyResponse = hourlyResponse;
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
