package com.example.musicmg.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.musicmg.Adapters.CategoryAdapter;
import com.example.musicmg.Classes.RecyclerItemClickListener;
import com.example.musicmg.Modals.CategoryModal;
import com.example.musicmg.databinding.ActivityAdminCategoryBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCategory extends AppCompatActivity {
    ActivityAdminCategoryBinding bnd;
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
        bnd = ActivityAdminCategoryBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getSupportActionBar().hide();
        myDatabase = FirebaseDatabase.getInstance();
        clist= new ArrayList<>();
        adapter = new CategoryAdapter(clist,getApplicationContext());
        bnd.adminCategoryRec.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        bnd.adminCategoryRec.setLayoutManager(layoutManager);
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
                bnd.adminCategoryRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.adminCategoryRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CategoryModal cmodal = clist.get(position);
                Intent intent = new Intent(getApplicationContext(), InsertSong.class);
                intent.putExtra(categoryName,cmodal.getCategoryName());
                intent.putExtra(categoryId,cmodal.getCategoryId());
                Toast.makeText(getApplicationContext(), cmodal.getCategoryName()+" clicked "+position, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));

    }
}