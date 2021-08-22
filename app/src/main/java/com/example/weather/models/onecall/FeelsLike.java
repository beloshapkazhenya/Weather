
package com.example.weather.models.onecall;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

@SuppressWarnings("unused")
public class FeelsLike  {

    @SerializedName("day")
    private Double mDay;
    @SerializedName("eve")
    private Double mEve;
    @SerializedName("morn")
    private Double mMorn;
    @SerializedName("night")
    private Double mNight;

    public Double getDay() {
        return mDay;
    }

    public void setDay(Double day) {
        mDay = day;
    }

    public Double getEve() {
        return mEve;
    }

    public void setEve(Double eve) {
        mEve = eve;
    }

    public Double getMorn() {
        return mMorn;
    }

    public void setMorn(Double morn) {
        mMorn = morn;
    }

    public Double getNight() {
        return mNight;
    }

    public void setNight(Double night) {
        mNight = night;
    }

}
