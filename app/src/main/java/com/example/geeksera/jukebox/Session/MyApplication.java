package com.example.geeksera.jukebox.Session;

import android.Manifest;
import android.app.Application;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.geeksera.jukebox.Objects.Request;
import com.example.geeksera.jukebox.Objects.SongsDetails;
import com.example.geeksera.jukebox.StaticData.Static;

import org.json.JSONArray;
import org.json.JSONObject;


public class MyApplication extends Application {


    RequestQueue mRequestQueue;
    SongsDetails songsDetails;
    Request request;
    String DeviceID;
    String Theme;



    @Override
    public void onCreate() {


        final Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        super.onCreate();
    }

    public String getThemee() {
        return Theme;
    }

    public void setTheme(String theme) {
        Theme = theme;
    }

    public SongsDetails getSongsDetails() {
        return songsDetails;
    }

    public void setSongsDetails(SongsDetails songsDetails) {
        this.songsDetails = songsDetails;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
        DeviceID=DeviceID.replace(":","");
    }

    public void SendRequest(String Funcation) {


        String url = Static.ServerAddress + Funcation;
        url=url.replace(" ","%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(stringRequest);
    }

    public void GetTheme() {


        String url = Static.ServerAddress + "selecTheme";
        url=url.replace(" ","%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {

                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("selecThemeResult");
                            Theme=jsonArray.getJSONObject(0).getString("Name");

                        }catch(Exception a)
                        {

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(stringRequest);
    }

}
