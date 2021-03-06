package com.nidhikamath.kotlinmusicplayerdemo

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nidhikamath.kotlinmusicplayerdemo.helper.FindMusic
import com.nidhikamath.kotlinmusicplayerdemo.helper.Music
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE: Int = 21
    private var  mediaPlayer: MediaPlayer?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        } else {
            initPlayer()
        }

    }

    private fun initPlayer(){
        val music = FindMusic(contentResolver)
        music.prepare()
        val musicList = music.allSongs
        Log.d("TAG", "songs " + musicList.size + " " )

        playRandom(musicList)
        shuffle?.setOnClickListener{
            playRandom(musicList)
        }
        play?.setOnClickListener {
            playOrPause()
        }
    }

    private fun playOrPause() {
        val isPlaying = mediaPlayer?.isPlaying
        if(isPlaying!!){
            mediaPlayer?.pause()
            play?.setImageResource(R.drawable.play)
        }else{
            mediaPlayer?.start()
            play?.setImageResource(R.drawable.pause)
        }

    }

    private fun playRandom(musicList: List<Music>) {
        Collections.shuffle(musicList)
        val music = musicList[0]
        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer.create(this, music.getURI())
        mediaPlayer?.setOnCompletionListener {
            playRandom(musicList)
        }
        image?.setImageURI(music.getAlbumArt())
        songName?.text = music.title
        artist?.text = music.artist

        mediaPlayer?.start()
        play?.setImageResource(R.drawable.pause)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initPlayer()
            } else {
                val myToast = Toast.makeText(applicationContext,resources.getString(R.string.acceptpermissions),Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.CENTER,0,0)
                myToast.show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}