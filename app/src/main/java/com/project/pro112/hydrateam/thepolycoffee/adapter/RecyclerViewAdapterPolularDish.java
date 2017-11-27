package com.project.pro112.hydrateam.thepolycoffee.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.pro112.hydrateam.thepolycoffee.R;

import java.text.DecimalFormat;

import static com.project.pro112.hydrateam.thepolycoffee.activity.shopping.Order.linearButtonViewCart;

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
        public ImageView btnPlus, btnSub;
        public TextView tvSl,foodPri;
        public ViewHolder(View itemView) {
            super(itemView);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnSub = itemView.findViewById(R.id.btnSub);
            tvSl = itemView.findViewById(R.id.tvSl);
            foodPri = itemView.findViewById(R.id.foodPri);
        }
    }

    @Override
    public RecyclerViewAdapterPolularDish.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_popular_dish, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterPolularDish.ViewHolder holder, final int position) {
        //hàm set data cho card view
        bindSetData(holder, position);
        //Nút cộng được nhấn
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindBtnPlusClick(holder, position);
            }
        });
        // Nút trừ được nhấn
        holder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindBtnSubClick(holder, position);
            }
        });

    }

    private void bindBtnSubClick(ViewHolder holder, int position) {
        //check xem 2 view btnSub và tvSl có hiện chưa
        int soLuong = Integer.parseInt(String.valueOf(holder.tvSl.getText()));
        if (soLuong > 0) {
            soLuong--;
            if(soLuong == 0){
                holder.tvSl.setVisibility(View.INVISIBLE);
                holder.btnSub.setVisibility(View.INVISIBLE);
            }
            holder.tvSl.setText("" + soLuong);
        }

        // Xu li update nut view cart
        DecimalFormat formatter = new DecimalFormat("#.#");
        LinearLayout linearLayout = linearButtonViewCart;
        TextView price = (TextView) linearLayout.getChildAt(0);
        TextView sl = (TextView) linearLayout.getChildAt(2);
        double priceaf;
        int slaf;
        if (price.getText().toString().equals("")) {
            priceaf = 0;
            slaf = 0;
        } else {
            priceaf = Double.parseDouble(price.getText().toString().substring(0, price.getText().toString().length() - 1));
            slaf = Integer.parseInt(sl.getText().toString());
        }
        double foodPri = Double.parseDouble(holder.foodPri.getText().toString().substring(0, holder.foodPri.getText().toString().length() - 1));
        double total = 0;
        if (priceaf >= foodPri) {
            total = priceaf - foodPri;
            slaf--;
        }
        price.setText(formatter.format(total) + "đ");
        sl.setText(slaf + "");

        if (sl.getVisibility() == View.VISIBLE && slaf == 0) {
            sl.setVisibility(View.INVISIBLE);
        }
        if (price.getVisibility() == View.VISIBLE && slaf == 0) {
            price.setVisibility(View.INVISIBLE);

        }
    }

    private void bindBtnPlusClick(ViewHolder holder, int position) {
        //check xem 2 view btnSub và tvSl có hiện chưa
        if(holder.tvSl.getVisibility() == View.INVISIBLE){
            holder.tvSl.setVisibility(View.VISIBLE);
        }
        if(holder.btnSub.getVisibility() == View.INVISIBLE){
            holder.btnSub.setVisibility(View.VISIBLE);
        }
        int soLuong =  Integer.parseInt(String.valueOf(holder.tvSl.getText()));

        if(soLuong >= 0){
            soLuong++;
            holder.tvSl.setText(""+soLuong);
        }else{
            Toast.makeText(context, "Phát sinh lỗi trong adapter Populardish", Toast.LENGTH_SHORT).show();
        }

        // Xu li update nut view cart
        DecimalFormat formatter = new DecimalFormat("#.#");
        LinearLayout linearLayout = linearButtonViewCart;
        TextView price = (TextView) linearLayout.getChildAt(0);
        TextView sl = (TextView) linearLayout.getChildAt(2);
        double priceaf;
        int slaf;
        if(price.getText().toString().equals("")){
            priceaf = 0;
            slaf = 0;
        }else {
            priceaf = Double.parseDouble(price.getText().toString().substring(0, price.getText().toString().length() - 1));
            slaf = Integer.parseInt(sl.getText().toString());
        }
        double foodPri = Double.parseDouble(holder.foodPri.getText().toString().substring(0,holder.foodPri.getText().toString().length() - 1));


        double total = foodPri + priceaf;
        slaf++;
        price.setText(formatter.format(total)+"đ");
        sl.setText(slaf+"");
        if(sl.getVisibility() == View.INVISIBLE){
            sl.setVisibility(View.VISIBLE);
        }
        if(price.getVisibility() == View.INVISIBLE){
            price.setVisibility(View.VISIBLE);
        }
    }

    private void bindSetData(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }


}
