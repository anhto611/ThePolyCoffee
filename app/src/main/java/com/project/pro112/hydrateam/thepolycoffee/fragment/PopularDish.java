package com.project.pro112.hydrateam.thepolycoffee.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.RecyclerViewAdapterPolularDish;
import com.project.pro112.hydrateam.thepolycoffee.interfaces.CheckButtonViewCartToHideOrShow;
import com.project.pro112.hydrateam.thepolycoffee.tool.SpaceBetweenGrid;

import static com.project.pro112.hydrateam.thepolycoffee.activity.Order.btnViewCart;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularDish extends Fragment implements CheckButtonViewCartToHideOrShow{


    public PopularDish() {
        // Required empty public constructor
    }
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    private int numberOfColums = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular_dish, container, false);
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        fragmentManager = getFragmentManager();
        setUpRecyclerView();
        hideButtonViewCart();
        return view;
    }

    private void setUpRecyclerView() {
        // không đổi size của card trong content
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getContext(),numberOfColums);

        mRecyclerView.setLayoutManager(mLayoutManager);

        // chọn adapter
        RecyclerViewAdapterPolularDish mAdapter = new RecyclerViewAdapterPolularDish(getContext(),fragmentManager);
        mRecyclerView.setAdapter(mAdapter);

        int spanCount = 2; // 2 columns
        int spacing = 20; // 20px
        boolean includeEdge = true;
        mRecyclerView.addItemDecoration(new SpaceBetweenGrid(spanCount, spacing, includeEdge));
    }

    private void hideButtonViewCart() {
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager mLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                // có data số 7 đổi lại lại số lượng - 1
                if(mLayoutManager.findLastCompletelyVisibleItemPosition() == 7){
                    // ngay bottom
                    btnViewCart.setVisibility(View.INVISIBLE);
                }else{
                    btnViewCart.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    @Override
    public void checkButtonTohideorShow() {
//        hideButtonViewCart();
        if(mLayoutManager.findLastCompletelyVisibleItemPosition() == 7 && btnViewCart.getVisibility() == View.VISIBLE){
            //Its at bottom ..
            btnViewCart.setVisibility(View.INVISIBLE);
        }else if(btnViewCart.getVisibility() == View.INVISIBLE){
            btnViewCart.setVisibility(View.VISIBLE);
        }
    }
}
