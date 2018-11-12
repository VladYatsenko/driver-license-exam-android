package com.android.testdai.util;

import android.content.Context;
import android.content.SharedPreferences;

import static com.android.testdai.util.Constants.APP_PREFERENCES_CATEGORY;
import static com.android.testdai.util.Constants.APP_PREFERENCES_DOUBLE_CLICK;

public class PreferencesUtil {

    private static PreferencesUtil preferencesUtil;
    private SharedPreferences settings;


    public static PreferencesUtil getInstance(Context context){
        if(preferencesUtil == null){
            preferencesUtil = new PreferencesUtil(context);
        }
        return preferencesUtil;
    }

    private PreferencesUtil(Context context) {
        settings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public String getCategory() {

        return settings.getString(APP_PREFERENCES_CATEGORY, "B");

    }

    public void setCategory(String category){

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(APP_PREFERENCES_CATEGORY, category);
        editor.apply();

    }

    public boolean getPreference(String key){

        if(key.equals(APP_PREFERENCES_DOUBLE_CLICK))
            return settings.getBoolean(key, false);
        else
            return settings.getBoolean(key, true);

    }

    public void setPreference(String key, boolean state){

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, state);
        editor.apply();

    }

}
