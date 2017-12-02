package com.project.pro112.hydrateam.thepolycoffee.activity.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.SimpleFragmentPagerAdapter;
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFood;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.tempdatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Order extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    public static LinearLayout linearButtonViewCart;
    private SimpleFragmentPagerAdapter adapter;
    private TextView price;
    String hinh[] = {"https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/drinks%2FAmericano%20Coffee.jpg?alt=media&token=ce14fdff-6601-47db-bec0-985c048f42c8"
            , "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/drinks%2FEspresso%20Con%20Panna.jpg?alt=media&token=613db236-0236-4f85-bcea-f4a8eb29061c",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/drinks%2FEspresso.jpg?alt=media&token=d13187ab-3df3-41e9-a7b2-e9e381386598",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/drinks%2FHazelnut%20Macchiatob.jpg?alt=media&token=bd502b7c-88a9-4518-8411-d531605f3bea",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/drinks%2FIce%20Caramel%20Macchiato.jpg?alt=media&token=9ca7f048-f2ed-4710-94de-07aa61740ba1",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/drinks%2FIce%20Coffee%20Mocha.jpg?alt=media&token=89370237-b1fe-4661-80b7-b1e0ddb01d49",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/drinks%2FMilk%20Coffee.jpg?alt=media&token=d3726c43-17c6-4adc-af43-b3511faf1882",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/drinks%2FMocha%20Coffee.jpg?alt=media&token=6efc76b7-3a3c-4b78-a9aa-363c8938f913",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/drinks%2FRistretto%20Bianco.jpg?alt=media&token=45cb53df-e92b-43cc-b5fb-e4a6cf25b2e4"};
    String name[] = {"Americano Coffee",
            "Espresso Con Panna",
            "Espresso",
            "Hazelnut Macchiatob",
            "Ice Caramel Macchiato",
            "Ice Coffee Mocha",
            "Milk Coffee",
            "Mocha Coffee",
            "Ristretto Bianco"};

    String des[] = {"Nutrition Facts Per Serving (23 g)",
            "Nutrition Facts Per Serving (43 g)",
            "Nutrition Facts Per Serving (53 g)",
            "Nutrition Facts Per Serving (112 g)",
            "Nutrition Facts Per Serving (25 g)",
            "Nutrition Facts Per Serving (16 g)",
            "Nutrition Facts Per Serving (43 g)",
            "Nutrition Facts Per Serving (53 g)",
            "Nutrition Facts Per Serving (112 g)"};
    String prices[] = {"34000",
            "34000",
            "24000",
            "25000",
            "32000",
            "25000",
            "24000",
            "25000",
            "32000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        setUpToolbar();
        setUpViewPager();
        updateViewcartButton();

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Foods").child("Drinks");
//        for (int i = 0; i < prices.length; i++) {
//            myRef.push().setValue(new Food(des[i],
//                    hinh[i], name[i], prices[i]));
//        }
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Food food = dataSnapshot.child("1").getValue(Food.class);
//                Toast.makeText(Order.this, food.getImage() + "", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
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
        viewPager.setOffscreenPageLimit(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //new interface kết với với fragment
        if (linearButtonViewCart.getVisibility() == View.INVISIBLE)
            linearButtonViewCart.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        linearButtonViewCart = (LinearLayout) findViewById(R.id.btnViewCart);
        linearButtonViewCart.setOnClickListener(this);
        TextView btnS = (TextView) linearButtonViewCart.getChildAt(1);
        price = (TextView) linearButtonViewCart.getChildAt(0);
        btnS.setText("View cart");
    }

    // view cart click
    @Override
    public void onClick(View v) {
        if (price.getVisibility() == View.INVISIBLE) {
            Toast.makeText(this, "Vui lòng chọn ít nhất một món", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Order.this, Cart.class);
            startActivity(intent);
        }
    }

    public void updateViewcartButton() {
        ArrayList<OrderedFood> orderedFoods;
        double truePrice = 0;
        int trueAmount = 0;
        tempdatabase tempdatabase = new tempdatabase(getBaseContext());
        DecimalFormat formatter = new DecimalFormat("#.#");
        LinearLayout linearLayout = linearButtonViewCart;
        TextView price = (TextView) linearLayout.getChildAt(0);
        TextView sl = (TextView) linearLayout.getChildAt(2);

        orderedFoods = tempdatabase.getOrderedFoods();
        for (int i = 0; i < orderedFoods.size(); i++) {
            truePrice = truePrice + (orderedFoods.get(i).getAmount() * orderedFoods.get(i).getPrice());
            trueAmount = trueAmount + orderedFoods.get(i).getAmount();
            Log.e("image ne", orderedFoods.get(i).getImage() + "");
        }

        price.setText(formatter.format(truePrice) + "đ");
        sl.setText(trueAmount + "");
        if (sl.getVisibility() == View.INVISIBLE && truePrice > 0)
            sl.setVisibility(View.VISIBLE);
        if (price.getVisibility() == View.INVISIBLE && truePrice > 0)
            price.setVisibility(View.VISIBLE);

        if (sl.getVisibility() == View.VISIBLE && truePrice <= 0) {
            sl.setVisibility(View.INVISIBLE);
        }
        if (price.getVisibility() == View.VISIBLE && truePrice <= 0) {
            price.setVisibility(View.INVISIBLE);
        }
    }
}
