package com.example.aman.finalproject.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



/**
 * Created by Aman on 4/24/2017.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private int tabcount;

    public MyPagerAdapter(FragmentManager fm, int tabcount) {
        super(fm);
        this.tabcount = tabcount;
    }

    @Override
    public Fragment getItem(int position) {
      return null;

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
