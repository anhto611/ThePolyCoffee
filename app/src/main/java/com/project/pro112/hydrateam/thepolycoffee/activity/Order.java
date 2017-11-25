package com.project.pro112.hydrateam.thepolycoffee.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.SimpleFragmentPagerAdapter;
import com.project.pro112.hydrateam.thepolycoffee.interfaces.CheckButtonViewCartToHideOrShow;

public class Order extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    private Button btnViewCart;
    private SimpleFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        setUpToolbar();
        setUpViewPager();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Order");
    }

        private void setUpViewPager() {
        adapter =
                new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //app su kien chuyen page
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        CheckButtonViewCartToHideOrShow fragment = (CheckButtonViewCartToHideOrShow) adapter.instantiateItem(viewPager, position);
        if (fragment != null) {
            fragment.checkButtonTohideorShow();
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        btnViewCart = (Button) findViewById(R.id.btnViewCart);
    }
}
