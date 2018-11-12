package model;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tutorial.androiddreamer.myhomework.GraphHistoryFragment;
import com.tutorial.androiddreamer.myhomework.NoteHistoryFragment;

public class HistoryPagerAdapter extends FragmentStatePagerAdapter {
    public static final int NUMBER_OF_FRAGMENTS = 2;


    public HistoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NoteHistoryFragment();
            case 1:
                return new GraphHistoryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENTS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "History";
            case 1:
                return "progress";
            default:
                return null;
        }
    }
}
