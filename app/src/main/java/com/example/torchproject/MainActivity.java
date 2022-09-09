package com.example.torchproject;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton imageButton;
    boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.torchbtn);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashlight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Camera permission required", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void runFlashlight() {
        imageButton.setOnClickListener(v -> {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            if (!state)
            {

                try {
                    String cameraId=cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId,state=true);
                    state= true;
                    imageButton.setImageResource(R.drawable.torch_on);
                }
                catch (CameraAccessException ignored)
                {}
            }
            else
            {

                try {
                    String cameraId=cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, state=false);
                    state=false;
                    imageButton.setImageResource(R.drawable.torch_off);
                }
                catch (CameraAccessException ignored)
                {}
            }
        });
    }
}
