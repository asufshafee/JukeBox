package com.example.geeksera.jukebox.Objects;

import java.io.Serializable;

/**
 * Created by GeeksEra on 12/21/2017.
 */

public class SongsDetails  implements Serializable{
    String Title;
    String S_id;
    String A_id;
    String Duration;
    String AlbumName;
    String Singer;
    String Playstatus;

    public String getPlaystatus() {
        return Playstatus;
    }

    public void setPlaystatus(String playstatus) {
        Playstatus = playstatus;
    }

    public String getPeriority() {
        return Periority;
    }

    public void setPeriority(String periority) {
        Periority = periority;
    }

    String Periority;

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getS_id() {
        return S_id;
    }

    public void setS_id(String s_id) {
        S_id = s_id;
    }

    public String getA_id() {
        return A_id;
    }

    public void setA_id(String a_id) {
        A_id = a_id;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getSinger() {
        return Singer;
    }

    public void setSinger(String singer) {
        Singer = singer;
    }

}
