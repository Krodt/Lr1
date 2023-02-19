package com.github.rsoi.domain;

public class Song {

    private String songName;
    private String songAuthor;

    public Song(String songName, String songAuthor){
        this.songName = songName;
        this.songAuthor = songAuthor;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String name) {
        this.songName = songName;
    }

    public String getSongAuthor() {
        return songAuthor;
    }

    public void setSongAuthor(String author) {
        this.songAuthor = songAuthor;
    }

}
