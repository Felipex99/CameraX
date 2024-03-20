package com.example.camerax;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
public class MainActivity extends AppCompatActivity {
    private int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView img;
    private Uri u;
    private AppCompatTextView info;
    private ActivityResultLauncher<Void> imgUri = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap ->{
        if(bitmap != null){
            Toast.makeText(this, "BITMAT:HEIGHT"+bitmap.getHeight()+"BITMAP WIDTH:"+bitmap.getWidth(), Toast.LENGTH_SHORT).show();
            String s = "BITMAT:HEIGHT"+bitmap.getHeight()+"BITMAP WIDTH:"+bitmap.getWidth()+"\n"+bitmap;
            info.setText(s);
            img.setImageBitmap(bitmap);
        }
    });
    private ActivityResultLauncher<Uri> imgTake = registerForActivityResult(new ActivityResultContracts.TakePicture(), result ->{
        if(result!=null){
            Toast.makeText(this, "RESULT:"+result, Toast.LENGTH_SHORT).show();
            img.setImageURI(u);
        }
    });
    private ListenableFuture<ProcessCameraProvider> cameraProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton pbtn = findViewById(R.id.preview);
        AppCompatButton btn = findViewById(R.id.foto);
        info = findViewById(R.id.info);
        img = findViewById(R.id.img_foto);
        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {imgUri.launch(null);}
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // Se a permissão ainda não foi concedida, solicita ao usuário
                        int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
//                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                        ActivityCompat.requestPermissions(MainActivity.this,
//                                new String Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "NOME_DA_IMAGEM.jpg");
                    contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    ContentResolver resolver = getContentResolver();
                    u = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);//ERRO NESSA LINHA
                    Toast.makeText(MainActivity.this, "RESOLVER:"+u, Toast.LENGTH_SHORT).show();
                    imgTake.launch(u);
                }catch(Exception e){
                    e.printStackTrace();
                    info.setText(e.getMessage());
                }

            }
        });
    }
}