package com.example.geeksera.jukebox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.geeksera.jukebox.Adapter.SongLibraryAdapter;
import com.example.geeksera.jukebox.Objects.SongsDetails;
import com.example.geeksera.jukebox.StaticData.Static;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PopularSongActivity extends AppCompatActivity {
    private List<SongsDetails> SongDetailsLIst = new ArrayList<>();
    private RecyclerView recyclerView;
    private SongLibraryAdapter mAdapter;

    RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_song);
        mRequestQueue = Volley.newRequestQueue(getBaseContext());
        recyclerView=(RecyclerView)findViewById(R.id.recycler_viewPopularSong);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
   SongDetailsLIst=new ArrayList<>();
   mAdapter=new SongLibraryAdapter(SongDetailsLIst,PopularSongActivity.this);
   recyclerView.setAdapter(mAdapter);
   SendRequestToServer();
    }
    public void SendRequestToServer() {
        SongDetailsLIst.clear();

        String url = Static.ServerAddress + "GetAllPoopularSongs";
        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            String json = null;
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("GetAllPoopularSongsResult");
                            Gson gson = new Gson();


                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    json = jsonArray.getString(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                SongsDetails songsDetails = new SongsDetails();
                                songsDetails = gson.fromJson(json, SongsDetails.class);
                                SongDetailsLIst.add(songsDetails);

                            }
                            mAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(stringRequest);
    }

}
