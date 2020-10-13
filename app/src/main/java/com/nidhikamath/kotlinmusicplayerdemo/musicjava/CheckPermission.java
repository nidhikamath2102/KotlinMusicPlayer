package com.nidhikamath.kotlinmusicplayerdemo.musicjava;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CheckPermission {
    public static final int STORAGE_PERMISSION_CODE = 21;

    public static boolean checkPermission(AppCompatActivity activity) {
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            return false;
        }
    }

    public static boolean canAccess(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == CheckPermission.STORAGE_PERMISSION_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                return true;
        }
        return false;
    }
}
