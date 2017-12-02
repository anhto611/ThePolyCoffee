package com.project.pro112.hydrateam.thepolycoffee.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFood;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.tempdatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by VI on 26/11/2017.
 */

public class RecyclerViewAdapterCartProduct extends RecyclerView.Adapter<RecyclerViewAdapterCartProduct.ViewHolder> {
    private Context context;
    private FragmentManager fragmentManager;
    private LayoutInflater layoutInflater;

    public RecyclerViewAdapterCartProduct(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSl, foodPri, foodTotal;
        public ImageView foodImg;
        public TextView foodName, foodDes;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSl = itemView.findViewById(R.id.tvSl);
            foodPri = itemView.findViewById(R.id.foodPri);
            foodName = itemView.findViewById(R.id.foodName);
            foodDes = itemView.findViewById(R.id.foodDes);
            foodImg = itemView.findViewById(R.id.foodImg);
            foodTotal = itemView.findViewById(R.id.foodTotal);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    @Override
    public RecyclerViewAdapterCartProduct.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_cart_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterCartProduct.ViewHolder holder, int position) {
        BinadtaSet(holder, position);
    }

    private void BinadtaSet(final ViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat("#.#");
        tempdatabase tempdatabase = new tempdatabase(context);
        ArrayList<OrderedFood> orderedFoods = tempdatabase.getOrderedFoods();
        holder.foodName.setText(orderedFoods.get(position).getName());
        holder.foodDes.setText(orderedFoods.get(position).getDiscription());
        holder.foodPri.setText(formatter.format(orderedFoods.get(position).getPrice()) + "đ");
        holder.tvSl.setText(""+orderedFoods.get(position).getAmount());
        holder.foodTotal.setText(""+(formatter.format(orderedFoods.get(position).getAmount()*orderedFoods.get(position).getPrice()))+"đ");
        Picasso.with(context).load(orderedFoods.get(position).getImage()).into(holder.foodImg, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        //set lại data ordered
    }

    @Override
    public int getItemCount() {
        tempdatabase tempdatabase = new tempdatabase(context);
        ArrayList<OrderedFood> orderedFoods =  tempdatabase.getOrderedFoods();
        return orderedFoods.size();
    }
}
