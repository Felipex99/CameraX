package com.example.camerax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ListenableFuture<ProcessCameraProvider> cameraProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton btn = findViewById(R.id.foto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intet = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(getIntent().resolveActivity(getPackageManager())!=null){
                    File photoFile = null;
                    try{
                        photoFile = createImageFile();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    Uri uri =
                }
            }
        });
//        cameraProvider = ProcessCameraProvider.getInstance(MainActivity.this);
//        startCamera();
//    }
//    public void startCamera(){
//        cameraProvider.addListener(()-> {
//            try {
//                ProcessCameraProvider cameraProvider1 = cameraProvider.get();
//
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, ContextCompat.getMainExecutor(this));
//    }
//
//    public void bindPreview(ProcessCameraProvider cameraProvider){
//        PreviewView previewView = findViewById(R.id.previewView);
//        Preview preview = new Preview.Builder().build();
//        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
//        preview.setSurfaceProvider(previewView.getSurfaceProvider());
//        cameraProvider.bindToLifecycle(this,cameraSelector,preview);
    }
}