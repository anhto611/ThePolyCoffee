package com.project.pro112.hydrateam.thepolycoffee.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.pro112.hydrateam.thepolycoffee.R;

/**
 * Created by VI on 26/11/2017.
 */

public class RecyclerViewAdapterCartInfo extends RecyclerView.Adapter<RecyclerViewAdapterCartInfo.ViewHolder> {
    private Context context;
    private FragmentManager fragmentManager;
    private LayoutInflater layoutInflater;

    public RecyclerViewAdapterCartInfo(Context context, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_info, tvInfoLeft,tvInfoRight;
        CardView cardview;
        public ViewHolder(View itemView) {
            super(itemView);
            title_info = itemView.findViewById(R.id.title_info);
            tvInfoLeft = itemView.findViewById(R.id.tvInfoLeft);
            tvInfoRight = itemView.findViewById(R.id.tvInfoRight);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_cart_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bindSetData(holder, position);
    }

    private void bindSetData(ViewHolder holder, int position) {
        switch (position) {
            case 0: {
                holder.title_info.setText("Discount");
                holder.tvInfoLeft.setText("Do you have a discount code?");
                holder.tvInfoRight.setText("");
                //click vào discount
                holder.cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "click vào discount trong adapter CART INFO", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case 1: {
                holder.title_info.setText("Transportation");
                holder.tvInfoLeft.setText("Transportation");
                holder.tvInfoRight.setText("10.000đ");
                break;
            }
            case 2: {
                holder.title_info.setText("Another Discount");
                holder.tvInfoLeft.setText("New comer");
                holder.tvInfoRight.setText("10000đ");
                break;
            }
            case 3: {
                holder.title_info.setText("");
                holder.tvInfoLeft.setText("Total");
                holder.tvInfoRight.setText("52.000đ");
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }


}
