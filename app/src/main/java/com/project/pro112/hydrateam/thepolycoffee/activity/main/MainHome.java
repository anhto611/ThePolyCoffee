package com.project.pro112.hydrateam.thepolycoffee.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.fragment.HomeFragment;
import com.project.pro112.hydrateam.thepolycoffee.fragment.LocationGPSFragment;
import com.project.pro112.hydrateam.thepolycoffee.fragment.MusicFragment;
import com.project.pro112.hydrateam.thepolycoffee.fragment.SettingsFragment;
import com.project.pro112.hydrateam.thepolycoffee.fragment.ShoppingFragment;
import com.project.pro112.hydrateam.thepolycoffee.tool.BottomNavigationViewHelper;

public class MainHome extends AppCompatActivity{

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_home);

        //Load FragmentHome khi lần đầu vào.
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHome, new HomeFragment()).commit();

        //Sự kiện click cho Bottom menu
        navigationView = (BottomNavigationView) findViewById(R.id.mBottom_menu);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment;
                Class classFragment = null;
                if (id == R.id.mHome)
                    classFragment = HomeFragment.class;
                if (id == R.id.mShopping)
                    classFragment = ShoppingFragment.class;
                if (id == R.id.mSettings)
                    classFragment = SettingsFragment.class;
                if (id == R.id.mLocation)
                    classFragment = LocationGPSFragment.class;
                if (id == R.id.mMusic)
                    classFragment = MusicFragment.class;
                try {
                    fragment = (Fragment) classFragment.newInstance();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frameLayoutHome, fragment).commit();
                    item.setChecked(true);
                } catch (Exception e) {
                }
                return false;
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
