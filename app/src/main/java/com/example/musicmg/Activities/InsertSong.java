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
import com.example.musicmg.databinding.ActivityInsertSongBinding;
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

public class InsertSong extends AppCompatActivity {
    int i=0;
    int cnt =0;
    ActivityInsertSongBinding bnd;
    ActivityResultLauncher<String> launcher;
    String imageUrl ="";
    String songName ="songName";
    String songCover ="songCover";
    String songId ="songId";
    String FsongUrl ="songUrl";
    String songUrl ="";
    String categoryName = "categoryName";
    String categoryId = "categoryId";
    String allSong = "allSong";
    FirebaseDatabase database;
    FirebaseStorage storage;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityInsertSongBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        categoryName = getIntent().getStringExtra(categoryName);
        categoryId = getIntent().getStringExtra(categoryId);
        bnd.categoryText.setText("You are inserting the "+categoryName+" Songs");
        bnd.wait.setVisibility(View.GONE);
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                bnd.songCover.setImageURI(uri);
                bnd.wait.setVisibility(View.VISIBLE);
                bnd.insertSong.setVisibility(View.GONE);
                final StorageReference reference = storage.getReference().child(allSong).child(allSong+"-"+ (i) +new Date().getTime()+".mp3");
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if(cnt==0){
                                    songUrl = uri.toString();
                                    Toast.makeText(getApplicationContext(), ""+songUrl, Toast.LENGTH_SHORT).show();
                                    bnd.wait.setVisibility(View.GONE);
                                    bnd.insertSong.setVisibility(View.VISIBLE);
                                    bnd.songCoverBrw.setVisibility(View.VISIBLE);
                                    bnd.songName.setVisibility(View.GONE);
                                }
                                else{
                                    imageUrl = uri.toString();
                                    Toast.makeText(getApplicationContext(), ""+imageUrl, Toast.LENGTH_SHORT).show();
                                    bnd.wait.setVisibility(View.GONE);
                                    bnd.insertSong.setVisibility(View.VISIBLE);
                                    bnd.songName.setVisibility(View.VISIBLE);
                                }

                            }
                        });
                    }
                });
            }
        });
        bnd.brwSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  ++i; cnt=0;
                  bnd.songCoverBrw.setVisibility(View.GONE);
                  bnd.songName.setVisibility(View.GONE);
                  bnd.insertSong.setVisibility(View.GONE);
                launcher.launch("audio/*");
            }
        });
        bnd.songCoverBrw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  ++i; cnt=1;
                launcher.launch("image/*");
            }
        });
        bnd.insertSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.songName.getText().toString().isEmpty()||imageUrl.isEmpty()||songUrl.isEmpty()){
                    Toast.makeText(getApplicationContext(), "The Song|Url Name is empty", Toast.LENGTH_SHORT).show();
                }
                else{

                    myref = database.getReference().child(allSong).child(categoryId).child(categoryName).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(songName,bnd.songName.getText().toString());
                    map.put(FsongUrl,songUrl);
                    map.put(songCover,imageUrl);
                    map.put(songId,myref.getKey());
                     myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The Song is inserted", Toast.LENGTH_SHORT).show();
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