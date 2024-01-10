package com.example.giftcon_app;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class conCheckView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_check_view);
        //인텐트에서 데이터 받아오기
        String Name=getIntent().getStringExtra("Name");
        String Date=getIntent().getStringExtra("Date");
        String URI=getIntent().getStringExtra("URI");

        TextView NameTxt =(TextView) findViewById(R.id.Name);
        TextView DateTxt =(TextView) findViewById(R.id.DATE);
        TextView EtcTxt =(TextView) findViewById(R.id.Etc);

        NameTxt.setText(Name);
        DateTxt.setText(Date);
        EtcTxt.setText(URI);

        //이미지 설정
        ImageView image=(ImageView) findViewById(R.id.imageView);
        image.setImageURI(Uri.parse(URI));

    }
}