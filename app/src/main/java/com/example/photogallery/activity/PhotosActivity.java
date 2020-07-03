package com.example.photogallery.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photogallery.R;
import com.example.photogallery.adapter.AlbumAdapter;
import com.example.photogallery.adapter.PhotoAdapter;
import com.example.photogallery.database.ImageDbHelper;
import com.example.photogallery.pojo.Picture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PhotosActivity extends AppCompatActivity {

    String albumName;
    TextView albumTitle;
    RecyclerView recyclerView;
    ArrayList<Picture> list;
    PhotoAdapter photoAdapter;
    ImageDbHelper imageDbHelper;
    String album = "";
    ImageView download;
    private static final int WRITE_EXTERNA_STORAGE_CODE = 1;
    public static boolean selected_state = false;
    Button selectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        list = new ArrayList<>();
        albumTitle = findViewById(R.id.album_name);
        recyclerView = findViewById(R.id.photo_rec_view);
        recyclerView.setHasFixedSize(true);
        imageDbHelper = new ImageDbHelper(PhotosActivity.this);
        download = findViewById(R.id.download_images);
        selectAll = findViewById(R.id.select_all);

        recyclerView.setLayoutManager(new GridLayoutManager(PhotosActivity.this,3));

        Intent intent = getIntent();
        albumName = intent.getStringExtra("title");
        albumTitle.setText(albumName);

        if(albumName.equals("Camera"))
            album = "C";
        else
        if(albumName.equals("Screenshots"))
            album = "S";
        else
        if(albumName.equals("WhatsApp"))
            album = "W";

        list = imageDbHelper.getAll(album);

        for(int i=0;i<list.size();i++)
            list.get(i).setIs_selected("0");

        photoAdapter = new PhotoAdapter(this,list);
        recyclerView.setAdapter(photoAdapter);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String [] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,WRITE_EXTERNA_STORAGE_CODE);
                    }else{
                        saveImages();
                    }
                }
            }
        });

        photoAdapter.setOnItemClickListener(new PhotoAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //ShowDialogBox(pos);
                try {
                    if (selected_state) {
                        list.get(pos).setIs_selected("1");
                    } else {

                        Bitmap storeImage = list.get(pos).getImage();

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        storeImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] imageInBytes = byteArrayOutputStream.toByteArray();

                        Intent intent = new Intent(PhotosActivity.this, FullImageActivity.class);
                        intent.putExtra("pic", imageInBytes);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    System.out.print("OnClick : ");
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(int pos) {
                try{
                    selected_state = true;
                    list.get(pos).setIs_selected("1");
                    selectAll.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    System.out.print("OnLongClick : ");
                    e.printStackTrace();
                }

            }
        });


        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<list.size();i++)
                    list.get(i).setIs_selected("1");

                recyclerView.setLayoutManager(new GridLayoutManager(PhotosActivity.this,3));
                photoAdapter = new PhotoAdapter(PhotosActivity.this,list);
                recyclerView.setAdapter(photoAdapter);

            }
        });

    }

    /*public void ShowDialogBox(final int pos){
        final Dialog dialog = new Dialog(PhotosActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        ImageView image = dialog.findViewById(R.id.img);
        Button btn_Full = dialog.findViewById(R.id.btn_full);
        Button btn_Close = dialog.findViewById(R.id.btn_close);

        image.setImageBitmap(list.get(pos).getImage());
        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_Full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }*/

    public void saveImages(){
        Bitmap bitmap = null;
        File path = Environment.getExternalStorageDirectory();

        int count = 0;

        for(int j=0;j<list.size();j++){
            if(list.get(j).getIs_selected().equals("1"))
                count++;
        }
        if(count>0){
            int i = 0;
            while (i<list.size()) {

                if(list.get(i).getIs_selected().equals("1")){
                    File dir = new File(path + "/" + album + "from");
                    System.out.println("Dir .. " + dir);
                    dir.mkdirs();
                    bitmap = list.get(i).getImage();
                    String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());

                    String imageName = String.valueOf(i) + ".JPEG";
                    File file = new File(dir, imageName);
                    OutputStream outputStream;
                    try {
                        outputStream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                i++;

            }
            Toast.makeText(PhotosActivity.this,"Downloading...!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(PhotosActivity.this,"No item selected..!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNA_STORAGE_CODE:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    Toast.makeText(PhotosActivity.this,"Enable Permission",Toast.LENGTH_SHORT).show();
                }
            }
        }
        saveImages();
    }

    @Override
    public void onBackPressed() {
        if(selected_state){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            selectAll.setVisibility(View.INVISIBLE);
            selected_state = false;
            for(int i=0;i<list.size();i++)
                list.get(i).setIs_selected("0");
            recyclerView.setLayoutManager(new GridLayoutManager(PhotosActivity.this,3));
            photoAdapter = new PhotoAdapter(this,list);
            recyclerView.setAdapter(photoAdapter);

        }else{
            /*Intent intent = new Intent(PhotosActivity.this,AlbumActivity.class);
            startActivity(intent);
            finish();*/
            super.onBackPressed();
        }
    }
}