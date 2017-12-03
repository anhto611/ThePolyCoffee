package com.project.pro112.hydrateam.thepolycoffee.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFireBaseFood;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by VI on 03/12/2017.
 */

public class RecyclerViewAdapterHistoryProduct extends RecyclerView.Adapter<RecyclerViewAdapterHistoryProduct.ViewHolder> {
   private Context context;
   private FragmentManager fragmentManager;
   private ArrayList<OrderedFireBaseFood> orderedFireBaseFoods;
   private LayoutInflater layoutInflater;
    DecimalFormat formatter;
    public RecyclerViewAdapterHistoryProduct(Context context,FragmentManager fragmentManager,
                                             ArrayList<OrderedFireBaseFood> orderedFireBaseFoods ) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.orderedFireBaseFoods = orderedFireBaseFoods;
        formatter = new DecimalFormat("#.#");

    }

    @Override
    public RecyclerViewAdapterHistoryProduct.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_cart_product, parent, false);
        RecyclerViewAdapterHistoryProduct.ViewHolder viewHolder = new RecyclerViewAdapterHistoryProduct.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterHistoryProduct.ViewHolder holder, int position) {
        BinDataSet(position,holder);
    }

    private void BinDataSet(int position, final ViewHolder holder) {
        holder.foodName.setText(orderedFireBaseFoods.get(position).getName());
        holder.foodDes.setText(orderedFireBaseFoods.get(position).getDiscription());
        holder.foodPri.setText(formatter.format(orderedFireBaseFoods.get(position).getPrice())+"đ");
        holder.foodTotal.setText(""+formatter.format((orderedFireBaseFoods.get(position).getAmount()*orderedFireBaseFoods.get(position).getPrice()))+"đ");
        holder.tvSl.setText(""+orderedFireBaseFoods.get(position).getAmount());
        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.with(context).load(orderedFireBaseFoods.get(position).getImage()).into(holder.foodImg, new Callback() {
            @Override
            public void onSuccess() {
                if (holder.progressBar.getVisibility() == View.VISIBLE)
                    holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderedFireBaseFoods.size();
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
}
