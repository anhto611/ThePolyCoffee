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

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.RecyclerViewAdapterDrinksandCakes;
import com.project.pro112.hydrateam.thepolycoffee.interfaces.CheckButtonViewCartToHideOrShow;

import static com.project.pro112.hydrateam.thepolycoffee.activity.Order.btnViewCart;
import static com.project.pro112.hydrateam.thepolycoffee.fragment.PopularDish.imHere;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cakes extends Fragment implements CheckButtonViewCartToHideOrShow {


    @SuppressLint("ValidFragment")
    private boolean isDrinks;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    private boolean imHere2;

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
        RecyclerViewAdapterDrinksandCakes mAdapter = new RecyclerViewAdapterDrinksandCakes(getContext(), fragmentManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void hideButtonViewCart() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if ((mLayoutManager.findLastCompletelyVisibleItemPosition() == 5) && btnViewCart.getVisibility() == View.VISIBLE) {
                    //Its at bottom ..
                    btnViewCart.setVisibility(View.INVISIBLE);
                } else if (btnViewCart.getVisibility() == View.INVISIBLE && imHere == true) {
                    btnViewCart.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getPosition() {
        return mLayoutManager.findLastCompletelyVisibleItemPosition();
    }

    @Override
    public boolean isLastItemVisible() {
        if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mLayoutManager.getItemCount())
            return true;
        else
            return false;
    }
}
