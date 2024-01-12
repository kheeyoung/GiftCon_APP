package com.example.giftcon_app;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class conCheck extends AppCompatActivity {

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_check);

        // 확인 - 이미지 버튼들 찾기
        ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        ImageButton imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        ImageButton imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        ImageButton imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
        ImageButton imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
        ImageButton imageButton9 = (ImageButton) findViewById(R.id.imageButton9);

        TextView TitleConCheck = (TextView) findViewById(R.id.TitleConCheck);

        // 로딩 기능 - 페이지 번호
        //int pagenum = 0;

        // 로딩 기능 - DB
        //DB 열기
        DB.DbOpenHelper mDbOpenHelper = new DB.DbOpenHelper(getApplicationContext());
        mDbOpenHelper.open();
        mDbOpenHelper.create();
        // 커서 설정
        Cursor iCursor = mDbOpenHelper.sortColumns();
        // 로딩 기능 - 저장된 콘 개수
        int ConNum= iCursor.getCount();

        iCursor.moveToFirst();

        //load 생성자
        load load=new load(0);

        load.loadCon(ConNum,1,imageButton1,iCursor,getApplicationContext(),mDbOpenHelper);
        load.loadCon(ConNum,2,imageButton2,iCursor,getApplicationContext(),mDbOpenHelper);
        load.loadCon(ConNum,3,imageButton3,iCursor,getApplicationContext(),mDbOpenHelper);
        load.loadCon(ConNum,4,imageButton4,iCursor,getApplicationContext(),mDbOpenHelper);
        load.loadCon(ConNum,5,imageButton5,iCursor,getApplicationContext(),mDbOpenHelper);
        load.loadCon(ConNum,6,imageButton6,iCursor,getApplicationContext(),mDbOpenHelper);
        load.loadCon(ConNum,7,imageButton7,iCursor,getApplicationContext(),mDbOpenHelper);
        load.loadCon(ConNum,8,imageButton8,iCursor,getApplicationContext(),mDbOpenHelper);
        load.loadCon(ConNum,9,imageButton9,iCursor,getApplicationContext(),mDbOpenHelper);

        //이전 버튼
        Button backBut=(Button) findViewById(R.id.back);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load.setPagenum(-1);
                load.loadCon(ConNum,1,imageButton1,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,2,imageButton2,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,3,imageButton3,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,4,imageButton4,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,5,imageButton5,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,6,imageButton6,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,7,imageButton7,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,8,imageButton8,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,9,imageButton9,iCursor,getApplicationContext(),mDbOpenHelper);
            }
        });
        //이후 버튼
        Button goBut=(Button) findViewById(R.id.go);
        goBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load.setPagenum(+1);
                load.loadCon(ConNum,1,imageButton1,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,2,imageButton2,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,3,imageButton3,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,4,imageButton4,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,5,imageButton5,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,6,imageButton6,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,7,imageButton7,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,8,imageButton8,iCursor,getApplicationContext(),mDbOpenHelper);
                load.loadCon(ConNum,9,imageButton9,iCursor,getApplicationContext(),mDbOpenHelper);


            }
        });



        //iCursor.close();
        //mDbOpenHelper.close();


    }
}