package com.example.musicmg.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.musicmg.R;
import com.example.musicmg.databinding.ActivityMainBinding;
import com.example.musicmg.databinding.ActivityTestBinding;

import java.io.IOException;

public class Test extends AppCompatActivity {
  ActivityTestBinding bnd;
  int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        MediaPlayer mp = new MediaPlayer();
        String arr[] = new String[20];
        arr[0] ="https://firebasestorage.googleapis.com/v0/b/musicmg-9d5bf.appspot.com/o/allSong%2FallSong-11673674097967.mp3?alt=media&token=2e78a858-c955-40b6-8d34-ed6c14ed55b1";
        arr[1] ="https://firebasestorage.googleapis.com/v0/b/musicmg-9d5bf.appspot.com/o/allSong%2FallSong-11673674097967.mp3?alt=media&token=2e78a858-c955-40b6-8d34-ed6c14ed55b1";
        arr[2] ="https://firebasestorage.googleapis.com/v0/b/musicmg-9d5bf.appspot.com/o/allSong%2FallSong-11673674097967.mp3?alt=media&token=2e78a858-c955-40b6-8d34-ed6c14ed55b1";
        arr[3] ="https://firebasestorage.googleapis.com/v0/b/musicmg-9d5bf.appspot.com/o/allSong%2FallSong-11673674097967.mp3?alt=media&token=2e78a858-c955-40b6-8d34-ed6c14ed55b1";
        arr[4] ="https://firebasestorage.googleapis.com/v0/b/musicmg-9d5bf.appspot.com/o/allSong%2FallSong-11673674097967.mp3?alt=media&token=2e78a858-c955-40b6-8d34-ed6c14ed55b1";
        arr[5] ="https://firebasestorage.googleapis.com/v0/b/musicmg-9d5bf.appspot.com/o/allSong%2FallSong-11673674097967.mp3?alt=media&token=2e78a858-c955-40b6-8d34-ed6c14ed55b1";
        arr[6] ="https://firebasestorage.googleapis.com/v0/b/musicmg-9d5bf.appspot.com/o/allSong%2FallSong-11673674097967.mp3?alt=media&token=2e78a858-c955-40b6-8d34-ed6c14ed55b1";
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String songPath = "android.resource://"+getPackageName()+"/raw/aawara";
//        String apath = "https://firebasestorage.googleapis.com/v0/b/musicmg-9d5bf.appspot.com/o/bollywoodSong%2FbollywoodSong-11673622106936?alt=media&token=d013b54a-4a5f-410b-982c-b55037a44cf8";
        Uri uri =  Uri.parse(songPath);
        try {
            mp.setDataSource(getApplicationContext(),uri);
            mp.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
        bnd.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });
        bnd.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mp.start();
            }
        });
        bnd.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mp.pause();
            mp.seekTo(0);
            }
        });




    }
}