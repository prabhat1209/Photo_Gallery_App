package com.example.photogallery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.photogallery.R;
import com.example.photogallery.database.ImageDbHelper;
import com.example.photogallery.pojo.Picture;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.view_gallery);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AlbumActivity.class));
            }
        });

        Bitmap i11 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c1);
        Bitmap i12 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c2);
        Bitmap i13 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c3);
        Bitmap i14 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c4);
        Bitmap i15 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c5);
        Bitmap i21 = BitmapFactory.decodeResource(this.getResources(), R.drawable.w1);
        Bitmap i22 = BitmapFactory.decodeResource(this.getResources(), R.drawable.w2);
        Bitmap i23 = BitmapFactory.decodeResource(this.getResources(), R.drawable.w3);
        Bitmap i24 = BitmapFactory.decodeResource(this.getResources(), R.drawable.w4);
        Bitmap i25 = BitmapFactory.decodeResource(this.getResources(), R.drawable.w5);
        Bitmap i31 = BitmapFactory.decodeResource(this.getResources(), R.drawable.t1);
        Bitmap i32 = BitmapFactory.decodeResource(this.getResources(), R.drawable.t2);
        Bitmap i33 = BitmapFactory.decodeResource(this.getResources(), R.drawable.t3);
        Bitmap i34 = BitmapFactory.decodeResource(this.getResources(), R.drawable.t4);
        Bitmap i35 = BitmapFactory.decodeResource(this.getResources(), R.drawable.t5);

        ImageDbHelper imageDbHelper = new ImageDbHelper(MainActivity.this);

        if(imageDbHelper.getCount()!=15){
            imageDbHelper.addImage(new Picture("1","C" ,i11));
            imageDbHelper.addImage(new Picture("1","C" ,i12));
            imageDbHelper.addImage(new Picture("1","C" ,i13));
            imageDbHelper.addImage(new Picture("1","C" ,i14));
            imageDbHelper.addImage(new Picture("1","C" ,i15));
            imageDbHelper.addImage(new Picture("1","S" ,i21));
            imageDbHelper.addImage(new Picture("1","S" ,i22));
            imageDbHelper.addImage(new Picture("1","S" ,i23));
            imageDbHelper.addImage(new Picture("1","S" ,i24));
            imageDbHelper.addImage(new Picture("1","S" ,i25));
            imageDbHelper.addImage(new Picture("1","W" ,i31));
            imageDbHelper.addImage(new Picture("1","W" ,i32));
            imageDbHelper.addImage(new Picture("1","W" ,i33));
            imageDbHelper.addImage(new Picture("1","W" ,i34));
            imageDbHelper.addImage(new Picture("1","W" ,i35));
        }

    }
}