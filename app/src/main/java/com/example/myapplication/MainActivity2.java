package com.example.myapplication;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    private final int PERMISSIONS_REQUEST_RESULT = 100;

    MyView myView;

    LinearLayout llFingerPainting;
    TextView tvFingerPainting;
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFingerPainting = (TextView)findViewById(R.id.tv_finger_painting);
        btSave = (Button)findViewById(R.id.bt_submit);
        btSave.setOnClickListener(onClickListener);

        llFingerPainting = (LinearLayout)findViewById(R.id.ll_finger_painting);
        myView = new MyView(MainActivity2.this);
        llFingerPainting.addView(myView);

        requestPermissionCamera();


    }

    View.OnClickListener onClickListener = (view) -> {

        switch (view.getId()){
            case R.id.bt_submit:
                Bitmap bitmap = myView.getCanvasBitmap();
                File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/shuvic/");
                File fileCacheItem = new File(path.getAbsolutePath() + "/RCFT");
                OutputStream out = null;
                if (fileCacheItem.exists()) {
                    Log.i("fileCacheItem 존재", fileCacheItem.getAbsolutePath());
                }
                try {
                    Files.createDirectory(path.toPath());
                    Log.i("fileCacheItem 없음", "파일 상위 디렉토리 생성");
                    path.mkdirs();
                    System.out.println("저장");
                    fileCacheItem.createNewFile();
                    out = new FileOutputStream(fileCacheItem);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, out);
                } catch (Exception e) {
                    Log.e("path.mkdirs", e.toString());
                    e.printStackTrace();
                }
            }

    };

    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }









//        String root = Environment.getExternalStorageDirectory().toString();
//        File myDir = new File(getFilesDir(),"file.bin");
//
//        myDir.mkdirs();
//
//        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
//        String fname = "finger_"+ timeStamp + ".jpg";
//
//        File file = new File(fname);
//
//
//        try {
//
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG,10,out);
//            Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();;
//            out.flush();
//            out.close();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        if(file.exists()){ file.delete();}
//    }
    public boolean requestPermissionCamera(){
        int sdkVersion = Build.VERSION.SDK_INT;

        if(sdkVersion >= Build.VERSION_CODES.M){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.
                        READ_EXTERNAL_STORAGE)||ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.
                                READ_EXTERNAL_STORAGE},PERMISSIONS_REQUEST_RESULT);
                }
                }
            }
            return true;
        }
        @Override
         public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults) {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (PERMISSIONS_REQUEST_RESULT == requestCode) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("permission", "agree");

                } else {
                    Log.i("permission", "disagree");
                }
                return;
            }
    }
}

