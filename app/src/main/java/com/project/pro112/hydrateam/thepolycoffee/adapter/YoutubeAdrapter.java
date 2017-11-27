package com.project.pro112.hydrateam.thepolycoffee.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.activity.video.PlayVideoYoutube;
import com.project.pro112.hydrateam.thepolycoffee.models.VideoYoutube;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Huyn_TQT on 11/27/2017.
 */

public class YoutubeAdrapter extends RecyclerView.Adapter<YoutubeAdrapter.ViewHolder> {

    private Context context;
    private List<VideoYoutube> youtubeList;

    public YoutubeAdrapter(Context context, List<VideoYoutube> youtubeList) {
        this.context = context;
        this.youtubeList = youtubeList;
    }

    @Override
    public YoutubeAdrapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_video_youtube, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YoutubeAdrapter.ViewHolder holder, int position) {
        final VideoYoutube youtube = youtubeList.get(position);
        holder.titleVideo.setText(youtube.getmTitleVideo());
        Picasso.with(context).load(youtube.getmThumbnailsVideo()).into(holder.imgVideo);
        holder.cardViewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayVideoYoutube.class);
                intent.putExtra("idvideo",youtube.getmIDVideo());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return youtubeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgVideo;
        TextView titleVideo;
        CardView cardViewVideo;

        public ViewHolder(View itemView) {
            super(itemView);
            titleVideo = (TextView) itemView.findViewById(R.id.titleVideo);
            imgVideo = (ImageView) itemView.findViewById(R.id.imageVideo);
            cardViewVideo = (CardView) itemView.findViewById(R.id.cardViewVideo);
        }
    }
}
