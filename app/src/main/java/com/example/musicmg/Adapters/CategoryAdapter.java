package com.example.musicmg.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicmg.Modals.CategoryModal;
import com.example.musicmg.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatHolder> {
   ArrayList<CategoryModal> clist;
   Context context;
    public CategoryAdapter(ArrayList<CategoryModal> clist, Context context) {
        this.clist = clist;
        this.context = context;
    }

    @NonNull
    @Override
    public CatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_row,parent,false);
        return new CatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatHolder holder, int position) {
        CategoryModal cModal = clist.get(position);
        if (cModal.getCategoryImg().isEmpty()){
            cModal.setCategoryImg("https://firebasestorage.googleapis.com/v0/b/musicmg-9d5bf.appspot.com/o/bollywoodSong%2FbollywoodSong-41673627561042?alt=media&token=d834c668-ed92-45bf-9302-aeb114c196ed");
        }
        Picasso.get().load(cModal.getCategoryImg()).placeholder(R.drawable.placeholder).into(holder.categoryImg);
        holder.categoryLongName.setText(cModal.getCategoryLongName());
        holder.rowCatLayout.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.animone));

    }

    @Override
    public int getItemCount() {
        return clist.size();
    }

    class  CatHolder extends RecyclerView.ViewHolder{
        ImageView  categoryImg;
        TextView categoryLongName;
        LinearLayout rowCatLayout;
        public CatHolder(@NonNull View itemView) {
            super(itemView);
            categoryLongName = itemView.findViewById(R.id.rowCatLongName);
            categoryImg = itemView.findViewById(R.id.rowCatImg);
            rowCatLayout = itemView.findViewById(R.id.rowCatLayout);
        }
    }
}
