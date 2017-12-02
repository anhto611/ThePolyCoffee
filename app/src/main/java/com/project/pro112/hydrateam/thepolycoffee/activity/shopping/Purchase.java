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
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFood;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.tempdatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.project.pro112.hydrateam.thepolycoffee.activity.shopping.Order.linearButtonViewCart;

public class Purchase extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private ScrollView scrollView;
    private Button order;
    private GoogleMap mMap;
    private TextView total;

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
    }


    //init
    private void initView() {
        order = (Button) findViewById(R.id.btnS);
        order.setText("Order");
        order.setOnClickListener(this);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        total = (TextView) findViewById(R.id.total);
    }

    public void setTotal() {
        LinearLayout linearLayout = linearButtonViewCart;
        TextView tv = (TextView) linearLayout.getChildAt(0);
        Double totalp = Double.parseDouble(tv.getText().toString().substring(0, tv.getText().toString().length() - 1));
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
        ArrayList<OrderedFood> orderedFoods = tempdatabase.getOrderedFoods();
        Toast.makeText(this, "nut purchase kich hoat....Delete all trash data", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < orderedFoods.size(); i++) {
            Toast.makeText(this, "Day len fire base: "+orderedFoods.get(i).getName(), Toast.LENGTH_SHORT).show();
        }
        tempdatabase.deleteAlldata();
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
}
