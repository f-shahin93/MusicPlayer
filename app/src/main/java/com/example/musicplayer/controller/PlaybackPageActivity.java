package com.example.musicplayer.controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.model.Music;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class PlaybackPageActivity extends AppCompatActivity {

    public static final String EXTRA_MUSIC = "Extra music";
    public static final String EXTRA_PLAYLIST = "Extra playlist";
    private static final String SONG_ID_ARG = "song_id_arg";
    public static final String BUNDLE_MEDIA = "Bundle_Media";
    private CircleImageView mImageVBackMusic;
    private AppCompatImageButton mButtonPrevious, mButtonNext, mButtonPlay, mButtonRepeat, mButtonShuffle;
    private SeekBar mSeekBar;
    private TextView mTextMusicName, mTextArtistName;
    private MediaPlayer mMediaPlayer;
    int pauseCurrentPosition = 0;
    private Music mMusic;
    private List<Music> mPlayList;
    private boolean flagPlay = false;
    private TextView mTextVTimeMusic;
    private ConstraintLayout mConstraintLayout;
    private LinearLayout mLinearLayout;
    private Handler mHandler;
    private int mMusicPosition;
    private MyMediaPlayer mMyMediaPlayer;
    int currentSongIndex = 0;
    boolean isShuffle = false;
    boolean isRepeat = false;
    int orientation;

    private boolean mRepeateSong = false;
    private static boolean mShuffle = false;
    //private CallBacks mCallBacks;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback_page);

        mImageVBackMusic = findViewById(R.id.image_back_music);
        mButtonShuffle = findViewById(R.id.shuffle_music);
        mButtonPlay = findViewById(R.id.play_music);
        mButtonPrevious = findViewById(R.id.previous_music);
        mButtonNext = findViewById(R.id.next_music);
        mButtonRepeat = findViewById(R.id.repeat_music);
        mSeekBar = findViewById(R.id.seekBar_music);
        mTextVTimeMusic = findViewById(R.id.text_view_time);
        mTextMusicName = findViewById(R.id.TvmusicName);
        mTextArtistName = findViewById(R.id.TvArtistName);
        mConstraintLayout = findViewById(R.id.play_music_layout);
        mLinearLayout = findViewById(R.id.play_music_Linearlayout);

        mMyMediaPlayer = MyMediaPlayer.getInstance(getApplicationContext());


        mMusic = (Music) getIntent().getSerializableExtra(EXTRA_MUSIC);
        mPlayList = (List) getIntent().getSerializableExtra(EXTRA_PLAYLIST);

        initUI();
        playMusic();
        mMediaPlayer = mMyMediaPlayer.getMediaPlayer();
        if(savedInstanceState != null){
            mMyMediaPlayer.getMediaPlayer().seekTo(savedInstanceState.getInt(BUNDLE_MEDIA));
        }

        //mButtonRepeat.setBackgroundResource(R.drawable.ic_repeat);
        //  mMediaPlayer = new MediaPlayer();



        /*Picasso.get()
                .load(Uri.parse("file://" + mMusic.getPicMusic()))
                .transform(new jp.wasabeef.picasso.transformations.BlurTransformation(getApplicationContext(), 5, 2))
                .into(mImageViewBackLayout);*/

        //Picasso.with(mContext).load(Uri.parse("file://" + albumArtPath)).into(imageView);
        //Picasso.with(mContext).load(Uri.fromFile(new File(albumArtPath))).into(imageView);

        Long id = mMusic.getId();
        mHandler = new Handler();
        mMusicPosition = 0;
        initMediaPlayer(mMusic);
        convertPicMusic(mMusic);
        updateProgress();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mTextVTimeMusic.setVisibility(View.VISIBLE);
              /*  int x = (int) Math.ceil(progress / 1000f);
                if (x < 10)
                    mTextVTimeMusic.setText("0:0" + x);
                else
                    mTextVTimeMusic.setText("0:" + x);

                double percent = progress / (double) seekBar.getMax();
                int offset = seekBar.getThumbOffset();
                int seekWidth = seekBar.getWidth();
                int val = (int) Math.round(percent * (seekWidth - 2 * offset));
                int labelWidth = mTextVTimeMusic.getWidth();
                mTextVTimeMusic.setX(offset + seekBar.getX() + val
                        - Math.round(percent * offset)
                        - Math.round(percent * labelWidth / 2));

                if (progress > 0 && mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }
                    mButtonPlay.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.ic_media_play));
                    mSeekBar.setProgress(0);
                }*/
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mTextVTimeMusic.setVisibility(View.VISIBLE);
                mHandler.removeCallbacks(mUpdate);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /*if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.seekTo(seekBar.getProgress());
                }*/

                mHandler.removeCallbacks(mUpdate);

                int totalDuration = mMyMediaPlayer.getMediaPlayer().getDuration();
                int currentDuration = progressToTime(seekBar.getProgress(), totalDuration);

                mMyMediaPlayer.getMediaPlayer().seekTo(currentDuration);

                updateProgress();


                //int totalDuration = mMediaPlayer.getDuration();

                //int currentDuration= .progressToTime(seekBar.getProgress(),totalDuration);

                /*
                mHandler.removeCallbacks(mUpdate);

                int totalDuration=mp.getDuration();
                int currentDuration=utils.progressToTime(seekBar.getProgress(),totalDuration);

                mp.seekTo(currentDuration);

                updateProgress();
                 */

            }
        });

        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //mMediaPlayer.prepare();
                //mMediaPlayer.setVolume(0.5f, 0.5f);
                //mMediaPlayer.setLooping(loop);

                /*if (mMediaPlayer == null) {
                    flagPlay = true;
                    initMediaPlayer(mMusic);
                    mMediaPlayer.start();
                    mButtonPlay.setBackgroundDrawable(ContextCompat.getDrawable(PlaybackPageActivity.this, android.R.drawable.ic_media_pause));

                } else if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
                    flagPlay = true;
                    // mSeekBar.setProgress(pauseCurrentPosition);
                    //mSeekBar.setMax(mMediaPlayer.getDuration());
                    //mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                    mMediaPlayer.seekTo(pauseCurrentPosition);
                    mMediaPlayer.start();
                    mButtonPlay.setBackgroundResource(R.drawable.ic_pause);

                } else if (mMediaPlayer != null && flagPlay) {
                    //clearMediaPlayer();
                    flagPlay = false;
                    mMediaPlayer.pause();
                    // pauseCurrentPosition = mMediaPlayer.getCurrentPosition();
                    //mMediaPlayer.seekTo(pauseCurrentPosition);
                    //mSeekBar.setProgress(0);
                    mButtonPlay.setBackgroundResource(R.drawable.ic_play);
                    pauseCurrentPosition = mMediaPlayer.getCurrentPosition();
                }*/

                if (mMyMediaPlayer.getMediaPlayer() != null && !mMyMediaPlayer.getMediaPlayer().isPlaying()) {
                    // mSeekBar.setProgress(pauseCurrentPosition);
                    //mSeekBar.setMax(mMediaPlayer.getDuration());
                    //mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                    flagPlay = true;
                    mMyMediaPlayer.getMediaPlayer().seekTo(pauseCurrentPosition);
                    mMyMediaPlayer.getMediaPlayer().start();
                    mButtonPlay.setBackgroundResource(R.drawable.ic_pause);

                } else if (mMyMediaPlayer.getMediaPlayer() != null && flagPlay) {
                    flagPlay = false;
                    mMyMediaPlayer.getMediaPlayer().pause();
                    // pauseCurrentPosition = mMediaPlayer.getCurrentPosition();
                    //mMediaPlayer.seekTo(pauseCurrentPosition);
                    //mSeekBar.setProgress(0);
                    mButtonPlay.setBackgroundResource(R.drawable.ic_play_music);
                    pauseCurrentPosition = mMyMediaPlayer.getMediaPlayer().getCurrentPosition();
                }

            }
        });

        mButtonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isRepeat) {
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    mButtonRepeat.setBackgroundResource(R.drawable.ic_repeat);

                } else {
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    mButtonRepeat.setBackgroundResource(R.drawable.ic_repeat_one);

                }
            }
        });

        mButtonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isShuffle) {
                    isShuffle = false;
                    mButtonShuffle.setBackgroundResource(R.drawable.ic_shuffle_off);
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();

                } else {
                    isShuffle = true;
                    mButtonShuffle.setBackgroundResource(R.drawable.ic_shuffle);
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();

                }
            }
        });

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int index = getIndexMusic(mMusic);
                nextMusic(index);

                /*int index = getIndexMusic(mMusic);
                index = (++index) % mPlayList.size();
                mMusic = mPlayList.get(index);
                convertPicMusic(mMusic);
                mMediaPlayer.release();
                initMediaPlayer(mMusic);
                mSeekBar.setProgress(0);
                mMediaPlayer.start();
                flagPlay = true;*/

            }
        });

        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int index = getIndexMusic(mMusic);
                //index = (index - 1 + mPlayList.size()) % mPlayList.size();
                //changeMusic(index);

                if(!isShuffle && isRepeat){
                    changeMusic(index);

                }else if(isShuffle && isRepeat){
                    Random random = new Random();
                    index = random.nextInt(mPlayList.size() -1);
                    changeMusic(index);

                }else if(!isShuffle && !isRepeat){
                    index = (index - 1 + mPlayList.size()) % mPlayList.size();
                    changeMusic(index);

                }else if(isShuffle && !isRepeat ){
                    Random random = new Random();
                    index = random.nextInt(mPlayList.size() -1);
                    changeMusic(index);
                }

/*                int index = getIndexMusic(mMusic);
                index = (index - 1 + mPlayList.size()) % mPlayList.size();
                mMusic = mPlayList.get(index);
                convertPicMusic(mMusic);
                mMediaPlayer.release();
                initMediaPlayer(mMusic);
                mSeekBar.setProgress(0);
                mMediaPlayer.start();
                flagPlay = true;*/

            }
        });

        mMyMediaPlayer.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                //mButtonPlay.setBackgroundResource(R.drawable.ic_play);
                Toast.makeText(getApplicationContext(), "onCompletion", Toast.LENGTH_SHORT).show();

                int index = getIndexMusic(mMusic);
                nextMusic(index);

            }
        });

    }


    private void convertPicMusic(Music music) {
        Bitmap bm = BitmapFactory.decodeFile(music.getPicMusic());
        mImageVBackMusic.setImageBitmap(bm);
    }

    public void initMediaPlayer(Music music) {
        long currMusicId = music.getId();
        Uri musicUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currMusicId);
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), musicUri);
        mSeekBar.setProgress(0);
        //mMediaPlayer.seekTo(mMediaPlayer.getDuration());

      /*  mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener((MediaPlayer.OnPreparedListener) this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener((MediaPlayer.OnErrorListener) this);*/
    }

    public static Intent newIntent(Context context, Music music, List<Music> playList) {
        Intent intent = new Intent(context, PlaybackPageActivity.class);
        intent.putExtra(EXTRA_MUSIC, music);
        intent.putExtra(EXTRA_PLAYLIST, (Serializable) playList);
        return intent;
    }

/*
    @Override
    public void run() {
        int currentPosition = mMediaPlayer.getCurrentPosition();
        int total = mMediaPlayer.getDuration();

        while (mMediaPlayer != null && flagPlay && currentPosition < total) {
            try {
                Thread.sleep(1000);
                currentPosition = mMediaPlayer.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }

            mSeekBar.setProgress(currentPosition);
            mHandler.postDelayed(this, 1000);

        }
    }*/


    public String milli_to_time(long millisecond) {

        String hours = String.valueOf((int) (millisecond / (60 * 60 * 1000)));
        String minutes = String.valueOf(((int) (millisecond % (60 * 60 * 1000) / (60 * 1000))));
        String seconds = String.valueOf(((int) ((millisecond % (60 * 60 * 1000)) % (60 * 1000) / 1000)));

        if (Integer.parseInt(hours) < 10)
            hours = "0" + hours;

        if (Integer.parseInt(minutes) < 10)
            minutes = "0" + minutes;

        if (Integer.parseInt(seconds) < 10)
            seconds = "0" + seconds;

        if (Integer.parseInt(hours) > 0)
            return hours + ":" + minutes + ":" + seconds;
        else
            return minutes + ":" + seconds;

    }


    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        return percentage.intValue();

    }


    public int progressToTime(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        return currentDuration * 1000;

    }

    public void playSong(int songIndex) {

        try {
            /*mMediaPlayer.reset();
            mMediaPlayer.setDataSource(String.valueOf(mPlayList.get(songIndex).getId()));
            mMediaPlayer.prepare();
            mMediaPlayer.start();*/

            mMyMediaPlayer.getMediaPlayer().reset();
            mMyMediaPlayer.getMediaPlayer().setDataSource(String.valueOf(mPlayList.get(songIndex).getId()));
            mMyMediaPlayer.getMediaPlayer().prepare();
            mMyMediaPlayer.getMediaPlayer().start();

            //String title = songsList.get(songIndex).get("title");
            // songTitle.setText(title);
            mButtonPlay.setBackgroundResource(android.R.drawable.ic_media_pause);
            mSeekBar.setMax(100);
            mSeekBar.setProgress(0);
            updateProgress();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void updateProgress() {

        mHandler.postDelayed(mUpdate, 100);

    }

    Runnable mUpdate = new Runnable() {
        @Override
        public void run() {

            if (mMyMediaPlayer.getMediaPlayer() != null) {

                long totalDuration = mMyMediaPlayer.getMediaPlayer().getDuration();
                long currentDuration = mMyMediaPlayer.getMediaPlayer().getCurrentPosition();

                mTextVTimeMusic.setText(milli_to_time(currentDuration));
                // songTotalDurationLabel.setText(milli_to_time(totalDuration));

                int progress = (getProgressPercentage(currentDuration, totalDuration));

                mSeekBar.setProgress(progress);
                mHandler.postDelayed(this, 100);
            }
        }
    };

    private void playMusic() {

        flagPlay = true;
        mMyMediaPlayer.play(mMusic);
        mButtonPlay.setBackgroundResource(R.drawable.ic_pause);

    }

    private void changeMusic(int index){
        mMusic = mPlayList.get(index);
        convertPicMusic(mMusic);
        initUI();
        mSeekBar.setProgress(0);
        mMyMediaPlayer.getMediaPlayer().seekTo(0);
        flagPlay = true;
        mMyMediaPlayer.play(mMusic);
    }

    private void nextMusic(int index){
        if(!isShuffle && isRepeat){
            changeMusic(index);

        }else if(isShuffle && isRepeat){
            Random random = new Random();
            index = random.nextInt(mPlayList.size() -1);
            changeMusic(index);

        }else if(!isShuffle && !isRepeat){
            index = (++index) % mPlayList.size();
            changeMusic(index);

        }else if(isShuffle && !isRepeat ){
            Random random = new Random();
            index = random.nextInt(mPlayList.size() -1);
            changeMusic(index);
        }
    }

    public void initUI() {

       orientation = getResources().getConfiguration().orientation;

        Picasso.get()
                .load(Uri.parse("file://" + mMusic.getPicMusic()))
                .transform(new BlurTransformation(getApplicationContext(), 10, 8))
                .into(new Target() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                mLinearLayout.setBackground(new BitmapDrawable(bitmap));
                            } else if (orientation == Configuration.ORIENTATION_PORTRAIT)
                                mConstraintLayout.setBackground(new BitmapDrawable(bitmap));

                       // mConstraintLayout.setBackground(new BitmapDrawable(bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        mTextMusicName.setText(mMusic.getTitle());
        mTextArtistName.setText(mMusic.getArtist());

    }

    private int getIndexMusic(Music music) {

        for (int i = 0; i < mPlayList.size(); i++) {
            if (mPlayList.get(i).getId().equals(music.getId())) {
                return i;
            }
        }
        return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outPersistentState.putInt(BUNDLE_MEDIA,mMyMediaPlayer.getMediaPlayer().getCurrentPosition());
    }
}
