package com.project.pro112.hydrateam.thepolycoffee.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.activity.settings.About;
import com.project.pro112.hydrateam.thepolycoffee.activity.settings.Support;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.tempdatabase;
import com.project.pro112.hydrateam.thepolycoffee.userscreen.EditProfileScreen;
import com.project.pro112.hydrateam.thepolycoffee.userscreen.LoginScreen;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    TextView logoApp;
    LinearLayout mAccount, mSupport, mAbout;
    Button btLogout;

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        logoApp = (TextView) view.findViewById(R.id.logoAppSetting);
        //Đổi font của logoApp:
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Lobster-Regular.ttf");
        logoApp.setTypeface(face);

        mAccount = (LinearLayout) view.findViewById(R.id.accountClick);
        mSupport = (LinearLayout) view.findViewById(R.id.supportClick);
        mAbout = (LinearLayout) view.findViewById(R.id.aboutClick);
        btLogout = (Button) view.findViewById(R.id.btnLogout);

        mAccount.setOnClickListener(this);
        mSupport.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        btLogout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        final Intent intent;
        switch (v.getId()) {
            case R.id.btnLogout:
                //logout delete all data tạm
                tempdatabase tempdatabase = new tempdatabase(getContext());
                tempdatabase.deleteAlldata();
                intent = new Intent(getActivity(), LoginScreen.class);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Logout");
                dialog.setMessage("You want to logout?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(intent);
                        //Kết thúc Fragment:
                        getActivity().finish();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.create().show();
                break;
            case R.id.accountClick:
                intent = new Intent(getActivity(), EditProfileScreen.class);
                startActivity(intent);
                break;
            case R.id.supportClick:
                intent = new Intent(getActivity(), Support.class);
                startActivity(intent);
                break;
            case R.id.aboutClick:
                intent = new Intent(getActivity(), About.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
