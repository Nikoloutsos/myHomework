package com.tutorial.androiddreamer.myhomework.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.tutorial.androiddreamer.myhomework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.tutorial.androiddreamer.myhomework.Adapters.HistoryPagerAdapter;
import com.tutorial.androiddreamer.myhomework.ViewModels.OverviewActivityViewModel;


public class OverviewActivity extends AppCompatActivity {
    OverviewActivityViewModel viewModel;
    HistoryPagerAdapter historyPagerAdapter;
    @BindView(R.id.vp_activity_history) ViewPager viewPager;
    @BindView(R.id.tl_activity_history) TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(OverviewActivityViewModel.class);
        setUITheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        ButterKnife.bind(this);
        setUIThemeForElements();
        ActionBar actionBar = getSupportActionBar();

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        historyPagerAdapter = new HistoryPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(historyPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        actionBar.setTitle("Summary");
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
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void setUITheme(){
        switch (viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme()){
            case 0:
                setTheme(R.style.AppTheme);
                break;
            case 1:
                setTheme(R.style.DarkTheme);
                break;
        }

    }

    private void setUIThemeForElements(){
        //TODO use the right colors!

        switch (viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme()){
            case 0:
                viewPager.setBackground(getDrawable(R.drawable.veneer_repeating));
                break;
            case 1:
                tabLayout.setBackgroundColor(Color.parseColor("#ff212121"));
                viewPager.setBackground(getDrawable(R.drawable.stardust_repeating));
                break;
        }



    }
}
