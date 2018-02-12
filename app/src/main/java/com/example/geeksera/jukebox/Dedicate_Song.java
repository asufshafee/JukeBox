package com.example.geeksera.jukebox;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.example.geeksera.jukebox.Objects.SongsDetails;
import com.example.geeksera.jukebox.Session.MyApplication;
import com.example.geeksera.jukebox.StaticData.Static;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dedicate_Song extends AppCompatActivity {


    TextView SongName;
    EditText DedicatedBy, DedicatedTo, Message, NoOFTokens;
    RadioButton Public, Private;
    String Type;

    RequestQueue mRequestQueue;

    SongsDetails songsDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_request__details);


        final Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();



        Bundle bundle = getIntent().getBundleExtra("bundle");
        songsDetails = (SongsDetails) bundle.getSerializable("songsDetails");

        SongName = (TextView) findViewById(R.id.SongName);

        DedicatedBy = (EditText) findViewById(R.id.DedicatedBy);
        DedicatedTo = (EditText) findViewById(R.id.DedicatedTo);
        Message = (EditText) findViewById(R.id.Message);
        NoOFTokens = (EditText) findViewById(R.id.NoOFTokens);

        Public = (RadioButton) findViewById(R.id.Public);
        Private = (RadioButton) findViewById(R.id.Privete);


        findViewById(R.id.SendRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DedicatedBy.getText().toString().equals("")) {
                    DedicatedBy.setError("Please Fill This Filed");
                    return;
                }
                if (DedicatedTo.getText().toString().equals("")) {
                    DedicatedTo.setError("Please Fill This Filed");
                    return;
                }
                if (Message.getText().toString().equals("")) {
                    Message.setError("Please Fill This Filed");
                    return;
                }
                if (NoOFTokens.getText().toString().equals("")) {
                    NoOFTokens.setError("Please Fill This Filed");
                    return;
                }

                if (Public.isChecked()) {
                    Type = "Public";
                } else {
                    Type = "Private";
                }

                SendRequestToServer();


            }
        });

        getSupportActionBar().setTitle("Dedicate Song");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    public void SendRequestToServer() {
        MyApplication myApplication = (MyApplication) getApplicationContext();
        String Funcation = "insertDedications/" + DedicatedBy.getText().toString() + "/" + DedicatedTo.getText().toString() + "/" + Type + "/" +
                songsDetails.getS_id() + "/" + Message.getText().toString() + "/" + myApplication.getDeviceID()+"/"+NoOFTokens.getText().toString();
        SendRequest(Funcation);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void SendRequest(String Funcation) {


        String url = Static.ServerAddress + Funcation;
        url=url.replace(" ","%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);

                            // Created a new Dialog
                            final Dialog dialog = new Dialog(Dedicate_Song.this);
                            // inflate the layout
                            dialog.setContentView(R.layout.message_dialog);
                            // Set the dialog text -- this is better done in the XML
                            final TextView Message = (TextView) dialog.findViewById(R.id.Message);
                            Message.setText(jsonObject.getString("insertDedicationsResult"));

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
