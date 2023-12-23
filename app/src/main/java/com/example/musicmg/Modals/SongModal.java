package com.example.musicmg.Modals;
public class SongModal {
    String songName,songCover,songId;
    String songUrl;



    public SongModal() {
    }
    public SongModal(String songName, String songCover, String songId,String songUrl) {
        this.songName = songName;
        this.songCover = songCover;
        this.songId = songId;
        this.songUrl = songUrl;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongCover() {
        return songCover;
    }

    public void setSongCover(String songCover) {
        this.songCover = songCover;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }
    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}
