package com.example.geeksera.jukebox.Adapter;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.geeksera.jukebox.Get_Token;
import com.example.geeksera.jukebox.Objects.SongsDetails;
import com.example.geeksera.jukebox.R;
import com.example.geeksera.jukebox.RequestSong;
import com.example.geeksera.jukebox.Dedicate_Song;

import java.util.List;

public class SongLibraryAdapter extends RecyclerView.Adapter<SongLibraryAdapter.MyViewHolder> {

    private List<SongsDetails> SongDetails;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView SongName,Singer,Albem,Duration,theme;

        LinearLayout Click;

        public MyViewHolder(View view) {
            super(view);
            SongName = (TextView) view.findViewById(R.id.SongName);
            Singer = (TextView) view.findViewById(R.id.Singer);
            Albem = (TextView) view.findViewById(R.id.Albem);
            Duration = (TextView) view.findViewById(R.id.Duration);
        theme=(TextView)view.findViewById(R.id.tvSongItemDesignTheme);
            Click=(LinearLayout)view.findViewById(R.id.Click);
        }
    }


    public SongLibraryAdapter(List<SongsDetails> SongLIst, Activity context) {
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
         holder.theme.setText(mSongDetail.getPlaystatus());
        holder.Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Created a new Dialog
                final Dialog dialog = new Dialog(mContext);
                // inflate the layout
                dialog.setContentView(R.layout.song_further_dialog);
                // Set the dialog text -- this is better done in the XML
                final TextView SongTittle=(TextView)dialog.findViewById(R.id.SongTittle);
                SongTittle.setText("Song Title: "+mSongDetail.getTitle());

                Button Dedicate;
                Dedicate = (Button) dialog.findViewById(R.id.RequestSong);
                Dedicate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(mContext, RequestSong.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("songsDetails",mSongDetail);
                        intent.putExtra("bundle",bundle);
                        mContext.startActivity(intent);
                        dialog.hide();


                        dialog.hide();

                    }
                });
                Button RequestSong;
                RequestSong = (Button) dialog.findViewById(R.id.Dedicate);
                RequestSong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(mContext, Dedicate_Song.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("songsDetails",mSongDetail);
                        intent.putExtra("bundle",bundle);
                        mContext.startActivity(intent);
                        dialog.hide();

                    }
                });
                Button Cancel;
                Cancel = (Button) dialog.findViewById(R.id.cencel);
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.hide();

                    }
                });

                Button GetToken;

                GetToken = (Button) dialog.findViewById(R.id.GetToken);
                GetToken.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        mContext.startActivity(new Intent(mContext, Get_Token.class));
                        dialog.hide();

                    }
                });


                dialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return SongDetails.size();
    }
}