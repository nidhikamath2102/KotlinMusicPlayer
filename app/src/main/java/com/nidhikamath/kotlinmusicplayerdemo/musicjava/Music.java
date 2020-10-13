package com.nidhikamath.kotlinmusicplayerdemo.musicjava;

import android.content.ContentUris;
import android.net.Uri;

public class Music {
    long id;
    String artist;
    String title;
    String album;
    long albumId;
    long duration;

    public Music(long id, String artist, String title, String album, long duration, long albumId) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.album = album;
        this.duration = duration;
        this.albumId = albumId;
    }

    public long getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public long getDuration() {
        return duration;
    }

    public long getAlbumId() {
        return albumId;
    }

    public Uri getURI() {
        return ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
    }

    public Uri getAlbumArt() {
        try {
            Uri genericArtUri = Uri.parse("content://media/external/audio/albumart");
            Uri actualArtUri = ContentUris.withAppendedId(genericArtUri, albumId);
            return actualArtUri;
        } catch(Exception e) {
            return null;
        }
    }
}
