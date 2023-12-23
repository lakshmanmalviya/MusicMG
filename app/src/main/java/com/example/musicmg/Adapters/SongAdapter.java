package com.example.musicmg.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicmg.Modals.CategoryModal;
import com.example.musicmg.Modals.SongModal;
import com.example.musicmg.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
   ArrayList<SongModal> slist;
   Context context;
    public SongAdapter(ArrayList<SongModal> slist, Context context) {
        this.slist = slist;
        this.context = context;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_row,parent,false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {
        SongModal sModal = slist.get(position);
        if (sModal.getSongCover().isEmpty()){
            sModal.setSongCover("https://firebasestorage.googleapis.com/v0/b/musicmg-9d5bf.appspot.com/o/bollywoodSong%2FbollywoodSong-41673627561042?alt=media&token=d834c668-ed92-45bf-9302-aeb114c196ed");
        }
        Picasso.get().load(sModal.getSongCover()).placeholder(R.drawable.placeholder).into(holder.songCover);
        holder.songName.setText(sModal.getSongName());
        holder.songName.setSelected(true);
        holder.songClickLayout.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.animone));

//        holder.songClickLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), ""+sModal.getSongName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    class SongHolder extends RecyclerView.ViewHolder{
        ImageView songCover;
        TextView songName;
        LinearLayout songClickLayout;
        public SongHolder(@NonNull View itemView) {
            super(itemView);
            songCover = itemView.findViewById(R.id.rowSongCover);
            songName = itemView.findViewById(R.id.rowSongName);
            songClickLayout = itemView.findViewById(R.id.songClickLayout);
        }
    }
}
