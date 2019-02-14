package com.tutorial.androiddreamer.myhomework.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.tutorial.androiddreamer.myhomework.ViewModels.SettingsActivityViewModel;
import com.tutorial.androiddreamer.myhomework.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {
    public static final String SHARED_PREFERENCE_ORDER = "com.tutorial.androiddreamer.myhomework.sharedPreference_order";
    public static final String SHARED_PREFERENCE_APPEARANCE = "com.tutorial.androiddreamer.myhomework.sharedPreference_appearance";
    public static final int APPEARANCE_LIGHT= 0;
    public static final int APPEARANCE_BLACK = 1;
    public static final int ORDER_TIME = 0;
    public static final int ORDER_IMPORTANCE = 1;
    private SettingsActivityViewModel viewModel;

    @BindView(R.id.sp_activity_settings_order) Spinner spOrder;
    @BindView(R.id.sp_activity_settings_appearance) Spinner spAppearance;
    @BindView(R.id.cl_activity_settings) ConstraintLayout constraintLayout;
    @BindView(R.id.tv_order_by) TextView tvOrderBy;
    @BindView(R.id.tv_appearance) TextView tvAppearance;
    private int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debb", "onCreate: " + check);
        viewModel = ViewModelProviders.of(this).get(SettingsActivityViewModel.class);
        setUITheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setTitle("Settings");
        setUIThemeForElements();
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
        ArrayList<CharSequence> order = new ArrayList<>();
        order.add(getResources().getString(R.string.time));
        order.add(getResources().getString(R.string.importance));

        ArrayAdapter<CharSequence> adapter;

        if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 1){
            adapter = new ArrayAdapter<CharSequence>(this,
                    android.R.layout.simple_spinner_item, order){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    TextView tv = (TextView) super.getView(position, convertView, parent);
                    tv.setTextColor(Color.WHITE);
                    // Return the view
                    return tv;
                }
            };
        }else{
            adapter = new ArrayAdapter<CharSequence>(this,
                    android.R.layout.simple_spinner_item, order);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrder.setAdapter(adapter);


        ArrayList<CharSequence> theme = new ArrayList<>();
        theme.add(getResources().getString(R.string.light));
        theme.add(getResources().getString(R.string.Dark));

        ArrayAdapter<CharSequence> adapter2;

        if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 1){
            adapter2 = new ArrayAdapter<CharSequence>(this,
                    android.R.layout.simple_spinner_item, theme){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    TextView tv = (TextView) super.getView(position, convertView, parent);
                    tv.setTextColor(Color.WHITE);
                    // Return the view
                    return tv;
                }
            };
        }else{
            adapter2 = new ArrayAdapter<CharSequence>(this,
                    android.R.layout.simple_spinner_item, theme);
        }


        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAppearance.setAdapter(adapter2);

        setSpinnersDefaultValues();

    }
    private void saveUserPreferences(){
        viewModel.getSharedPrefRepository().getSharedPreferencesDAO().saveSharedPreferences(spOrder.getSelectedItemPosition(),
                spAppearance.getSelectedItemPosition());
    }
    private void setSpinnersDefaultValues(){
        spOrder.setSelection(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefOrder());
        spAppearance.setSelection(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme());
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
        if (viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 1){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
    }
    private void setUIThemeForElements(){
        if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 0){
            constraintLayout.setBackground(getDrawable(R.drawable.veneer_repeating));


        }else if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 1){
            constraintLayout.setBackground(getDrawable(R.drawable.stardust_repeating));
            tvAppearance.setTextColor(Color.WHITE);
            tvOrderBy.setTextColor(Color.WHITE);
        }
    }

}
