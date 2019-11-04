package com.example.musicplayer.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.musicplayer.R;

public class ListActivity extends AppCompatActivity {

    public static final String EXTRA_STATUS = "Extra status";
    public static final String EXTRA_ID = "Extra id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        String status = getIntent().getStringExtra(EXTRA_STATUS);
        Long id = getIntent().getLongExtra(EXTRA_ID, 0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_container, ListMusicFragment.newInstance(status,id))
                .commit();

    }

    public static Intent newIntent(Context context, String status, Long id) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra(EXTRA_STATUS,status);
        intent.putExtra(EXTRA_ID,id);
        return intent;

    }

}
