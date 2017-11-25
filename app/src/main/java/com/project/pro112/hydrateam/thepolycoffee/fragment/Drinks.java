package com.project.pro112.hydrateam.thepolycoffee.fragment;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.RecyclerViewAdapterDrinksandCakes;
import com.project.pro112.hydrateam.thepolycoffee.interfaces.CheckButtonViewCartToHideOrShow;

/**
 * A simple {@link Fragment} subclass.
 */
public class Drinks extends Fragment implements CheckButtonViewCartToHideOrShow{

    public Drinks() {

    }
    @SuppressLint("ValidFragment")
    private boolean isDrinks;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drinks_and_cakes, container, false);
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        fragmentManager = getFragmentManager();
        setUpRecyclerView();
        hideButtonViewCart();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getContext(), "sit", Toast.LENGTH_SHORT).show();
    }

    private void setUpRecyclerView() {
        // không đổi size của card trong content
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        // chọn adapter
        RecyclerViewAdapterDrinksandCakes mAdapter = new RecyclerViewAdapterDrinksandCakes(getContext(),fragmentManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void hideButtonViewCart() {
        final Button button = (Button) getActivity().findViewById(R.id.btnViewCart);
        if(mLayoutManager.findLastCompletelyVisibleItemPosition() == 5){
            //Its at bottom ..
            button.setVisibility(View.INVISIBLE);
        }else{
            button.setVisibility(View.VISIBLE);
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager mLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                // có data số 5 đổi lại lại số lượng - 1
                if(mLayoutManager.findLastCompletelyVisibleItemPosition() == 5){
                    // ngay bottom
                    button.setVisibility(View.INVISIBLE);
                }else{
                    button.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void checkButtonTohideorShow() {
        final Button button = (Button) getActivity().findViewById(R.id.btnViewCart);
        if(mLayoutManager.findLastCompletelyVisibleItemPosition() == 5){
            //Its at bottom ..
            button.setVisibility(View.INVISIBLE);
        }else{
            button.setVisibility(View.VISIBLE);
        }
    }
}
