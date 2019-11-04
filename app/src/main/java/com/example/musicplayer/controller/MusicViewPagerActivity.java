package com.example.musicplayer.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.musicplayer.R;
import com.example.musicplayer.model.Album;
import com.example.musicplayer.model.Artist;
import com.example.musicplayer.model.Music;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MusicViewPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabItem mTabAlbums;
    private TabItem mTabArtists;
    private TabItem mTabTracks;
    private FragmentStatePagerAdapter mAdapter;
    private List<Album> mListAlbums = new ArrayList<>();
    private List<Artist> mListArtists = new ArrayList<>();
    private List<Music> mListTracks = new ArrayList<>();
    private int mNumOfTab;
    private boolean mIsInTabMusic = false;

    public static final String MODALFRAGMENT = "modalfragment";
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout mLinearLayoutBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_view_pager);

        mTabLayout = findViewById(R.id.tablayout);
        mTabAlbums = findViewById(R.id.tab_albums);
        mTabArtists = findViewById(R.id.tab_artists);
        mTabTracks = findViewById(R.id.tab_musics);
        mViewPager = findViewById(R.id.activity_containerViewPager);

        /*mLinearLayoutBottomSheet = findViewById(R.id.bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(mLinearLayoutBottomSheet);
        bottomSheetBehavior.setPeekHeight(50);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);*/

        mNumOfTab = mTabLayout.getTabCount();

        mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }

            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0: {
                        mIsInTabMusic = false;
                        return ListAlbumFragment.newInstance("album");
                        //return ListAlbumFragment.newInstance(mListAlbums , "album");
                    }
                    case 1: {
                        mIsInTabMusic = false;
                        return ListAlbumFragment.newInstance("artist");
                        //return ListAlbumFragment.newInstance(mListArtists,"artist");
                    }
                    case 2: {
                        mIsInTabMusic = true;
                        return ListMusicFragment.newInstance("", (long) 00);
                    }
                }
                return null;
            }

            @Override
            public int getCount() {
                return mNumOfTab;
            }
        };

        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*mLinearLayoutBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(PlaybackPageActivity.newIntent(getApplicationContext()));

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });*/

        //finish();


    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MusicViewPagerActivity.class);
        return intent;
    }

}