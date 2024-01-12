package com.example.giftcon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMeue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_meue);
        // 등록 화면
        Button conreg=(Button) findViewById(R.id.conreg);
        conreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),conReg.class);
                startActivity(intent);
            }
        });

        // 확인 화면
        Button viewcon=(Button) findViewById(R.id.viewcon);
        viewcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),conCheck.class);
                startActivity(intent);
            }
        });


    }
}