package com.example.photogallery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photogallery.R;
import com.example.photogallery.activity.PhotosActivity;
import com.example.photogallery.pojo.Picture;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    Context context;
    List<Picture> list;
    LayoutInflater inflater;
    ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onItemClick(int pos);
        void onLongClick(int pos);
    }

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    public PhotoAdapter(Context context, List<Picture> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_photo, parent, false);
        ViewHolder holder = new ViewHolder(view,itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picture picture = list.get(position);
        if(list.get(position).getIs_selected()=="1")
            holder.imageView.setColorFilter(R.color.white);
        holder.imageView.setImageBitmap(picture.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CheckBox checkBox;
        CardView cardView;

        public ViewHolder(@NonNull View itemView, final ItemClickListener itemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo_id);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(PhotosActivity.selected_state)
                        imageView.setColorFilter(R.color.white);

                    itemClickListener.onItemClick(getAdapterPosition());
                }
            });

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public boolean onLongClick(View v) {
                    imageView.setColorFilter(R.color.white);
                    PhotosActivity.selected_state = true;
                    itemClickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });

        }
    }



}
