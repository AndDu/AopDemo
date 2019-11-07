package com.yingze.aoptest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Permission({Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE})
    public void onClick(View view) {
        Toast.makeText(this, "....", Toast.LENGTH_SHORT).show();
    }


    @PermissionDenied
    public void ondeny() {
        Toast.makeText(this, "ondeny", Toast.LENGTH_SHORT).show();
    }

    @PermissionCanceled
    public void onCancle() {
        Toast.makeText(this, "onCancle", Toast.LENGTH_SHORT).show();
    }
}
