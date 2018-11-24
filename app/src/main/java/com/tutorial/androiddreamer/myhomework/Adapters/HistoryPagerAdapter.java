package com.tutorial.androiddreamer.myhomework.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tutorial.androiddreamer.myhomework.Fragments.GraphHistoryFragment;
import com.tutorial.androiddreamer.myhomework.Fragments.ArchivedNoteFragment;

public class HistoryPagerAdapter extends FragmentStatePagerAdapter {
    public static final int NUMBER_OF_FRAGMENTS = 2;


    public HistoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ArchivedNoteFragment();
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
                return "Archived notes";
            case 1:
                return "Statistics";
            default:
                return null;
        }
    }
}
