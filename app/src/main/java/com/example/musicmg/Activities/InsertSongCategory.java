package com.example.musicmg.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.musicmg.R;
import com.example.musicmg.databinding.ActivityInsertSongCategoryBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InsertSongCategory extends AppCompatActivity {
    ActivityInsertSongCategoryBinding bnd;
    ActivityResultLauncher<String> launcher;
    FirebaseDatabase database;
    FirebaseStorage storage;
    DatabaseReference myref;
    String allSong = "allSong";
    String categoryImg ="categoryImg";
    String categoryName ="categoryName";
    String categoryLongName ="categoryLongName";
    String categoryId ="categoryId";
    String imageUrl ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityInsertSongCategoryBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        bnd.waitCat.setVisibility(View.GONE);
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                bnd.categoryImg.setImageURI(uri);
                bnd.waitCat.setVisibility(View.VISIBLE);
//                bnd.insertCat.setVisibility(View.GONE);
                final StorageReference reference = storage.getReference().child(allSong).child(allSong+"category"+"-"+new Date().getTime());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                    imageUrl = uri.toString();
                                    Toast.makeText(getApplicationContext(), ""+imageUrl, Toast.LENGTH_SHORT).show();
                                    bnd.waitCat.setVisibility(View.GONE);
                                    bnd.insertCat.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
        });
        bnd.brCatImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnd.insertCat.setVisibility(View.GONE);
                launcher.launch("image/*");
            }
        });
        bnd.insertCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.categoryName.getText().toString().isEmpty()||bnd.categoryLongName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "The Category Name is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    myref = database.getReference().child(allSong).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(categoryName,bnd.categoryName.getText().toString());
                    map.put(categoryLongName,bnd.categoryLongName.getText().toString());
                    map.put(categoryImg,imageUrl);
                    map.put(categoryId,myref.getKey());
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The category is inserted", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed"+e, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}