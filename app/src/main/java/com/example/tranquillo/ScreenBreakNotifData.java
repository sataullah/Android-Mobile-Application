package com.example.tranquillo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class ScreenBreakNotifData {

    private int mondayStart, mondayEnd,
            tuesdayStart, tuesdayEnd,
            wednesdayStart, wednesdayEnd,
            thursdayStart, thursdayEnd,
            fridayStart, fridayEnd,
            saturdayStart, saturdayEnd,
            sundayStart, sundayEnd;

    public final static String MONDAY_START = "mondayStart", MONDAY_END = "mondayEnd",
            TUESDAY_START = "tuesdayStart", TUESDAY_END = "tuesdayEnd",
            WEDNESDAY_START = "wednesdayStart", WEDNESDAY_END = "wednesdayEnd",
            THURSDAY_START = "thursdayStart", THURSDAY_END = "thursdayEnd",
            FRIDAY_START = "fridayStart", FRIDAY_END = "fridayEnd",
            SATURDAY_START = "saturdayStart", SATURDAY_END = "saturdayEnd",
            SUNDAY_START = "sundayStart", SUNDAY_END = "sundayEnd";

    //For local initialisation
    public ScreenBreakNotifData(Context context){
        initFromLocal(context);
    }

    //For initialisation with fresh data from server
    public ScreenBreakNotifData(Context context, DataSnapshot dataSnapshot)
    {
        initFromSnapshot(dataSnapshot);
        updateLocalData(context);
    }

    private void initFromSnapshot(DataSnapshot dataSnapshot) {
        mondayStart = getTimeFromSnapshot(dataSnapshot, MONDAY_START);
        mondayEnd = getTimeFromSnapshot(dataSnapshot, MONDAY_END);
        tuesdayStart = getTimeFromSnapshot(dataSnapshot, TUESDAY_START);
        tuesdayEnd = getTimeFromSnapshot(dataSnapshot, TUESDAY_END);
        wednesdayStart = getTimeFromSnapshot(dataSnapshot, WEDNESDAY_START);
        wednesdayEnd = getTimeFromSnapshot(dataSnapshot, WEDNESDAY_END);
        thursdayStart = getTimeFromSnapshot(dataSnapshot, THURSDAY_START);
        thursdayEnd = getTimeFromSnapshot(dataSnapshot, THURSDAY_END);
        fridayStart = getTimeFromSnapshot(dataSnapshot, FRIDAY_START);
        fridayEnd = getTimeFromSnapshot(dataSnapshot, FRIDAY_END);
        saturdayStart = getTimeFromSnapshot(dataSnapshot, SATURDAY_START);
        saturdayEnd = getTimeFromSnapshot(dataSnapshot, SATURDAY_END);
        sundayStart = getTimeFromSnapshot(dataSnapshot, SUNDAY_START);
        sundayEnd = getTimeFromSnapshot(dataSnapshot, SUNDAY_END);
    }

    private int getTimeFromSnapshot(DataSnapshot dataSnapshot, String key) {
        if(dataSnapshot.child(key).getValue() == null) return -1;
        else return Integer.parseInt(dataSnapshot.child(key).getValue().toString());
    }

    private void initFromLocal(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        mondayStart = preferences.getInt(MONDAY_START, -1);
        mondayEnd = preferences.getInt(MONDAY_END, -1);
        tuesdayStart = preferences.getInt(TUESDAY_START, -1);
        tuesdayEnd = preferences.getInt(TUESDAY_END, -1);
        wednesdayStart = preferences.getInt(WEDNESDAY_START, -1);
        wednesdayEnd = preferences.getInt(WEDNESDAY_END, -1);
        thursdayStart = preferences.getInt(THURSDAY_START, -1);
        thursdayEnd = preferences.getInt(THURSDAY_END, -1);
        fridayStart = preferences.getInt(FRIDAY_START, -1);
        fridayEnd = preferences.getInt(FRIDAY_END, -1);
        saturdayStart = preferences.getInt(SATURDAY_START, -1);
        saturdayEnd = preferences.getInt(SATURDAY_END, -1);
        sundayStart = preferences.getInt(SUNDAY_START, -1);
        sundayEnd = preferences.getInt(SUNDAY_END, -1);
    }

    public int getDayStartTime(int day){
        switch (day)
        {
            case Calendar.MONDAY:
                return mondayStart;
            case Calendar.TUESDAY:
                return tuesdayStart;
            case Calendar.WEDNESDAY:
                return wednesdayStart;
            case Calendar.THURSDAY:
                return thursdayStart;
            case Calendar.FRIDAY:
                return fridayStart;
            case Calendar.SATURDAY:
                return saturdayStart;
            default:
                return sundayStart;
        }
    }

    public int getDayEndTime(int day) {
        switch (day) {
            case Calendar.MONDAY:
                return mondayEnd;
            case Calendar.TUESDAY:
                return tuesdayEnd;
            case Calendar.WEDNESDAY:
                return wednesdayEnd;
            case Calendar.THURSDAY:
                return thursdayEnd;
            case Calendar.FRIDAY:
                return fridayEnd;
            case Calendar.SATURDAY:
                return saturdayEnd;
            default:
                return sundayEnd;
        }
    }

    public void updateLocalData(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<Boolean> hasChanged = new ArrayList<>();
        hasChanged.add(updatePref(preferences, MONDAY_START, mondayStart));
        hasChanged.add(updatePref(preferences, MONDAY_END, mondayEnd));
        hasChanged.add(updatePref(preferences, TUESDAY_START, tuesdayStart));
        hasChanged.add(updatePref(preferences, TUESDAY_END, tuesdayEnd));
        hasChanged.add(updatePref(preferences, WEDNESDAY_START, wednesdayStart));
        hasChanged.add(updatePref(preferences, WEDNESDAY_END, wednesdayEnd));
        hasChanged.add(updatePref(preferences, THURSDAY_START, thursdayStart));
        hasChanged.add(updatePref(preferences, THURSDAY_END, thursdayEnd));
        hasChanged.add(updatePref(preferences, FRIDAY_START, fridayStart));
        hasChanged.add(updatePref(preferences, FRIDAY_END, fridayEnd));
        hasChanged.add(updatePref(preferences, SATURDAY_START, saturdayStart));
        hasChanged.add(updatePref(preferences, SATURDAY_END, saturdayEnd));
        hasChanged.add(updatePref(preferences, SUNDAY_START, sundayStart));
        hasChanged.add(updatePref(preferences, SUNDAY_END, sundayEnd));
        if (hasChanged.contains(true)) {
            SB_NotificationBReceiver.scheduleDay(context);
        }
    }

    private boolean updatePref(SharedPreferences preferences, String key, int newTime) {
        int oldTime = preferences.getInt(key, -1);
        if (oldTime == newTime) return false;
        else {
            preferences.edit().putInt(key, newTime).apply();
            return true;
        }
    }


    //Getter and Setter
    public int getMondayStart() {
        return mondayStart;
    }

    public void setMondayStart(int mondayStart) {
        this.mondayStart = mondayStart;
    }

    public int getMondayEnd() {
        return mondayEnd;
    }

    public void setMondayEnd(int mondayEnd) {
        this.mondayEnd = mondayEnd;
    }

    public int getTuesdayStart() {
        return tuesdayStart;
    }

    public void setTuesdayStart(int tuesdayStart) {
        this.tuesdayStart = tuesdayStart;
    }

    public int getTuesdayEnd() {
        return tuesdayEnd;
    }

    public void setTuesdayEnd(int tuesdayEnd) {
        this.tuesdayEnd = tuesdayEnd;
    }

    public int getWednesdayStart() {
        return wednesdayStart;
    }

    public void setWednesdayStart(int wednesdayStart) {
        this.wednesdayStart = wednesdayStart;
    }

    public int getWednesdayEnd() {
        return wednesdayEnd;
    }

    public void setWednesdayEnd(int wednesdayEnd) {
        this.wednesdayEnd = wednesdayEnd;
    }

    public int getThursdayStart() {
        return thursdayStart;
    }

    public void setThursdayStart(int thursdayStart) {
        this.thursdayStart = thursdayStart;
    }

    public int getThursdayEnd() {
        return thursdayEnd;
    }

    public void setThursdayEnd(int thursdayEnd) {
        this.thursdayEnd = thursdayEnd;
    }

    public int getFridayStart() {
        return fridayStart;
    }

    public void setFridayStart(int fridayStart) {
        this.fridayStart = fridayStart;
    }

    public int getFridayEnd() {
        return fridayEnd;
    }

    public void setFridayEnd(int fridayEnd) {
        this.fridayEnd = fridayEnd;
    }

    public int getSaturdayStart() {
        return saturdayStart;
    }

    public void setSaturdayStart(int saturdayStart) {
        this.saturdayStart = saturdayStart;
    }

    public int getSaturdayEnd() {
        return saturdayEnd;
    }

    public void setSaturdayEnd(int saturdayEnd) {
        this.saturdayEnd = saturdayEnd;
    }

    public int getSundayStart() {
        return sundayStart;
    }

    public void setSundayStart(int sundayStart) {
        this.sundayStart = sundayStart;
    }

    public int getSundayEnd() {
        return sundayEnd;
    }

    public void setSundayEnd(int sundayEnd) {
        this.sundayEnd = sundayEnd;
    }
}
