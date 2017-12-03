package com.project.pro112.hydrateam.thepolycoffee.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.RecyclerViewAdapterDrinksandCakes;
import com.project.pro112.hydrateam.thepolycoffee.interfaces.CheckButtonViewCartToHideOrShow;
import com.project.pro112.hydrateam.thepolycoffee.models.Food;

import java.util.ArrayList;

import static com.project.pro112.hydrateam.thepolycoffee.activity.shopping.Order.linearButtonViewCart;

/**
 * A simple {@link Fragment} subclass.
 */
public class Drinks extends Fragment implements CheckButtonViewCartToHideOrShow {

    public Drinks() {

    }

    @SuppressLint("ValidFragment")
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    private int firstVisibleInListview;
    private ArrayList<Food> foods;
    private RecyclerViewAdapterDrinksandCakes mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drinks_and_cakes, container, false);
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        fragmentManager = getFragmentManager();
        setUpRecyclerView();
        hideButtonViewCartDrinks();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpRecyclerView() {
        // không đổi size của card trong content
        mRecyclerView.setHasFixedSize(true);
        foods = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // get data from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        myRef = database.getReference("Foods/Drinks");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("name").getValue();
                    String des = (String) messageSnapshot.child("discription").getValue();
                    String image = (String) messageSnapshot.child("image").getValue();
                    String price = (String) messageSnapshot.child("price").getValue();
                    Food food = new Food(des, image, name, price);
                    foods.add(food);
                }
                RecyclerViewAdapterDrinksandCakes mAdapter = new RecyclerViewAdapterDrinksandCakes(getContext(), fragmentManager, false, foods);
                if(mAdapter!=null) {
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void hideButtonViewCartDrinks() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && linearButtonViewCart.getVisibility() == View.VISIBLE)
                    linearButtonViewCart.setVisibility(View.INVISIBLE);
                else if (dy < 0 && linearButtonViewCart.getVisibility() == View.INVISIBLE)
                    linearButtonViewCart.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getPosition() {
        return mLayoutManager.findLastCompletelyVisibleItemPosition();
    }
}
