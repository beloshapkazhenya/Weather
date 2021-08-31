package com.example.weather.models.local.onecalllocal;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class OneCallLocalModel extends RealmObject {
    RealmList<HourLocal> hourLocals;
    RealmList<DayLocal> dayLocals;

    public OneCallLocalModel(RealmList<HourLocal> hourLocals,
                             RealmList<DayLocal> dayLocals) {
        this.hourLocals = hourLocals;
        this.dayLocals = dayLocals;
    }

    public OneCallLocalModel() {

    }

    public void setHourLocals(RealmList<HourLocal> hourLocals) {
        this.hourLocals = hourLocals;
    }

    public List<HourLocal> getHourLocals() {
        return hourLocals;
    }

    public void setDayLocals(RealmList<DayLocal> dayLocals) {
        this.dayLocals = dayLocals;
    }

    public List<DayLocal> getDayLocals() {
        return dayLocals;
    }
}
