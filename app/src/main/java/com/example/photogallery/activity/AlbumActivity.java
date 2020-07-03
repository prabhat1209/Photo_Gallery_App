package com.example.photogallery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.example.photogallery.R;
import com.example.photogallery.adapter.AlbumAdapter;
import com.example.photogallery.pojo.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    ArrayList<Album> list;
    RecyclerView recyclerView;
    AlbumAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        list = new ArrayList<>();

        recyclerView = findViewById(R.id.rec_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        list.add(new Album("Camera"));
        list.add(new Album("Screenshots"));
        list.add(new Album("WhatsApp"));

        albumAdapter = new AlbumAdapter(this,list);
        recyclerView.setAdapter(albumAdapter);

        albumAdapter.setOnItemClickListener(new AlbumAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(AlbumActivity.this,PhotosActivity.class);
                intent.putExtra("title",list.get(pos).getTitle());
                startActivity(intent);
            }
        });

    }

}