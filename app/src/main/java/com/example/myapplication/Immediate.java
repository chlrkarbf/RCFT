package com.example.myapplication;
import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Immediate extends AppCompatActivity {

    MyView myView;

    Button btnSave;
    LinearLayout ll_finger_painting;
    TextView tvFingerPainting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediate);


        ll_finger_painting = (LinearLayout) findViewById(R.id.ll_finger_painting);
        myView = new MyView(Immediate.this);
        btnSave = (Button) findViewById(R.id.btn_saveimage);
        tvFingerPainting = (TextView) findViewById(R.id.tv_finger_painting);
        ll_finger_painting.addView(myView);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = myView.getCanvasBitmap();
                saveImageToGalllery(bitmap);
            }
        });
    }

    private void saveImageToGalllery(Bitmap bitmap) {
        OutputStream fos;
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        String imageFileName = "finger_" + timeStamp +"Immediate"+ ".jpg";

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageFileName + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/.jpeg");
                //Create a subfolder in Pictures Directory
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + File.separator + "Immediate");
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);


                Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Toast.makeText(this, "이미지저장 오류", Toast.LENGTH_SHORT).show();

        }
    }
}