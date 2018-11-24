package com.tutorial.androiddreamer.myhomework.Model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.tutorial.androiddreamer.myhomework.Activities.SettingsActivity;

import java.util.Set;

public class SharedPreferencesDAO {
    SharedPreferences sharedPreferences;
    public SharedPreferencesDAO(Application application){
        sharedPreferences = application.getSharedPreferences(
                SettingsActivity.MY_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public int getSharedPrefTheme(){
        return sharedPreferences.getInt(SettingsActivity.SHARED_PREFERENCE_APPEARANCE, SettingsActivity.APPEARANCE_LIGHT);
    }

    public int getSharedPrefOrder(){
        return sharedPreferences.getInt(SettingsActivity.SHARED_PREFERENCE_ORDER, SettingsActivity.ORDER_TIME);
    }

    public void saveSharedPreferences(int order_mode, int theme_mode){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SettingsActivity.SHARED_PREFERENCE_ORDER, order_mode);
        editor.putInt(SettingsActivity.SHARED_PREFERENCE_APPEARANCE, theme_mode);
        editor.apply();


    }

}
