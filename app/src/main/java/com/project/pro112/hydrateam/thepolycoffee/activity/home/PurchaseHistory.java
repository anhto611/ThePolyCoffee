package com.project.pro112.hydrateam.thepolycoffee.activity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.project.pro112.hydrateam.thepolycoffee.R;

public class PurchaseHistory extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.tvTitleToolbar);

        toolbar.setTitle("");
        txtTitle.setText("Purchase history");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}