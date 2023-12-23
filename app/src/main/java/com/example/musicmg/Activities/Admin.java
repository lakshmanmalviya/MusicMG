package com.example.musicmg.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.musicmg.Fragments.DeleteOps;
import com.example.musicmg.Fragments.InsertOps;
import com.example.musicmg.Fragments.UpdateOps;
import com.example.musicmg.R;
import com.example.musicmg.databinding.ActivityAdminBinding;

public class Admin extends AppCompatActivity {
ActivityAdminBinding bnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getSupportActionBar().hide();
        InsertOps insert = new InsertOps();
        repFrag(insert);
        bnd.insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repFrag(insert);
            }
        });
        bnd.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repFrag(new DeleteOps());
            }
        });
        bnd.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repFrag(new UpdateOps());
            }
        });
    }

    public void repFrag(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.repFrag,fragment);
        transaction.commit();
    }
}