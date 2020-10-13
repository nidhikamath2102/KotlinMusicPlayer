package com.nidhikamath.kotlinmusicplayerdemo.musicjava;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindMusic {
    private static final String TAG = "FIND_MUSIC";

    private ContentResolver contentResolver;
    private List<Music> musicList = new ArrayList<>();
    private Random random = new Random();

    public FindMusic(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void prepare() {
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor c = contentResolver.query(uri, null, MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null);

        if (c == null) {
            return;
        }
        if (!c.moveToFirst()) {
            return;
        }

        int artistColumn = c.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int titleColumn = c.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int albumColumn = c.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int albumArtColumn = c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        int durationColumn = c.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int idColumn = c.getColumnIndex(MediaStore.Audio.Media._ID);

        do {
            musicList.add(new Music(c.getLong(idColumn), c.getString(artistColumn), c.getString(titleColumn), c.getString(albumColumn),
                    c.getLong(durationColumn), c.getLong(albumArtColumn)));
        } while (c.moveToNext());

    }

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    public Music getRandomSong() {
        if (musicList.size() <= 0) return null;
        return musicList.get(random.nextInt(musicList.size()));
    }

    public List<Music> getAllSongs() {
        return musicList;
    }

}
