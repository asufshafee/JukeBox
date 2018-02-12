package com.example.geeksera.jukebox.Adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geeksera.jukebox.Objects.Request;
import com.example.geeksera.jukebox.R;

import java.util.List;

public class TokenRequestsAdapter extends RecyclerView.Adapter<TokenRequestsAdapter.MyViewHolder> {

    private List<Request> RequestDetails;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Username, TableNO, Status, Tokens;

        public MyViewHolder(View view) {
            super(view);
            Username = (TextView) view.findViewById(R.id.UserName);
            TableNO = (TextView) view.findViewById(R.id.TableNumber);
            Status = (TextView) view.findViewById(R.id.Status);
            Tokens = (TextView) view.findViewById(R.id.Tokens);

        }
    }


    public TokenRequestsAdapter(List<Request> RequestLIst, Activity context) {
        this.RequestDetails = RequestLIst;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_requests, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Request request = RequestDetails.get(position);

        holder.Username.setText(request.getPurchasedby());
        holder.TableNO.setText(request.getTableNumber());
        holder.Tokens.setText(request.getTcount());
        if (request.getTokenstatus().equals("1")) {
            holder.Status.setText("Approved");
        } else {
            holder.Status.setText("Not Approved");
            holder.Status.setTextColor(Color.RED);
        }


    }

    @Override
    public int getItemCount() {
        return RequestDetails.size();
    }
}