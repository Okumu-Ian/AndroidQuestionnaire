package com.app.itsforher;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static final String SHARED_PREF_NAME = "assigno";

    private static Context mCtx;

    public Prefs(Context context) {
        mCtx = context;
    }


    //this method will store the user data in shared preferences
    public void putUser(String username) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", username);
        editor.apply();
    }

    //this method will give the logged in user
    public String getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user", null);
    }


    public void putURL(String username) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("url", username);
        editor.apply();
    }

    public String getURL() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("url", null);
    }

}
