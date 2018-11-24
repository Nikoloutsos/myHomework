package com.tutorial.androiddreamer.myhomework.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tutorial.androiddreamer.myhomework.ViewModels.SettingsActivityViewModel;
import com.tutorial.androiddreamer.myhomework.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {
    public static final String MY_SHARED_PREFERENCES_NAME = "my_pref";
    public static final String SHARED_PREFERENCE_ORDER = "com.tutorial.androiddreamer.myhomework.sharedPreference_order";
    public static final String SHARED_PREFERENCE_APPEARANCE = "com.tutorial.androiddreamer.myhomework.sharedPreference_appearance";
    public static final int APPEARANCE_LIGHT= 0;
    public static final int APPEARANCE_BLACK = 1;
    public static final int ORDER_TIME = 0;
    public static final int ORDER_IMPORTANCE = 1;
    private SettingsActivityViewModel settingsActivityViewModel;

    @BindView(R.id.sp_activity_settings_order) Spinner spOrder;
    @BindView(R.id.sp_activity_settings_appearance) Spinner spAppearance;
    private int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debb", "onCreate: " + check);
        settingsActivityViewModel = ViewModelProviders.of(this).get(SettingsActivityViewModel.class);
        setUITheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setTitle("Settings");
        ActionBar actionBar = getSupportActionBar();
        setUpSpinnerViews();

        spOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(++check > 2){
                    saveUserPreferences();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spAppearance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(++check > 2){
                    saveUserPreferences();
                    Intent intent = getIntent();
                    setResult(RESULT_OK);
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade,R.anim.fade_out);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

    }

    private void setUpSpinnerViews() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.orders_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrder.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.appearance_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAppearance.setAdapter(adapter2);

        setSpinnersDefaultValues();

    }
    private void saveUserPreferences(){
        settingsActivityViewModel.getSharedPrefRepository().getSharedPreferencesDAO().saveSharedPreferences(spOrder.getSelectedItemPosition(),
                spAppearance.getSelectedItemPosition());
    }

    private void setSpinnersDefaultValues(){
        spOrder.setSelection(settingsActivityViewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefOrder());
        spAppearance.setSelection(settingsActivityViewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }


    private void setUITheme(){
        if (settingsActivityViewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 1){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
    }
}
