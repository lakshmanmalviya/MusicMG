package com.example.musicmg.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicmg.Activities.AdminCategory;
import com.example.musicmg.Activities.InsertSongCategory;
import com.example.musicmg.databinding.FragmentInsertOpsBinding;

public class InsertOps extends Fragment {
    FragmentInsertOpsBinding bnd;
    public InsertOps() {  }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         bnd = FragmentInsertOpsBinding.inflate(inflater,container,false);
        bnd.insertSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getContext(), AdminCategory.class));
            }
        });
        bnd.categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InsertSongCategory.class));
            }
        });
         return bnd.getRoot();
    }
}