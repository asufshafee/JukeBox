package com.example.geeksera.jukebox;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.geeksera.jukebox.Adapter.SongLibraryAdapter;
import com.example.geeksera.jukebox.Objects.SongsDetails;
import com.example.geeksera.jukebox.Session.MyApplication;
import com.example.geeksera.jukebox.StaticData.Static;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicLibrary extends Fragment {


    private List<SongsDetails> SongDetailsLIst = new ArrayList<>();
    private RecyclerView recyclerView;
    private SongLibraryAdapter mAdapter;
    MyApplication myApplication;
    SwipeRefreshLayout swipeRefreshLayout;

    public MusicLibrary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_library, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayoutSongLibraray);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        myApplication = (MyApplication) getActivity().getApplicationContext();

        mAdapter = new SongLibraryAdapter(SongDetailsLIst, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SendRequestToServer();
            }
        });
        SendRequestToServer();

        return view;
    }


    public void SendRequestToServer() {
        SongDetailsLIst.clear();
        RequestQueue mRequestQueue;
        final Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        final com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        String url = Static.ServerAddress + "GetLibrary/" + myApplication.getThemee();
        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            String json = null;
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("GetLibraryResult");
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
                            swipeRefreshLayout.setRefreshing(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(stringRequest);
    }
}