package com.project.pro112.hydrateam.thepolycoffee.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.tool.RoundedTransformation;

public class HomeFragment extends Fragment {

    private RoundedTransformation transformation;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        transformation = new RoundedTransformation();
        ImageView img = (ImageView) view.findViewById(R.id.mAvatar);
        transformation.setImageByPicasso(R.drawable.avatar_demo , img);


        return view;
    }

}
