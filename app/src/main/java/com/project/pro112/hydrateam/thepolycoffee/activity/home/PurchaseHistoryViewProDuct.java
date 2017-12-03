package com.project.pro112.hydrateam.thepolycoffee.activity.home;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.RecyclerViewAdapterHistoryProduct;
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFireBaseFood;

import java.util.ArrayList;

public class PurchaseHistoryViewProDuct extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtTitle;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FragmentManager fragmentManager;
    private ArrayList<OrderedFireBaseFood> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history_view_pro_duct);
        initView();
        setUpToolbar();
        setUpdata();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.tvTitleToolbar);
        toolbar.setTitle("");
        txtTitle.setText("Purchase history");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpdata() {
        Intent intent = getIntent();
        String keyNe = intent.getStringExtra("keyNe");
        Toast.makeText(this, keyNe + "", Toast.LENGTH_SHORT).show();

        if (getUserId() != null) {
            arrayList = new ArrayList<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef;
            myRef = database.getReference("Orders/" + getUserId() + "/" + keyNe + "/Foods");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        OrderedFireBaseFood orderedFireBaseFood = new OrderedFireBaseFood();
                        Long amount = (Long) messageSnapshot.child("amount").getValue();
                        String des = (String) messageSnapshot.child("discription").getValue();
                        String image = (String) messageSnapshot.child("image").getValue();
                        String name = (String) messageSnapshot.child("name").getValue();
                        Long price = (Long) messageSnapshot.child("price").getValue();
                        orderedFireBaseFood.setAmount(Integer.parseInt(String.valueOf(amount)));
                        orderedFireBaseFood.setDiscription(des);
                        orderedFireBaseFood.setImage(image);
                        orderedFireBaseFood.setName(name);
                        orderedFireBaseFood.setPrice(price);
                        arrayList.add(orderedFireBaseFood);
                    }

                    RecyclerViewAdapterHistoryProduct recyclerViewAdapterHistoryProduct = new RecyclerViewAdapterHistoryProduct(getBaseContext(),
                            fragmentManager,
                            arrayList
                    );

                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(recyclerViewAdapterHistoryProduct);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
        }
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
        return "" + uid;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        fragmentManager = this.getFragmentManager();
    }
}
