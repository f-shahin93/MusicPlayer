package com.example.musicplayer.controller;


import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.model.Album;
import com.example.musicplayer.model.Artist;
import com.example.musicplayer.model.Music;
import com.example.musicplayer.repository.ListRepository;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMusicFragment extends Fragment {

    public static final String ARG_ID = "Arg id";
    public static final String ARG_STATUS = "Arg status";
    private String statusList;
    private Long idList;
    private List<Music> mPlayList;
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private CircleImageView mCircleIvTopList;

    public ListMusicFragment() {
        // Required empty public constructor
    }

    public static ListMusicFragment newInstance(String status, Long id) {
        ListMusicFragment fragment = new ListMusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATUS, status);
        args.putLong(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            statusList = getArguments().getString(ARG_STATUS);
            idList = getArguments().getLong(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mPlayList = new ArrayList();

        if (statusList.equals("artist")) {
            mPlayList = ListRepository.getInstance().getMusicListByArtist(getActivity(), idList);
            view = getViewAlbumArtistList(inflater, container);

        } else if (statusList.equals("album")) {
            mPlayList = ListRepository.getInstance().getMusicListByAlbum(getActivity(), idList);
            view = getViewAlbumArtistList(inflater, container);

        } else
            mPlayList = ListRepository.getInstance().getListMusics(getActivity());

        mMyAdapter = new MyAdapter(mPlayList);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mMyAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<Music> listAdapter;

        MyAdapter(List<Music> list) {
            this.listAdapter = list;
        }

        public void setListAdapter(List<Music> list) {
            listAdapter = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View itemView = layoutInflater.inflate(R.layout.item_list_fragment, parent, false);

            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.bind(listAdapter.get(position));

        }

        @Override
        public int getItemCount() {
            return listAdapter.size();
        }

    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageVItemList;

        private TextView mTvItem1List, mTvItem2List;
        private Music mMusic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageVItemList = itemView.findViewById(R.id.IV_item_list);
            mTvItem1List = itemView.findViewById(R.id.TV_item_list_musicname);
            mTvItem2List = itemView.findViewById(R.id.TV_item_list_artistname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(PlaybackPageActivity.newIntent(getContext(), mMusic, mPlayList));
                }
            });

        }

        public void bind(Music music) {
            mMusic = music;
            mTvItem1List.setText(music.getTitle());
            mTvItem2List.setText(music.getArtist());

            if (mMusic.getPicMusic() == null) {
                mImageVItemList.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));

            } else
                mImageVItemList.setImageBitmap(BitmapFactory.decodeFile(mMusic.getPicMusic()));

        }

    }

    private View getViewAlbumArtistList(LayoutInflater inflater, ViewGroup container) {
        View view;
        view = inflater.inflate(R.layout.recycler_album_artist_list, container, false);
        mCircleIvTopList = view.findViewById(R.id.circle_Iv_listAlbum);
        if (mPlayList.size() > 0 && mPlayList.get(0).getPicMusic() != null)
            mCircleIvTopList.setImageBitmap(BitmapFactory.decodeFile(mPlayList.get(0).getPicMusic()));
        else
            mCircleIvTopList.setBackgroundResource(R.drawable.ic_back_music);
        return view;
    }
}
