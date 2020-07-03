package com.example.photogallery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.photogallery.pojo.Picture;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ImageDbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "image_db";
    public static final String TABLE_NAME = "image_table";

    /*public static final String KEY_ID = "id";
    public static final String KEY_IMAGENAME = "name";
    public static final String KEY_IMAGE = "image";*/

    private static String create = "create table imageInfo (id INTEGER PRIMARY KEY AUTOINCREMENT,"+"imageName TEXT" +",type TEXT"+ ",image BLOB)";

    public ByteArrayOutputStream byteArrayOutputStream;
    public byte[] imageInBytes;

    Context context;

    public ImageDbHelper(@Nullable Context context) {
        super(context,DB_NAME, null, DB_VERSION);
        //this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String create = "CREATE TABLE "+ TABLE_NAME +"(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IMAGENAME + " TEXT, " + KEY_IMAGE + " BLOB)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addImage(Picture picture){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap storeImage = picture.getImage();

            byteArrayOutputStream = new ByteArrayOutputStream();
            storeImage.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            imageInBytes = byteArrayOutputStream.toByteArray();
            ContentValues contentValues = new ContentValues();

            contentValues.put("imageName",picture.getImageName());
            contentValues.put("type",picture.getType());
            contentValues.put("image",imageInBytes);

            long checkIfQueryRuns = db.insert("imageInfo",null,contentValues);

            /*if(checkIfQueryRuns!=-1){
                Toast.makeText(context,"All Ok",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Not Ok",Toast.LENGTH_SHORT).show();
            }*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getCount(){
        String query = "SELECT * FROM " + "imageInfo";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        return c.getCount();
    }

    public ArrayList<Picture> getAll(String album){
        ArrayList<Picture> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + "imageInfo";
        Cursor cursor = db.rawQuery(select, null);

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Picture picture = new Picture();
                Bitmap bitmap = null;
                if(cursor.getString(2).equals(album)){
                    byte[] imag = cursor.getBlob(3);
                    bitmap = BitmapFactory.decodeByteArray(imag,0,imag.length);
                    picture.setImage(bitmap);
                    list.add(picture);
                }
            }while(cursor.moveToNext());
        }
        return list;
    }

}
