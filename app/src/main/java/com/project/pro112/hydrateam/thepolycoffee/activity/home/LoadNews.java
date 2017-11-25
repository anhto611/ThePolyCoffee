package com.project.pro112.hydrateam.thepolycoffee.activity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.project.pro112.hydrateam.thepolycoffee.R;

public class LoadNews extends AppCompatActivity {

    WebView webViewNews;
    ImageButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_load_news);

        webViewNews = (WebView) findViewById(R.id.webViewNews);
        btnClose = (ImageButton) findViewById(R.id.btnCloseNews);

        String link = getIntent().getStringExtra("link");
        webViewNews.loadUrl(link);
        webViewNews.setWebChromeClient(new WebChromeClient());

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
