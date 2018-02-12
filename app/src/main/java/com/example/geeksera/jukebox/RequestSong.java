package com.example.geeksera.jukebox;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.geeksera.jukebox.Session.MyApplication;
import com.example.geeksera.jukebox.StaticData.Static;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestSong extends AppCompatActivity {


    TextView SongName;
    EditText NoOfTokens;
    SongsDetails songsDetails;

    RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_request_token);


        getSupportActionBar().setTitle("Request Song");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        songsDetails = (SongsDetails) bundle.getSerializable("songsDetails");

        SongName = (TextView) findViewById(R.id.SongName);
        NoOfTokens = (EditText) findViewById(R.id.NoOFTokens);

        SongName.setText(songsDetails.getTitle());
        findViewById(R.id.SendRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (NoOfTokens.getText().toString().equals("")) {
                    NoOfTokens.setError("please Fill This Filed");
                    return;
                }


                SendRequestToServer();

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void SendRequestToServer() {

        MyApplication myApplication = (MyApplication) getApplicationContext();
        String Funcation = "RequestSong/" + myApplication.getDeviceID() + "/" + NoOfTokens.getText().toString() + "/" + songsDetails.getS_id();
        SendRequest(Funcation);


    }

    public void SendRequest(String Funcation) {


        String url = Static.ServerAddress + Funcation;
        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);

                            // Created a new Dialog
                            final Dialog dialog = new Dialog(RequestSong.this);
                            // inflate the layout
                            dialog.setContentView(R.layout.message_dialog);
                            // Set the dialog text -- this is better done in the XML
                            final TextView Message = (TextView) dialog.findViewById(R.id.Message);
                            Message.setText(jsonObject.getString("RequestSongResult"));

                            Button Dedicate;
                            Dedicate = (Button) dialog.findViewById(R.id.OK);
                            Dedicate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    dialog.hide();
                                    finish();

                                }
                            });
                            dialog.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
