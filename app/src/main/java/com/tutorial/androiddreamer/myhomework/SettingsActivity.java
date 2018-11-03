package com.tutorial.androiddreamer.myhomework;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {
    private Spinner spOrder, spAppearance;
    public static final String MY_SHARED_PREFERENCES_NAME = "my_pref";
    public static final String SHARED_PREFERENCE_ORDER = "com.tutorial.androiddreamer.myhomework.sharedPreference_order";
    public static final String SHARED_PREFERENCE_IMPORTANCE = "com.tutorial.androiddreamer.myhomework.sharedPreference_appearance";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        setTitle("Settings");
        setUpSpinnerViews();
    }

    private void setUpSpinnerViews() {
        spOrder = findViewById(R.id.sp_activity_settings_order);

        AdapterView.OnItemSelectedListener callback = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveUserPreferences();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.orders_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrder.setAdapter(adapter);
        spAppearance = findViewById(R.id.sp_activity_settings_appearance);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.appearance_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAppearance.setAdapter(adapter2);

        setSpinnersDefaultValues();


        spOrder.setOnItemSelectedListener(callback);
        spAppearance.setOnItemSelectedListener(callback);
    }
/*

int LIGHT_APPEARANCE = 0;
int DARK_APPEARANCE = 1;
int ORDER_BY_TIME = 0;
int ORDER_BY_IMPORTANCE = 1;
 */

    private void saveUserPreferences(){
        SharedPreferences sharedPref = this.getSharedPreferences(
                MY_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(SHARED_PREFERENCE_ORDER, spOrder.getSelectedItemPosition());
        editor.putInt(SHARED_PREFERENCE_IMPORTANCE, spAppearance.getSelectedItemPosition());
        editor.apply();
    }

    private void setSpinnersDefaultValues(){
        SharedPreferences sharedPref = this.getSharedPreferences(
                MY_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        spOrder.setSelection(sharedPref.getInt(SHARED_PREFERENCE_ORDER, 0));
        spAppearance.setSelection(sharedPref.getInt(SHARED_PREFERENCE_IMPORTANCE, 0));
    }
}
