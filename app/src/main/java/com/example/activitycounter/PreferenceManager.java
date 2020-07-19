package com.example.activitycounter;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    public static final String PREFERENCES_NAME = "rebuild_preference";
    private static final long DEFAULT_VALUE_LONG = -1L;


    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setLong(Context context, String key, long value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        long value = prefs.getLong(key, DEFAULT_VALUE_LONG);
        return value;
    }

    public static void clear(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();

    }
}
