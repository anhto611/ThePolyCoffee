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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.SimpleFragmentPagerAdapter;

public class Order extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    public static LinearLayout linearButtonViewCart;
    private SimpleFragmentPagerAdapter adapter;
    private TextView price;
    String hinh[] = {"https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FBlueberry%20Muffin.jpg?alt=media&token=0a5ca0e2-76e1-424b-82e0-56868018c508"
    ,"https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FChocolate%20Chunk.jpg?alt=media&token=b828477f-0013-40a5-9348-1ee43ab2df92" ,
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FChocolate%20Croissant.jpg?alt=media&token=db787346-6c75-4421-9e76-e305f7f11366",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FChocolate%20Marble.jpg?alt=media&token=60050903-f6be-4e65-866b-e63541444461" ,
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FChonga%20Bagel.jpg?alt=media&token=b0a1442a-435a-4639-b1a0-9848b4386774",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FIce%20Cream%20Cone.jpg?alt=media&token=852ace4c-419b-4909-a1b7-a8739540e83f" ,
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FNanana%20Nut%20Bread.jpg?alt=media&token=129426a5-4ff6-4904-ae89-99ef8e3c95bf" ,
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FPeanut%20butter%20cookie.jpg?alt=media&token=c3d76617-bc53-4a82-ae45-692ede76648d" ,
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FPolar%20Bear%20Cake.jpg?alt=media&token=caa2c8ed-20f5-4a23-847f-02279abeee02" ,
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FReindeer%20Ginbread.jpg?alt=media&token=7758b6a3-42e3-4fcc-8ac5-85749cfe6b12",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/cakes%2FSnowman%20Cookie.jpg?alt=media&token=ffda9035-7587-4f53-ad07-9e09a9813714" };
    String name[] = {"Blueberry Muffin",
    "Chocolate Chunk" ,
            "Chocolate Croissant" ,
            " Chocolate Marble" ,
            "Chonga Bagel" ,
            " Ice Cream Cone" ,
            "Nanana Nut Bread" ,
            "Peanut butter cookie" ,
            "Polar Bear Cake",
            "Reindeer Ginbread" ,
            "Snowman Cookie"};

    String des[]  = {"Nutrition Facts Per Serving (63 g)",
            "Nutrition Facts Per Serving (23 g)" ,
            "Nutrition Facts Per Serving (51 g)" ,
            "Nutrition Facts Per Serving (92 g)" ,
            "Nutrition Facts Per Serving (73 g)" ,
            "Nutrition Facts Per Serving (66 g)" ,
            "Nutrition Facts Per Serving (123 g)" ,
            "Nutrition Facts Per Serving (43 g)" ,
            "Nutrition Facts Per Serving (153 g)" ,
            "Nutrition Facts Per Serving (123 g)" ,
            "Nutrition Facts Per Serving (18 g)"};
    String prices[] = {"34000" ,
            "34000" ,
            "24000" ,
            "25000" ,
            "14000" ,
            "51000" ,
            "37000" ,
            "13000" ,
            "52000" ,
            "43000" ,
            "34000"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        setUpToolbar();
        setUpViewPager();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Foods").child("Cakes");
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
}
