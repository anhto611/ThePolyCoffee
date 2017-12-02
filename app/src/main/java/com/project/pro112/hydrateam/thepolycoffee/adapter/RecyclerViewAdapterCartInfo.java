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
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFood;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.tempdatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
        DecimalFormat formatter = new DecimalFormat("#.#");
        tempdatabase tempdatabase = new tempdatabase(context);
        ArrayList<OrderedFood> orderedFoods = tempdatabase.getOrderedFoods();
        Double total = 0.0;
        for (int i = 0; i < orderedFoods.size(); i++) {
            total = (total + orderedFoods.get(i).getPrice()*orderedFoods.get(i).getAmount());
        }
        double totalbf = total;
        if(total < 200000){
            total = total + 10000;
        }else{
            total = total + 0;
        }
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
                holder.title_info.setText("Prices");
                holder.tvInfoLeft.setText("Your order cost");
                holder.tvInfoRight.setText(totalbf+"đ");
                break;
            }
            case 2: {
                holder.title_info.setText("Transportation");
                holder.tvInfoLeft.setText("Transportation");
                if(totalbf>200000){
                    holder.tvInfoRight.setText("0đ");
                }else{
                    holder.tvInfoRight.setText("10000đ");
                }
                break;
            }
            case 3: {
                holder.title_info.setText("");
                holder.tvInfoLeft.setText("Total");
                holder.tvInfoRight.setText(formatter.format(total)+"đ");
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }


}
