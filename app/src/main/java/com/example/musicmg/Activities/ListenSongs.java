package com.example.musicmg.Activities;

import android.media.AudioManager;
import android.media.MediaCasException;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.musicmg.Adapters.SongAdapter;
import com.example.musicmg.Classes.RecyclerItemClickListener;
import com.example.musicmg.Modals.SongModal;
import com.example.musicmg.R;
import com.example.musicmg.databinding.ActivityListenSongsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListenSongs extends AppCompatActivity {
   ActivityListenSongsBinding bnd;
   private Handler mHandler = new Handler();
    SongAdapter adapter;
    SongModal smodal;
    int totalSize =0;
    int index;
    ArrayList<SongModal> slist;
    LinearLayoutManager layoutManager;
    FirebaseDatabase myDatabase;
    final String allSong = "allSong";
    String categoryName = "categoryName";
    String categoryId = "categoryId";
    String songUrl = "";
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityListenSongsBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getSupportActionBar().hide();
         mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        myDatabase = FirebaseDatabase.getInstance();
        categoryName = getIntent().getStringExtra(categoryName);
        categoryId = getIntent().getStringExtra(categoryId);
        slist= new ArrayList<>();
        adapter = new SongAdapter(slist,getApplicationContext());
        bnd.listenSongRec.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        bnd.listenSongRec.setLayoutManager(layoutManager);
        myDatabase.getReference().child(allSong).child(categoryId).child(categoryName)
                .addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            slist.clear();
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                SongModal smodal = dataSnapshot.getValue(SongModal.class);
                                slist.add(smodal);
                            }
                            adapter.notifyDataSetChanged();
                            totalSize = slist.size();
                        }
                        else{
                            bnd.seekLayout.setVisibility(View.GONE);
                            bnd.seekCntLayout.setVisibility(View.GONE);
                            bnd.seeText.setText("No song is Available in this section");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                });
        bnd.listenSongRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.listenSongRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                   smodal = slist.get(position);
                   index = position;
                  songUrl = smodal.getSongUrl();
                  if (songUrl.isEmpty()){
                      Toast.makeText(getApplicationContext(), "No song is Available ", Toast.LENGTH_SHORT).show();
                  }
                else{
                    Uri uri = Uri.parse(songUrl);
                    playMusic(uri);
                }
            }
            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        bnd.playSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(songUrl.isEmpty()){
                    smodal = slist.get(0);
                    songUrl = smodal.getSongUrl();
                    Uri uri = Uri.parse(songUrl);
                    playMusic(uri);
                    rotateNow();
                }
              else if(mp.isPlaying()){
                mp.pause();
                bnd.playSong.setImageResource(R.drawable.play);
                bnd.rotateImg.setVisibility(View.GONE);
               }
               else{
                   mp.start();
                   bnd.playSong.setImageResource(R.drawable.pause);
               }
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                bnd.playSong.setImageResource(R.drawable.play);
                playNext();
            }
        });
        bnd.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
                rotateNow();
            }
        });
        bnd.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   playPrev();
                   rotateNow();
            }
        });

//        bnd.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if(fromUser){
//                    bnd.seekBar.setMax(mp.getDuration()/1000);
//                    mp.seekTo(progress);
//                    bnd.seekBar.setProgress(progress);
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(getApplicationContext(), ""+progress, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//        });
    }

    @Override
    public void onBackPressed() {
         mp.release();
        super.onBackPressed();
    }
    public void playMusic(Uri uri){
           mp.reset();
         try {
             mp.setDataSource(getApplicationContext(), uri);
             mp.prepare();
             mp.start();
             bnd.playSong.setImageResource(R.drawable.pause);
//             bnd.endTime.setText(((mp.getDuration()/1000)/60)+":"+((mp.getDuration()/1000)%60));
         }catch (Exception e){
             e.printStackTrace();
             Toast.makeText(getApplicationContext(), "loading failed", Toast.LENGTH_SHORT).show();
             playMusic(uri);
         }
    }
    public void playPrev(){
        if(index>=1){
            index--;
        }
        else{
            index=0;
        }
        smodal = slist.get(index);
        songUrl  = smodal.getSongUrl();
        Uri uri = Uri.parse(songUrl);
        playMusic(uri);
    }
    public  void playNext(){
        if(index < slist.size()-1){
            index++;
        }
        else{
            index=0;
        }
        smodal = slist.get(index);
        songUrl  = smodal.getSongUrl();
        Uri uri = Uri.parse(songUrl);
        playMusic(uri);
    }
    public void rotateNow(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
      if(mp.isPlaying()){
          bnd.rotateImg.setVisibility(View.VISIBLE);
          bnd.rotateImg.startAnimation(animation);
        }

      else {
          bnd.rotateImg.setVisibility(View.GONE);
      }
    }
//private Runnable updateSongTime = new Runnable() {
//    @Override
//    public void run() {
////        startTime = mp.getCurrentPosition();
////        bnd.startTime.setText(String.format("%d min, %d sec",
////                TimeUnit.MILLISECONDS.toMinutes((long)startTime),
////                TimeUnit.MILLISECONDS.toSeconds((long) startTime)-
////                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))
////                ));
////        bnd.seekBar.setProgress((int)startTime);
//
//        mHandler.postDelayed(this,1000);
//    }
//};
}