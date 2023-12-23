package com.example.musicmg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.musicmg.Activities.Admin;
import com.example.musicmg.Activities.InsertSong;
import com.example.musicmg.Activities.ListenSongs;
import com.example.musicmg.Activities.MyHandler;
import com.example.musicmg.Activities.Test;
import com.example.musicmg.Adapters.CategoryAdapter;
import com.example.musicmg.Classes.RecyclerItemClickListener;
import com.example.musicmg.Modals.CategoryModal;
import com.example.musicmg.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding bnd;
    CategoryAdapter adapter;
    ArrayList<CategoryModal> clist;
    LinearLayoutManager layoutManager;
    FirebaseDatabase myDatabase;
    final String allSong = "allSong";
    String categoryName = "categoryName";
    String categoryId = "categoryId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getSupportActionBar().hide();
        myDatabase = FirebaseDatabase.getInstance();
        clist= new ArrayList<>();
        adapter = new CategoryAdapter(clist,getApplicationContext());
        bnd.categoryRec.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        bnd.categoryRec.setLayoutManager(layoutManager);
        myDatabase.getReference().child(allSong)
                .addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            clist.clear();
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                CategoryModal cmodal = dataSnapshot.getValue(CategoryModal.class);
                                clist.add(cmodal);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Not a single playlist  is there", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                });

            bnd.temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Admin.class));
//                startActivity(new Intent(getApplicationContext(), MyHandler.class));
            }
        });
        bnd.categoryRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.categoryRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CategoryModal cmodal = clist.get(position);
                Intent intent = new Intent(getApplicationContext(), ListenSongs.class);
                intent.putExtra(categoryName,cmodal.getCategoryName());
                intent.putExtra(categoryId,cmodal.getCategoryId());
//               Toast.makeText(getApplicationContext(), cmodal.getCategoryName()+" clicked "+position, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }
}