package com.example.musicplayer.controller;

import android.content.ContentUris;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.musicplayer.model.Music;

import java.util.List;

public class MyMediaPlayer {
    public static MyMediaPlayer instance;
    private MediaPlayer mMediaPlayer;
    private Context mContext;

    private MyMediaPlayer(Context context) {
        mMediaPlayer = new MediaPlayer();
        mContext = context;

    }

    public static MyMediaPlayer getInstance(Context context) {
        if(instance == null){
            instance = new MyMediaPlayer(context);
        }
        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    public void play(Music music){
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer= null;
        }

        long currMusicId = music.getId();
        Uri musicUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currMusicId);
        mMediaPlayer = MediaPlayer.create(mContext, musicUri);
        mMediaPlayer.start();
    }

    public void previous(Music music , List<Music> playList){

         int index = getIndexMusic(music ,playList);
                index = (index-1 + playList.size()) % playList.size();
                music = playList.get(index);
                mMediaPlayer.release();
                play(music);
                mMediaPlayer.start();

    }

    public void next(Music music , List<Music> playList){

        int index = getIndexMusic(music ,playList);
        index = (++index) % playList.size();
        music = playList.get(index);
        mMediaPlayer.release();
        play(music);
        mMediaPlayer.start();
    }

    private int getIndexMusic(Music music ,List<Music> playList) {

        for (int i = 0; i < playList.size(); i++) {
            if (playList.get(i).getId().equals(music.getId())) {
                return i;
            }
        }
        return -1;
    }

}
