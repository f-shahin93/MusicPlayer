package com.example.musicplayer.controller;


import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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


public class ListAlbumFragment extends Fragment {

    public static final String TAG_LIST = "tagList";
    String tagList = "";
    private List mPlayList = new ArrayList();
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;

    public ListAlbumFragment() {
        // Required empty public constructor
    }

    public static ListAlbumFragment newInstance(String tag) {
        ListAlbumFragment fragment = new ListAlbumFragment();
        Bundle args = new Bundle();
        args.putString(TAG_LIST, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tagList = getArguments().getString(TAG_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        if (tagList.equals("album")) {
            mPlayList = ListRepository.getInstance().getListAlbum(getActivity());

        } else if (tagList.equals("artist")) {
            mPlayList = ListRepository.getInstance().getListArtist(getActivity());
            getPicArtistList(mPlayList);
        }

        mMyAdapter = new MyAdapter(mPlayList);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mMyAdapter);

        if (tagList.equals("album")) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        List listAdapter;

        MyAdapter(List list) {
            this.listAdapter = list;
        }

        public void setListAdapter(List list) {
            listAdapter = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View itemView;
            if (tagList.equals("album")) {
                itemView = layoutInflater.inflate(R.layout.item_list_viewpager_album, parent, false);
            } else
                itemView = layoutInflater.inflate(R.layout.item_list_fragment, parent, false);

            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            if (tagList.equals("album")) {
                holder.bindAlbum((Album) listAdapter.get(position));

            } else if (tagList.equals("artist")) {
                holder.bindArtist((Artist) listAdapter.get(position));
            }

        }

        @Override
        public int getItemCount() {
            return listAdapter.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageVItemList;
        private TextView mTvItem1List, mTvItem2List;
        private Artist mArtist;
        private Album mAlbum;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageVItemList = itemView.findViewById(R.id.IV_item_list);
            mTvItem1List = itemView.findViewById(R.id.TV_item_list_musicname);
            mTvItem2List = itemView.findViewById(R.id.TV_item_list_artistname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tagList.equals("album")) {
                        startActivity(ListActivity.newIntent(getContext(), tagList, mAlbum.getId()));

                    } else if (tagList.equals("artist")) {
                        startActivity(ListActivity.newIntent(getContext(), tagList, mArtist.getId()));
                    }
                }
            });

        }

        public void bindAlbum(Album album) {
            mAlbum = album;
            if (album.getAlbumPic() == null) {
                mImageVItemList.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));

            } else
                mImageVItemList.setImageBitmap(BitmapFactory.decodeFile(album.getAlbumPic()));

            mTvItem1List.setText(album.getAlbumName());
            mTvItem2List.setText(album.getArtist());
        }

        public void bindArtist(Artist artist) {
            mArtist = artist;
            if (artist.getAlbumPic() == null) {
                mImageVItemList.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));

            } else
                mImageVItemList.setImageBitmap(BitmapFactory.decodeFile(artist.getAlbumPic()));

            mTvItem1List.setText(artist.getArtist());
            mTvItem2List.setText("Musics count : "+artist.getMusicCount());

        }
    }

    private void getPicArtistList(List<Artist> listArtist){
        List<Album> listAlbum = ListRepository.getInstance().getListAlbum(getActivity());

        for (int i = 0 ;i<listAlbum.size();i++){
                for (int j = 0 ;j<listArtist.size();j++){
                    if(listAlbum.get(i).getArtist().equals(listArtist.get(j).getArtist())){
                        listArtist.get(j).setAlbumPic(listAlbum.get(i).getAlbumPic());
                    }
                }
        }
    }

}
