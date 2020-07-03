package com.example.photogallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photogallery.R;
import com.example.photogallery.pojo.Album;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    List<Album> list;
    Context context;
    LayoutInflater inflater;
    ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    public AlbumAdapter(Context context, List<Album> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_album, parent, false);
        ViewHolder holder = new ViewHolder(view,itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = list.get(position);
        if(list.get(position).getTitle().equals("Camera")){
            holder.imageView.setImageResource(R.drawable.camera);
        }else if(list.get(position).getTitle().equals("Screenshots")){
            holder.imageView.setImageResource(R.drawable.screenshot);
        }else if(list.get(position).getTitle().equals("WhatsApp")){
            holder.imageView.setImageResource(R.drawable.whats);
        }
        holder.albumTitle.setText(album.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView albumTitle;
        public ImageView imageView;
        public ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView,final ItemClickListener listener) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.go_to_photo_activity);
            albumTitle = itemView.findViewById(R.id.album_title);
            imageView = itemView.findViewById(R.id.album_image);

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

        }

    }
}
