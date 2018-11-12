package com.tutorial.androiddreamer.myhomework;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.HistoryPagerAdapter;


public class HistoryActivity extends AppCompatActivity {
    HistoryPagerAdapter historyPagerAdapter;
    @BindView(R.id.vp_activity_history)
    ViewPager viewPager;
    @BindView(R.id.tl_activity_history)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ActionBar actionBar = getSupportActionBar();
        ButterKnife.bind(this);

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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        super.onBackPressed();
    }
}
