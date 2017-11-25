package com.project.pro112.hydrateam.thepolycoffee.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.pro112.hydrateam.thepolycoffee.fragment.Cakes;
import com.project.pro112.hydrateam.thepolycoffee.fragment.Drinks;
import com.project.pro112.hydrateam.thepolycoffee.fragment.PopularDish;

/**
 * Created by VI on 24/11/2017.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    //mảng title của tabLayout
    private String titleTablayouts[] = {"Popular dish","Drinks","Cakes"};
    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PopularDish();
        } else if(position == 1){
            return new Drinks();
        }else
            return new Cakes();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleTablayouts[position];
    }
}
