package com.project.pro112.hydrateam.thepolycoffee.activity.shopping;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFood;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.tempdatabase;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.project.pro112.hydrateam.thepolycoffee.activity.shopping.Order.linearButtonViewCart;

public class Purchase extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private ScrollView scrollView;
    private Button order;
    private GoogleMap mMap;
    private TextView total;
    private boolean isPushDataDone;
    private Double totalp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        initView();
        setUpToolbar();
        //như bên cart
        setUpHideButtonWhenSrollToTheBottom();
        setTotal();
        setUpMaps();
//        Toast.makeText(this, "User id: "+getUserId()+"", Toast.LENGTH_SHORT).show();
//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//        Toast.makeText(this, "Date: "+currentDateTimeString, Toast.LENGTH_SHORT).show();
    }

    private String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid;
        if (user != null) {
            // User is signed in
            uid = user.getUid();
        } else {
            // No user is signed in
            uid = null;
        }
        return ""+uid;
    }
    //init
    private void initView() {
        order = (Button) findViewById(R.id.btnS);
        order.setText("Order");
        order.setOnClickListener(this);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        total = (TextView) findViewById(R.id.total);
        isPushDataDone = false;
    }

    public void setTotal() {
        LinearLayout linearLayout = linearButtonViewCart;
        TextView tv = (TextView) linearLayout.getChildAt(0);
        totalp = Double.parseDouble(tv.getText().toString().substring(0, tv.getText().toString().length() - 1));
        if(totalp > 200000){

        }else{
            totalp = totalp + 10000;
        }
        DecimalFormat formatter = new DecimalFormat("#.#");
        total.setText(formatter.format(totalp)+"đ");
    }
    //Setup tool bar
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
        txtTitle.setText("Information and purchase");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpHideButtonWhenSrollToTheBottom() {
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View child = scrollView.getChildAt(0);
                if (child != null) {
                    int childHeight = child.getHeight();
                    if (scrollView.getHeight() < childHeight) {
                        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                            @Override
                            public void onScrollChanged() {
                                if (scrollView != null) {
                                    if (scrollView.getChildAt(0).getBottom() == (scrollView.getHeight() + scrollView.getScrollY())) {
                                        order.setVisibility(View.INVISIBLE);
                                    } else {
                                        order.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });
                    } else {
                        order.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    // click vào nút order
    @Override
    public void onClick(View v) {
        tempdatabase tempdatabase = new tempdatabase(getBaseContext());
        PushDataToFireBase();
        if(isPushDataDone) {
            tempdatabase.deleteAlldata();
            Toast.makeText(this, "Mua hàng hành công!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Đã xảy ra lỗi, mua hàng thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    //Set up maps
    public void setUpMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
    }

    private void PushDataToFireBase() {
        tempdatabase tempdatabase = new tempdatabase(getBaseContext());
        ArrayList<OrderedFood> orderedFoods = tempdatabase.getOrderedFoods();
        if(getUserId() !=null && orderedFoods.size() > 0) {
            // get firebase instance
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef;
            myRef = database.getReference();
            myRef.child("Orders").child(""+getUserId());
            Map myMapFoods = new HashMap();
            if(orderedFoods.size() >= 0) {
                for (int i = 0; i < orderedFoods.size(); i++) {
                    OrderedFood food = new OrderedFood(orderedFoods.get(i).getDiscription()+"",
                            ""+orderedFoods.get(i).getImage(),
                            ""+orderedFoods.get(i).getName(),
                            orderedFoods.get(i).getPrice(),
                            orderedFoods.get(i).getAmount());
                    myMapFoods.put("Food"+(i+1), food);
                }
            }else{
                Toast.makeText(this, "Vui lòng chọn món!", Toast.LENGTH_SHORT).show();
            }

            Map mParent = new HashMap();
            mParent.put("Date", ""+getCurrentTime());
            mParent.put("Total", totalp);
            mParent.put("Foods", myMapFoods);
            myRef.child("Orders").child(""+getUserId()).push().setValue(mParent);
        }else{
            Toast.makeText(this, "Vui lòng chọn ít nhất 1 món!", Toast.LENGTH_SHORT).show();
        }
        isPushDataDone = true;
    }
    private String getCurrentTime() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }
}
