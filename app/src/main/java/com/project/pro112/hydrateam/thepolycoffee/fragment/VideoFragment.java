package com.project.pro112.hydrateam.thepolycoffee.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.YoutubeAdrapter;
import com.project.pro112.hydrateam.thepolycoffee.models.VideoYoutube;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoFragment extends Fragment {

    RecyclerView recyclerViewVideo;
    ArrayList<VideoYoutube> videoYoutubes;
    YoutubeAdrapter youtubeAdrapter;
    LinearLayoutManager layoutManager;

    public static String API_KEY_YOUTUBE = "AIzaSyBaPYvB9YzMguH4WD5l4qzvqtlrwmJUkII";
    String ID_PLAYLIST = "PL52-tNn6o9AJpjVbbtbL58UiVz1IIWHZ2";

    String urlGetJson = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + ID_PLAYLIST + "&key=" + API_KEY_YOUTUBE + "&maxResults=50";

    public VideoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerViewVideo = (RecyclerView) view.findViewById(R.id.recyViewVideo);
        recyclerViewVideo.setHasFixedSize(true);
        recyclerViewVideo.setLayoutManager(layoutManager);
        videoYoutubes = new ArrayList<>();

        youtubeAdrapter = new YoutubeAdrapter(getActivity(), videoYoutubes);
        recyclerViewVideo.setAdapter(youtubeAdrapter);

        GetJsonYoutube(urlGetJson);

        return view;
    }


    private void GetJsonYoutube(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems = response.getJSONArray("items");

                    String title = "";
                    String linkImg = "";
                    String idVideo = "";
                    for (int i = 0; i< jsonItems.length(); i++){
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");

                        //Get title video
                        title = jsonSnippet.getString("title");

                        //Get img video
                        JSONObject jsonThumbnails = jsonSnippet.getJSONObject("thumbnails");
                        JSONObject jsonThumbnailsHigh = jsonThumbnails.getJSONObject("high");
                        linkImg = jsonThumbnailsHigh.getString("url");

                        //Get id video
                        JSONObject jsonResourceId = jsonSnippet.getJSONObject("resourceId");
                        idVideo = jsonResourceId.getString("videoId");

                        videoYoutubes.add(new VideoYoutube(title, linkImg, idVideo));
                    }
                    youtubeAdrapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
