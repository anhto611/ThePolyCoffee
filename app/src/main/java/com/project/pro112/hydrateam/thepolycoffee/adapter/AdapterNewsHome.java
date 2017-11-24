package com.project.pro112.hydrateam.thepolycoffee.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.activity.LoadNews;
import com.project.pro112.hydrateam.thepolycoffee.models.ArticleNews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Huyn_TQT on 11/23/2017.
 */

public class AdapterNewsHome extends RecyclerView.Adapter<AdapterNewsHome.ViewHolder> {

    private Context context;
    private ArrayList<ArticleNews> listNews;

    public AdapterNewsHome(Context context, ArrayList<ArticleNews> listNews) {
        this.context = context;
        this.listNews = listNews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_cardview_new, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ArticleNews articleNews = listNews.get(position);
        holder.titleNews.setText(articleNews.getTitle());
        Picasso.with(context).load(articleNews.getImage()).into(holder.imageNews);
        holder.moreNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoadNews.class);
                intent.putExtra("link", articleNews.getLink());
                context.startActivity(intent);
            }
        });
        holder.cardViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoadNews.class);
                intent.putExtra("link", articleNews.getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageNews;
        TextView titleNews, contentNews;
        Button moreNews;
        CardView cardViewNews;

        public ViewHolder(View itemView) {
            super(itemView);
            imageNews = (ImageView) itemView.findViewById(R.id.imageNews);
            titleNews = (TextView) itemView.findViewById(R.id.titleNews);
            contentNews = (TextView) itemView.findViewById(R.id.contentNews);
            moreNews = (Button) itemView.findViewById(R.id.moreNews);
            cardViewNews = (CardView) itemView.findViewById(R.id.cardViewNews);
        }
    }
}
