package com.nidhikamath.kotlinmusicplayerdemo.helper

import android.content.ContentResolver
import android.provider.MediaStore
import com.nidhikamath.kotlinmusicplayerdemo.helper.Music
import java.util.*

class FindMusic(val contentResolver: ContentResolver) {
    private val musicList: MutableList<Music> = ArrayList()
    private val random = Random()
    fun prepare() {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val c = contentResolver.query(uri, null, MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null)?: return
        if (!c.moveToFirst()) {
            return
        }
        val artistColumn = c.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        val titleColumn = c.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val albumColumn = c.getColumnIndex(MediaStore.Audio.Media.ALBUM)
        val albumArtColumn = c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
        val durationColumn = c.getColumnIndex(MediaStore.Audio.Media.DURATION)
        val idColumn = c.getColumnIndex(MediaStore.Audio.Media._ID)
        do {
            musicList.add(
                Music(
                    c.getLong(idColumn),
                    c.getString(artistColumn),
                    c.getString(titleColumn),
                    c.getString(albumColumn),
                    c.getLong(durationColumn),
                    c.getLong(albumArtColumn)
                )
            )
        } while (c.moveToNext())
    }

    val randomSong: Music?
        get() = if (musicList.size <= 0) null else musicList[random.nextInt(musicList.size)]

    val allSongs: List<Music>
        get() = musicList

    companion object {
        private const val TAG = "FIND_MUSIC"
    }
}