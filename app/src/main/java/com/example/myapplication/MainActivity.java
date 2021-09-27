package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

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
        myView = new MyView(MainActivity.this);
        llFingerPainting.addView(myView);

        requestPermissionCamera();
    }

    View.OnClickListener onClickListener = (view) -> {

        switch (view.getId()){
            case R.id.bt_submit:
                Bitmap bitmap = myView.getCanvasBitmap();
                if(isExternalStorageWritable()){
                    saveImage(bitmap);
            }
                break;
        }
    };


    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }



    //jpet로 변환하여 이름 설정후 저
    private void saveImage(Bitmap finalBitmap){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/RCFT";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
            Toast.makeText(this,"폴더가 생성되었습니다.",Toast.LENGTH_SHORT).show();
        }

        SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        FileOutputStream fos = null;


        try {

            fos = new FileOutputStream(path+"/RCFT"+day.format(date)+".jpeg");
            finalBitmap.compress(Bitmap.CompressFormat.JPEG,10,fos);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" +
                    path+"/RCFT"+day.format(date)+".JPEG")));
            Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();
            fos.flush();
            fos.close();


        }catch (FileNotFoundException e) {
            System.out.println("File does not exist");
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

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

