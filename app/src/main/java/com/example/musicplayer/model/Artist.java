package com.example.musicplayer.model;

import java.io.Serializable;

public class Artist implements Serializable {

    private Long mId;
   // private String mAlbumName;
    private String mArtist;
    private String mAlbumPic;
    private String mMusicCount;

    public Artist(Long id, String artist, String albumPic, String musicCount) {
        mId = id;
        mArtist = artist;
        mAlbumPic = albumPic;
        mMusicCount = musicCount;
    }

    public Artist(Long id, String artist, String musicCount) {
        mId = id;
        mArtist = artist;
        mMusicCount = musicCount;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getAlbumPic() {
        return mAlbumPic;
    }

    public void setAlbumPic(String albumPic) {
        mAlbumPic = albumPic;
    }

    public String getMusicCount() {
        return mMusicCount;
    }

    public void setMusicCount(String musicCount) {
        mMusicCount = musicCount;
    }
}
