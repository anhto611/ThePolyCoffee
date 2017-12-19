package com.project.pro112.hydrateam.thepolycoffee.activity.home;

import android.app.FragmentManager;
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
import com.project.pro112.hydrateam.thepolycoffee.adapter.HistoryListDateAdapter;
import com.project.pro112.hydrateam.thepolycoffee.models.DateAndTotal;

import java.util.ArrayList;

public class PurchaseHistory extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtTitle;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FragmentManager fragmentManager;
    private ArrayList<DateAndTotal> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        initView();
        setUpToolbar();
        setUpdata();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                    ArrayList<DateAndTotal> listHistory = new ArrayList<>(arrayList.size());
                    for (int i = arrayList.size() - 1; i >= 0; i--) {
                        listHistory.add(arrayList.get(i));
                    }
                    HistoryListDateAdapter historyListDateAdapter = new HistoryListDateAdapter(getBaseContext(), fragmentManager, listHistory);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(historyListDateAdapter);
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