package com.example.geeksera.jukebox.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.geeksera.jukebox.Objects.SongsDetails;
import com.example.geeksera.jukebox.R;

import java.util.List;

public class SongQueueAdapter extends RecyclerView.Adapter<SongQueueAdapter.MyViewHolder> {

    private List<SongsDetails> SongDetails;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView SongName,Singer,Albem,Duration;

        LinearLayout Click;

        public MyViewHolder(View view) {
            super(view);
            SongName = (TextView) view.findViewById(R.id.SongName);
            Singer = (TextView) view.findViewById(R.id.Singer);
            Albem = (TextView) view.findViewById(R.id.Albem);
            Duration = (TextView) view.findViewById(R.id.Duration);

            Click=(LinearLayout)view.findViewById(R.id.Click);
        }
    }


    public SongQueueAdapter(List<SongsDetails> SongLIst, Activity context) {
        this.SongDetails = SongLIst;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SongsDetails mSongDetail = SongDetails.get(position);

        holder.SongName.setText(mSongDetail.getTitle());
        holder.Singer.setText(mSongDetail.getSinger());
        holder.Albem.setText(mSongDetail.getAlbumName());
        holder.Duration.setText(mSongDetail.getDuration());
    }

    @Override
    public int getItemCount() {
        return SongDetails.size();
    }
}