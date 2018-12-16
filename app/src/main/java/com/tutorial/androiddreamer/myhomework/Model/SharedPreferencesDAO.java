package com.tutorial.androiddreamer.myhomework.Model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.tutorial.androiddreamer.myhomework.Activities.SettingsActivity;


/**
 * The role of this class is only to help me interact with sharedPref values in the whole app.
 */
public class SharedPreferencesDAO {
    public static final String MY_SHARED_PREFERENCES_NAME = "my_pref";
    public static final String MY_SHARED_PREFERENCES_NAME_STATS = "my_pref_stats";

    public static final String MY_SHARED_PREFERENCES_TOTAL_NOTES_ADDED = "com.tutorial.androiddreamer.myhomework.Model.TotalNotes";
    public static final String MY_SHARED_PREFERENCES_TOTAL_NOTES_EDITED = "com.tutorial.androiddreamer.myhomework.Model.TotalEditedNotes";
    public static final String MY_SHARED_PREFERENCES_TOTAL_NOTES_ARCHIVED = "com.tutorial.androiddreamer.myhomework.Model.TotalNotesArchived";
    public static final String MY_SHARED_PREFERENCES_TOTAL_NOTES_DELETED = "com.tutorial.androiddreamer.myhomework.Model.TotalNotesDeleted";
    public static final String MY_SHARED_PREFERENCES_TOTAL_NOTES_SHARED = "com.tutorial.androiddreamer.myhomework.Model.TotalNotesShared";
    public static final String MY_SHARED_PREFERENCES_TOTAL_TUOA = "com.tutorial.androiddreamer.myhomework.Model.TotalTimesUserOpenApp";


    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesStats;

    public SharedPreferencesDAO(Application application){
        sharedPreferences = application.getSharedPreferences(MY_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sharedPreferencesStats = application.getSharedPreferences(MY_SHARED_PREFERENCES_NAME_STATS, Context.MODE_PRIVATE);
    }


    public void saveSharedPreferences(int order_mode, int theme_mode){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SettingsActivity.SHARED_PREFERENCE_ORDER, order_mode);
        editor.putInt(SettingsActivity.SHARED_PREFERENCE_APPEARANCE, theme_mode);
        editor.apply();
    }
    public int getSharedPrefTheme(){
        return sharedPreferences.getInt(SettingsActivity.SHARED_PREFERENCE_APPEARANCE, SettingsActivity.APPEARANCE_LIGHT);
    }
    public int getSharedPrefOrder(){
        return sharedPreferences.getInt(SettingsActivity.SHARED_PREFERENCE_ORDER, SettingsActivity.ORDER_TIME);
    }

    //Statistics

    /**
     * Increase the number of total notes Added/Edited/Deleted/Archived
     */
    public void increaseTotalNotes(){
        increaseTag(MY_SHARED_PREFERENCES_TOTAL_NOTES_ADDED);
    }
    public void increaseTotalNotesEdited(){
        increaseTag(MY_SHARED_PREFERENCES_TOTAL_NOTES_EDITED);
    }
    public void increaseTotalNotesArchived(){
        increaseTag(MY_SHARED_PREFERENCES_TOTAL_NOTES_ARCHIVED);
    }
    public void increaseTotalNotesDeleted(){
        increaseTag(MY_SHARED_PREFERENCES_TOTAL_NOTES_DELETED);
    }
    public void increaseTotalNotesShared(){
        increaseTag(MY_SHARED_PREFERENCES_TOTAL_NOTES_SHARED);
    }

    private void increaseTag(String key){
        SharedPreferences.Editor editor = sharedPreferencesStats.edit();
        editor.putInt(key, 1 + sharedPreferencesStats.getInt(key, 0));
        editor.apply();
    }



    /**
     *
     * @return The number of notes Added/Edited/Deleted/Archived
     */
    public int getTotalNotesAdded(){
        return sharedPreferencesStats.getInt(MY_SHARED_PREFERENCES_TOTAL_NOTES_ADDED, 0);
    }
    public int getTotalNotesEdited(){
        return sharedPreferencesStats.getInt(MY_SHARED_PREFERENCES_TOTAL_NOTES_EDITED,0);
    }
    public int getTotalNotesDeleted(){
        return sharedPreferencesStats.getInt(MY_SHARED_PREFERENCES_TOTAL_NOTES_DELETED,0);
    }
    public int getTotalNotesArchived(){
        return sharedPreferencesStats.getInt(MY_SHARED_PREFERENCES_TOTAL_NOTES_ARCHIVED,0);
    }

    public int getTotalNotesShared() {
        return sharedPreferencesStats.getInt(MY_SHARED_PREFERENCES_TOTAL_NOTES_SHARED, 0);
    }

}
