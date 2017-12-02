package com.project.pro112.hydrateam.thepolycoffee.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.activity.home.MembershipProgram;
import com.project.pro112.hydrateam.thepolycoffee.activity.home.PurchaseHistory;
import com.project.pro112.hydrateam.thepolycoffee.adapter.AdapterNewsHome;
import com.project.pro112.hydrateam.thepolycoffee.models.ArticleNews;
import com.project.pro112.hydrateam.thepolycoffee.tool.DomParser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment implements View.OnClickListener {

    AdapterNewsHome adapterNewsHome;
    ArrayList<ArticleNews> listNews;

    RecyclerView recyclerViewNews;
    LinearLayoutManager layoutManager;
    ProgressBar progressLoadNews;
    Button btnHistory;
    LinearLayout btnMemberShip;
    ImageView imgAvatar;
    TextView nameUser;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        btnMemberShip = (LinearLayout) view.findViewById(R.id.clickMembership);
        progressLoadNews = (ProgressBar) view.findViewById(R.id.progressLoadNews);
        btnHistory = (Button) view.findViewById(R.id.btnHistory);
        imgAvatar = (ImageView) view.findViewById(R.id.mAvatar);
        nameUser = (TextView) view.findViewById(R.id.mNameUser);
        recyclerViewNews = (RecyclerView) view.findViewById(R.id.recyNew);

        btnMemberShip.setOnClickListener(this);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PurchaseHistory.class);
                startActivity(intent);
            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(user_id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child("fullName").getValue();
                String image = (String) dataSnapshot.child("linkAvatar").getValue();
                nameUser.setText(name);
                Picasso.with(getContext()).load(image).into(imgAvatar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setLayoutManager(layoutManager);

        listNews = new ArrayList<>();
        //Lấy link RSS
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyTask myTask = new MyTask(getActivity());
                myTask.execute("https://dailycoffeenews.com/feed/");
            }
        });

        return view;
    }

    class MyTask extends AsyncTask<String, Integer, String> {

        Context context;

        MyTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            progressLoadNews.setVisibility(View.VISIBLE);

            return docNoiDung(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            DomParser domParser = new DomParser();
            Document document = domParser.getDocument(s);
            try {
                NodeList nodeList = document.getElementsByTagName("item");
                NodeList nodeListdescription = document.getElementsByTagName("description");
                String img = "";
                String title;
                String link;
                String content;
                ArticleNews articleNews;
                for (int i = 0; i < nodeList.getLength(); i++) {
                    String cdata = nodeListdescription.item(i + 1).getTextContent();
                    content = cdata.substring(cdata.indexOf("</div>") + 6);
//                    Log.d("content", content + ".........." + i);
                    Pattern getImg = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                    Matcher matcherImg = getImg.matcher(cdata);
                    if (matcherImg.find()) {
                        img = matcherImg.group(1);
//                        Log.d("image", img + ".........." + i);
                    }
                    Element element = (Element) nodeList.item(i);
                    title = domParser.getValue(element, "title");
                    link = domParser.getValue(element, "link");
//                    Log.d("title", title + ".........." + i);
//                    Log.d("link", link + ".........." + i);

                    articleNews = new ArticleNews(title, link, img, content);
                    listNews.add(articleNews);

                    adapterNewsHome = new AdapterNewsHome(getActivity(), listNews);
                    recyclerViewNews.setAdapter(adapterNewsHome);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            progressLoadNews.setVisibility(View.INVISIBLE);
            super.onPostExecute(s);
        }
    }

    // Doc noi dung RSS
    private String docNoiDung(String theUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(theUrl);

            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setConnectTimeout(10 * 1000);

            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clickMembership:
                Intent intent = new Intent(getActivity(), MembershipProgram.class);
                startActivity(intent);
                break;
        }
    }
}
