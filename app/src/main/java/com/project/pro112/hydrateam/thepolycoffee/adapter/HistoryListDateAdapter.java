package com.project.pro112.hydrateam.thepolycoffee.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.activity.home.PurchaseHistoryViewProDuct;
import com.project.pro112.hydrateam.thepolycoffee.models.DateAndTotal;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by VI on 03/12/2017.
 */

public class HistoryListDateAdapter extends RecyclerView.Adapter<HistoryListDateAdapter.ViewHolder> {
    private Context context;
    private FragmentManager fragmentManager;
    private LayoutInflater layoutInflater;
    private ArrayList<DateAndTotal> dateAndTotals;
    DecimalFormat formatter;
    public HistoryListDateAdapter(Context context, FragmentManager fragmentManager, ArrayList<DateAndTotal> dateAndTotals) {
        this.dateAndTotals = dateAndTotals;
        this.context = context;
        this.fragmentManager = fragmentManager;
        formatter = new DecimalFormat("#.#");

    }

    @Override
    public HistoryListDateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_history_date, parent, false);
        HistoryListDateAdapter.ViewHolder viewHolder = new HistoryListDateAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HistoryListDateAdapter.ViewHolder holder, final int position) {
        onBindDataSet(holder, position);
        final int positionTam = position;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewOnClick(positionTam,holder);
            }
        });
    }

    private void onBindDataSet(ViewHolder holder, int position) {
        holder.tvTotal.setText(String.valueOf(formatter.format(dateAndTotals.get(position).getTotal()))+"đ");
        holder.tvDate.setText(String.valueOf(dateAndTotals.get(position).getDateNe()));
    }

    private void cardViewOnClick(int position, ViewHolder holder) {
        //get key and start activ
        Intent intent = new Intent(context, PurchaseHistoryViewProDuct.class);
        intent.putExtra("keyNe", dateAndTotals.get(position).getKeyOrder() + "");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return dateAndTotals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTotal, tvDate;
        public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvdate);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
