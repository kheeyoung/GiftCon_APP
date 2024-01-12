package com.example.giftcon_app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;


public class load {

    int pagenum;
    public load(int pagenum){
        this.pagenum=pagenum;
    }
    //로딩 기능
    public void loadCon(int ConNum, int Btunum, ImageButton button, Cursor C, Context TrackerService,DB.DbOpenHelper mDbOpenHelper){ //전체 콘 개수, 페이지 넘버,버튼 넘버,이미지 버튼,커서, 인텐트 , 닫을 DB
        try {
            if (ConNum >= Btunum + pagenum && pagenum >= 0) {
                int connum=Btunum + (pagenum * 9) - 1;
                C.moveToPosition(connum);
                //정보 받아오기
                String name=C.getString(1);
                String date=C.getString(2);
                String uri=C.getString(4);

                //파일 열어서 비트맵 가져오기
                File file = new File();
                Bitmap bitmap =file.StringToBitmap(file.FileRead(TrackerService,uri));

                button.setImageBitmap(bitmap);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =new Intent(TrackerService,conCheckView.class);
                        ArrayList<String> strList = new ArrayList<String>();
                        strList.add("리스트1");
                        strList.add("리스트2");
                        intent.putExtra("Name", name);
                        intent.putExtra("Date", date);
                        intent.putExtra("URI", uri);
                        intent.putExtra("ConNum",String.valueOf(connum));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        TrackerService.startActivity(intent);
                        mDbOpenHelper.close();

                    }
                });
            } else {
                button.setImageResource(0);
            }
        }
        catch (Exception e){
            button.setImageResource(0);
        }
    }

    //페이지 번호 ++
    public void setPagenum(int num){
        pagenum=pagenum+num;
    }
}
