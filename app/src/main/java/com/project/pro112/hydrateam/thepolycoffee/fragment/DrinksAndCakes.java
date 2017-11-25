package com.project.pro112.hydrateam.thepolycoffee.fragment;


import android.annotation.SuppressLint;
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
import com.project.pro112.hydrateam.thepolycoffee.adapter.RecyclerViewAdapterD;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrinksAndCakes extends Fragment {

    public DrinksAndCakes() {

    }
    @SuppressLint("ValidFragment")
    public DrinksAndCakes(boolean isDrinks) {
        // phân biệt giữa drinks và cakes nếu "true" thì đổ data drinks vào
        this.isDrinks = isDrinks;
    }
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

    private void setUpRecyclerView() {
        // không đổi size của card trong content
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        // chọn adapter
        RecyclerViewAdapterD mAdapter = new RecyclerViewAdapterD(getContext(),fragmentManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void hideButtonViewCart() {
        final Button button = (Button) getActivity().findViewById(R.id.btnViewCart);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    button.setVisibility(View.INVISIBLE);
                }else{
                    button.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
