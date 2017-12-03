
package com.project.pro112.hydrateam.thepolycoffee.activity.shopping;

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
    String hinh[] = {"https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2FChilly%20juice.jpg?alt=media&token=3a0f2533-2e9c-4d18-9ce2-92969b02ff76"
            ,"https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2FChocolate%20cup.jpg?alt=media&token=8fce5e19-0d1e-41a0-a6ca-deae1a5a1b65" ,
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2FChocolate%20milk.jpg?alt=media&token=49c53562-3359-4827-8bd3-379d79e78329",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2FGreen%20tea.jpg?alt=media&token=ab330591-392e-49c9-98fb-10d546443705" ,
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2FHoney%20lovely.jpg?alt=media&token=02af8fe4-55e4-41ef-b3f2-21ec795ee53c",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2Flovely%20cake.jpg?alt=media&token=a64e6103-6581-4ee0-ab4e-524c06731185"};
    String name[] = {"Chilly juice",
            " Chocolate cup" ,
            "Chocolate cup" ,
            "Green tea" ,
            "Honey lovely" ,
            "lovely cake"};

    String des[]  = {"Nutrition Facts Per Serving (23 g)",
            "Nutrition Facts Per Serving (43 g)" ,
            "Nutrition Facts Per Serving (53 g)" ,
            "Nutrition Facts Per Serving (112 g)" ,
            "Nutrition Facts Per Serving (25 g)" ,
            "Nutrition Facts Per Serving (16 g)"};
    String prices[] = {"34000" ,
            "34000" ,
            "24000" ,
            "25000" ,
            "32000",
            "25000"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        setUpToolbar();
        setUpViewPager();
        updateViewcartButton();
    }
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Foods").child("Popular");
//        for (int i = 0; i < prices.length; i++) {
//            myRef.push().setValue(new Food(des[i],
//                    hinh[i], name[i], prices[i]));
//        }
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Food food = dataSnapshot  .child("1").getValue(Food.class);
//                Toast.makeText(Order.this, food.getImage()+"", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

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
        if(linearButtonViewCart.getVisibility() == View.INVISIBLE)
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
        if(price.getVisibility() == View.INVISIBLE){
            Toast.makeText(this, "Vui lòng chọn ít nhất một món", Toast.LENGTH_SHORT).show();
        }else {
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
