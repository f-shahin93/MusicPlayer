package com.example.musicplayer.model;

import java.io.Serializable;

public class Music implements Serializable {

    private Long mId;
    private String mTitle;
    private String mArtist;
    private String mAlbum;
    private String mPicMusic;
    private int mDuration;
    private String mPath;


    /*private String data;
    private Uri art;
    private Bitmap mBitmap;*/


    public Music(Long id, String title, String artist, String album, String picMusic, int duration , String path) {
        mId = id;
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
        mPicMusic = picMusic;
        mDuration = duration;
        mPath = path;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getPicMusic() {
        return mPicMusic;
    }

    public void setPicMusic(String picMusic) {
        mPicMusic = picMusic;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }
}
