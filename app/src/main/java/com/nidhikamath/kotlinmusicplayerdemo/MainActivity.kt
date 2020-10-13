package com.nidhikamath.kotlinmusicplayerdemo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nidhikamath.kotlinmusicplayerdemo.musicjava.FindMusic

class MainActivity : AppCompatActivity() {
    private var image: ImageView? = null
    private var songName: TextView? = null
    private var artist: TextView? = null
    private var play: ImageButton? = null
    private var shuffle: ImageButton? = null
    val PERMISSION_REQUEST_CODE: Int = 21


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
        val musicList = FindMusic(contentResolver)
        musicList.prepare()
        musicList.allSongs
        Log.d("TAG", "songs " + musicList.allSongs.size + " " )


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initPlayer();
            } else {
                val myToast = Toast.makeText(applicationContext,resources.getString(R.string.acceptpermissions),Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.LEFT,200,200)
                myToast.show()
                finish()
            }
        }
    }
}