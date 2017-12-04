package com.project.pro112.hydrateam.thepolycoffee.activity.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.project.pro112.hydrateam.thepolycoffee.R;

public class MembershipProgram extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtTitle;
    TextView logoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_program);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.tvTitleToolbar);
        toolbar.setTitle("");
        txtTitle.setText("Membership program");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        logoApp = (TextView) findViewById(R.id.logoAppMem);
        //Đổi font của logoApp:
        Typeface face = Typeface.createFromAsset(this.getAssets(),
                "fonts/Lobster-Regular.ttf");
        logoApp.setTypeface(face);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
