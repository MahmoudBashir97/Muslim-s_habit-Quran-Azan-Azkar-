package com.mahmoudbashir.prefs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPreference {
    Context context;
    private static final String SHARED_PREF_USER = "AZAN_APP";

    private static SharedPreference sharedPrefranceManager;

    private SharedPreference(Context context) {
        this.context = context;
    }

    public synchronized static SharedPreference getInastance(Context context) {
        if (sharedPrefranceManager == null) {
            sharedPrefranceManager = new SharedPreference(context);
        }
        return sharedPrefranceManager;
    }

    public void save_alarm_status(boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("alarm_status", status);
        editor.apply();
    }

    public void save_InfoData(String city, int times_way,String lat,String lng) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //editor.clear();
        editor.putString("city", city);
        editor.putInt("times_school_Id", times_way);
        editor.putString("lat", lat);
        editor.putString("lng", lng);
        editor.putBoolean("userLogged", true);
        editor.apply();
    }
    public void savePath(String path_name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString("path", path_name);

        editor.apply();
    }

    public void saveLastCount(int count){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putInt("last_count", count);

        editor.apply();
    }
    public int getLastCount(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("last_count", 0);
    }
    public String getPath(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("path", "");
    }

    public String getLat(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("lat", "");
    }
    public String getLng(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("lng", "");
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("userLogged", false);
    }

    public boolean getAlarmStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("alarm_status", true);
    }

    public String getCity() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("city", "");
    }

    public int getTimesSchoolId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("times_school_Id", -1);
    }

}