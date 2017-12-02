package com.project.pro112.hydrateam.thepolycoffee.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.models.Food;
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFood;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.tempdatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.project.pro112.hydrateam.thepolycoffee.activity.shopping.Order.linearButtonViewCart;

/**
 * Created by VI on 25/11/2017.
 */

public class RecyclerViewAdapterDrinksandCakes extends RecyclerView.Adapter<RecyclerViewAdapterDrinksandCakes.ViewHolder> {
    private Context context;
    private FragmentManager fragmentManager;
    private LayoutInflater inflater;
    private boolean isCake;
    private ArrayList<Food> foods;

    public RecyclerViewAdapterDrinksandCakes(Context context, FragmentManager fragmentManager, boolean isCake, ArrayList<Food> foods) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.isCake = isCake;
        this.foods = foods;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView btnPlus, btnSub, foodImg;
        public TextView tvSl, foodPri, foodName, foodDes;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnSub = itemView.findViewById(R.id.btnSub);
            tvSl = itemView.findViewById(R.id.tvSl);
            foodPri = itemView.findViewById(R.id.foodPri);
            foodName = itemView.findViewById(R.id.foodName);
            foodDes = itemView.findViewById(R.id.foodDes);
            foodImg = itemView.findViewById(R.id.foodImg);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    @Override
    public RecyclerViewAdapterDrinksandCakes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_drinks_and_cakes, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterDrinksandCakes.ViewHolder holder, final int position) {
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

    private void bindBtnSubClick(RecyclerViewAdapterDrinksandCakes.ViewHolder holder, int position) {
        //get data từ data tạm
        tempdatabase tempdatabase = new tempdatabase(context);
        // lấy đc data các món người dùng đã chọn
        ArrayList<OrderedFood> orderedFoods = tempdatabase.getOrderedFoods();
        // tiến hành lôi data ra và set lại tvsl và sub button
        if (orderedFoods.size() < 0) {
            //erorr
        } else {
            int sltemp = 1;
            double truePrice = 0;
            int trueAmount = 0;
            for (int i = 0; i < orderedFoods.size(); i++) {
                if (holder.foodName.getText().equals(orderedFoods.get(i).getName())) {
                    sltemp = orderedFoods.get(i).getAmount();
                }
            }
            sltemp--;
            if (sltemp <= 0) {
                // nếu sl == 0 thì ẩn nút sub và sl di và delete food trong database
                tempdatabase.deleteOrderedFood("" + holder.foodName.getText());
                if (holder.tvSl.getVisibility() == View.VISIBLE) {
                    holder.tvSl.setVisibility(View.INVISIBLE);
                }
                if (holder.btnSub.getVisibility() == View.VISIBLE) {
                    holder.btnSub.setVisibility(View.INVISIBLE);
                }
            } else {
                tempdatabase.updateOrderedFood(new OrderedFood(holder.foodDes.getText() + "",
                        foods.get(position).getImage() + "",
                        holder.foodName.getText() + "",
                        Double.parseDouble(holder.foodPri.getText().toString().substring(0,
                                holder.foodPri.getText().toString().length() - 1)),
                        sltemp));
                if (holder.tvSl.getVisibility() == View.INVISIBLE) {
                    holder.tvSl.setVisibility(View.VISIBLE);
                }
                if (holder.btnSub.getVisibility() == View.INVISIBLE) {
                    holder.btnSub.setVisibility(View.VISIBLE);
                }
            }
            // đẵ xử lý xong slemp (số lượng khi click nút sub)
            if (holder.tvSl.getVisibility() == View.VISIBLE) {
                holder.tvSl.setText("" + sltemp);
            }

            // Xu li update nut view cart
            DecimalFormat formatter = new DecimalFormat("#.#");
            LinearLayout linearLayout = linearButtonViewCart;
            TextView price = (TextView) linearLayout.getChildAt(0);
            TextView sl = (TextView) linearLayout.getChildAt(2);
            orderedFoods = tempdatabase.getOrderedFoods();
            for (int i = 0; i < orderedFoods.size(); i++) {
                truePrice = truePrice + (orderedFoods.get(i).getAmount() * orderedFoods.get(i).getPrice());
                trueAmount = trueAmount + orderedFoods.get(i).getAmount();
            }
            price.setText(formatter.format(truePrice) + "đ");
            sl.setText(trueAmount + "");
            if (sl.getVisibility() == View.INVISIBLE && truePrice > 0)
                sl.setVisibility(View.VISIBLE);
            if (price.getVisibility() == View.INVISIBLE && truePrice > 0)
                price.setVisibility(View.VISIBLE);

            if (sl.getVisibility() == View.VISIBLE && truePrice <= 0) {
                sl.setVisibility(View.INVISIBLE);
            }
            if (price.getVisibility() == View.VISIBLE && truePrice <= 0) {
                price.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void bindBtnPlusClick(RecyclerViewAdapterDrinksandCakes.ViewHolder holder, int position) {
        //get data từ data tạm
        tempdatabase tempdatabase = new tempdatabase(context);
        // lấy đc data các món người dùng đã chọn
        ArrayList<OrderedFood> orderedFoods = tempdatabase.getOrderedFoods();
        // tiến hành lôi data ra và set lại tvsl và sub button
        if (orderedFoods.size() < 0) {

        } else {
            int sltemp = 0;
            double truePrice = 0;
            int trueAmount = 0;
            for (int i = 0; i < orderedFoods.size(); i++) {
                if (holder.foodName.getText().equals(orderedFoods.get(i).getName())) {
                    sltemp = orderedFoods.get(i).getAmount();
                }
            }

            sltemp++;
            if (sltemp == 1) {
                tempdatabase.insertFood(new OrderedFood(holder.foodDes.getText() + "",
                        foods.get(position).getImage() + "",
                        holder.foodName.getText() + "",
                        Double.parseDouble(holder.foodPri.getText().toString().substring(0,
                                holder.foodPri.getText().toString().length() - 1)),
                        sltemp));
            } else if (sltemp > 1) {
                tempdatabase.updateOrderedFood(new OrderedFood(holder.foodDes.getText() + "",
                        foods.get(position).getImage() + "",
                        holder.foodName.getText() + "",
                        Double.parseDouble(holder.foodPri.getText().toString().substring(0,
                                holder.foodPri.getText().toString().length() - 1)),
                        sltemp));
            }
            //check xem 2 view btnSub và tvSl có hiện chưa
            if (holder.tvSl.getVisibility() == View.INVISIBLE) {
                holder.tvSl.setVisibility(View.VISIBLE);
            }
            if (holder.btnSub.getVisibility() == View.INVISIBLE) {
                holder.btnSub.setVisibility(View.VISIBLE);
            }

            if (sltemp >= 0) {
                holder.tvSl.setText("" + sltemp);
            } else {
                Toast.makeText(context, "Phát sinh lỗi trong adapter Populardish", Toast.LENGTH_SHORT).show();
            }

            // Xu li update nut view cart
            // khi click thì lôi hết data trong ordered food ra tính toán
            orderedFoods = tempdatabase.getOrderedFoods();
            for (int i = 0; i < orderedFoods.size(); i++) {
                truePrice = truePrice + (orderedFoods.get(i).getAmount() * orderedFoods.get(i).getPrice());
                trueAmount = trueAmount + orderedFoods.get(i).getAmount();
            }
            DecimalFormat formatter = new DecimalFormat("#.#");
            LinearLayout linearLayout = linearButtonViewCart;
            TextView price = (TextView) linearLayout.getChildAt(0);
            TextView sl = (TextView) linearLayout.getChildAt(2);

            price.setText(formatter.format(truePrice) + "đ");
            sl.setText(trueAmount + "");
            if (sl.getVisibility() == View.INVISIBLE) {
                sl.setVisibility(View.VISIBLE);
            }
            if (price.getVisibility() == View.INVISIBLE) {
                price.setVisibility(View.VISIBLE);
            }
        }
    }

    //set data
    private void bindSetData(final RecyclerViewAdapterDrinksandCakes.ViewHolder holder, int position) {
        holder.foodName.setText(foods.get(position).getName());
        holder.foodDes.setText(foods.get(position).getDiscription());
        holder.foodPri.setText(foods.get(position).getPrice() + "đ");
        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.with(context).load(foods.get(position).getImage()).into(holder.foodImg, new Callback() {
            @Override
            public void onSuccess() {
                if (holder.progressBar.getVisibility() == View.VISIBLE)
                    holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        //set lại data ordered
        tempdatabase tempdatabase = new tempdatabase(context);
        // lấy đc data các món người dùng đã chọn
        ArrayList<OrderedFood> orderedFoods = tempdatabase.getOrderedFoods();
        // tiến hành lôi data ra và set lại tvsl và sub button
        for (int i = 0; i < orderedFoods.size(); i++) {
            if (holder.foodName.getText().equals(orderedFoods.get(i).getName())) {
                holder.tvSl.setText(String.valueOf(orderedFoods.get(i).getAmount()));
                if (holder.tvSl.getVisibility() == View.INVISIBLE)
                    holder.tvSl.setVisibility(View.VISIBLE);
                if (holder.btnSub.getVisibility() == View.INVISIBLE)
                    holder.btnSub.setVisibility(View.VISIBLE);
            } else {

            }
        }
    }

    @Override
    public int getItemCount() {
        return (foods.size());
    }


}
