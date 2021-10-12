package com.example.myapplication;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class Info2Activity extends AppCompatActivity {

    Button next;
    EditText name,hbd,phone,hbd_long;
    RadioButton r_hbd_p,r_hbd_m,r_dif,r_ssam;
    RadioGroup info_rg1,info_rg2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info2);

        next = findViewById(R.id.btn_info_next);
        name = findViewById(R.id.edit_name);
        hbd = findViewById(R.id.edit_hbd);
        phone = findViewById(R.id.edit_phone);
        hbd_long = findViewById(R.id.edit_hbd_long);

        r_hbd_p = findViewById(R.id.rbtn_hbd_plus);
        r_hbd_m = findViewById(R.id.rbtn_hbd_min);
        r_dif = findViewById(R.id.rbtn_dif);
        r_ssam = findViewById(R.id.rbtn_ssam);

        info_rg1 = findViewById(R.id.info_rg1);
        info_rg2 = findViewById(R.id.info_rg2);






    }

}

