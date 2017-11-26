package com.project.pro112.hydrateam.thepolycoffee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.SimpleFragmentPagerAdapter;
import com.project.pro112.hydrateam.thepolycoffee.interfaces.CheckButtonViewCartToHideOrShow;

import static com.project.pro112.hydrateam.thepolycoffee.fragment.PopularDish.imHere;

public class Order extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    public static Button btnViewCart;
    private SimpleFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        TextView txtTitle = (TextView) findViewById(R.id.tvTitleToolbar);
        toolbar.setTitle("");
        txtTitle.setText("Order");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    }

    @Override
    public void onPageSelected(int position) {
        //new interface kết với với fragment
        if (position == 0) {
            imHere = true;
            CheckButtonViewCartToHideOrShow fragment = (CheckButtonViewCartToHideOrShow) adapter.instantiateItem(viewPager, position);
            if (fragment != null) {
                if (fragment.getPosition() == 7) {
                    // ngay bottom
                    btnViewCart.setVisibility(View.INVISIBLE);
                } else {
                    btnViewCart.setVisibility(View.VISIBLE);
                }
            }
        } else {
            imHere = true;
            if (position == 1) {
                imHere = false;
            }
            CheckButtonViewCartToHideOrShow fragment2 = (CheckButtonViewCartToHideOrShow) adapter.instantiateItem(viewPager, position);
            if (fragment2 != null) {
                if (fragment2.getPosition() == 5) {
                    // ngay bottom
                    btnViewCart.setVisibility(View.INVISIBLE);

                } else {
                    btnViewCart.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        btnViewCart = (Button) findViewById(R.id.btnViewCart);
        btnViewCart.setOnClickListener(this);
    }

    // view cart click
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Order.this, Cart.class);
        startActivity(intent);
    }
}
