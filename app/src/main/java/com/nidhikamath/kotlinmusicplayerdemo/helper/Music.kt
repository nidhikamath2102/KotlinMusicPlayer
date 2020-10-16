package com.nidhikamath.kotlinmusicplayerdemo.helper

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore

class Music(
    var id: Long,
    var artist: String,
    var title: String,
    var album: String,
    var duration: Long,
    var albumId: Long
){
    fun getURI(): Uri {
        return ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
        )
    }

    fun getAlbumArt(): Uri? {
        return try {
            val genericArtUri = Uri.parse("content://media/external/audio/albumart")
            ContentUris.withAppendedId(genericArtUri, albumId)
        } catch (e: Exception) {
            null
        }
    }
}


