package com.project.pro112.hydrateam.thepolycoffee.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.pro112.hydrateam.thepolycoffee.R;

/**
 * Created by VI on 25/11/2017.
 */

public class RecyclerViewAdapterPolularDish extends RecyclerView.Adapter<RecyclerViewAdapterPolularDish.ViewHolder> {
    private Context context;
    private FragmentManager fragmentManager;
    private LayoutInflater inflater;

    public RecyclerViewAdapterPolularDish(Context context, FragmentManager fragmentManager) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerViewAdapterPolularDish.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_popular_dish, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterPolularDish.ViewHolder holder, int position) {
        //hàm set data cho card view
        bindSetData(holder);
    }

    private void bindSetData(RecyclerViewAdapterPolularDish.ViewHolder holder) {
    }

    @Override
    public int getItemCount() {
        return 8;
    }


}
