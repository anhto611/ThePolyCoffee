package com.project.pro112.hydrateam.thepolycoffee.activity.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.project.pro112.hydrateam.thepolycoffee.R;

public class About extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtTitle, txtParaOne, txtParaTwo, txtParaThree, txtParaFour ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.tvTitleToolbar);
        txtParaOne = (TextView) findViewById(R.id.paragraph_one);
        txtParaTwo = (TextView) findViewById(R.id.paragraph_two);
        txtParaThree = (TextView) findViewById(R.id.paragraph_three);
        txtParaFour = (TextView) findViewById(R.id.paragraph_four);

        toolbar.setTitle("");
        txtTitle.setText("About");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        Typeface face2 = Typeface.createFromAsset(getAssets(),
//                "fonts/JosefinSans-Regular.ttf");
//        txtParaOne.setTypeface(face2);
//        txtParaTwo.setTypeface(face2);
//        txtParaThree.setTypeface(face2);
//        txtParaFour.setTypeface(face2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
