package com.example.amanarora.watchit.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.amanarora.watchit.ui.FavoritesFragment;
import com.example.amanarora.watchit.ui.MoviesFragment;

/**
 * Created by Aman's Laptop on 4/21/2016.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    int NumOfTabs;

    public TabsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.NumOfTabs = NumOfTabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                // Top Rated fragment activity
                return new MoviesFragment();
            case 1:
                // Games fragment activity
                return new FavoritesFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        return NumOfTabs;
    }
}
