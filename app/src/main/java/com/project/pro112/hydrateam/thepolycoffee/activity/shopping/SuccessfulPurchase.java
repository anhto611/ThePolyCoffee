package com.project.pro112.hydrateam.thepolycoffee.activity.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.project.pro112.hydrateam.thepolycoffee.activity.home.PurchaseHistoryViewProDuct;
import com.project.pro112.hydrateam.thepolycoffee.activity.main.MainHome;
import com.project.pro112.hydrateam.thepolycoffee.models.DateAndTotal;

import java.util.ArrayList;

public class SuccessfulPurchase extends AppCompatActivity {

    private Button btnViewOrder, btnBacktohome;
    private ArrayList<DateAndTotal> arrayList;

    private String keyOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_purchase);

        setUpToolbar();
        initView();
        onClickBtn();
        setUpdata();
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtTitle = (TextView) findViewById(R.id.tvTitleToolbar);
        toolbar.setTitle("");
        txtTitle.setText("Successful purchase");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initView() {
        btnBacktohome = (Button) findViewById(R.id.backHome);
        btnViewOrder = (Button) findViewById(R.id.viewOrder);
    }

    private void setUpdata() {
        if (getUserId() != null) {
            arrayList = new ArrayList<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef;
            myRef = database.getReference("Orders/" + getUserId());
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        DateAndTotal dateAndTotal = new DateAndTotal();
                        String dateNe = (String) messageSnapshot.child("Date").getValue();
                        Long total = (Long) messageSnapshot.child("Total").getValue();
                        dateAndTotal.setDateNe(dateNe);
                        dateAndTotal.setTotal(total);
                        dateAndTotal.setKeyOrder(messageSnapshot.getKey() + "");
                        arrayList.add(dateAndTotal);
                    }

                    keyOrder = arrayList.get(arrayList.size() - 1).getKeyOrder();
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

    private void onClickBtn() {
        btnBacktohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessfulPurchase.this, MainHome.class);
                finish();
                startActivity(intent);
            }
        });
        btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessfulPurchase.this, PurchaseHistoryViewProDuct.class);
                intent.putExtra("keyNe", keyOrder + "");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
