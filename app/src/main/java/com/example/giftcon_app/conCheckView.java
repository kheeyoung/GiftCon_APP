package com.example.giftcon_app;

import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        String bitmapString=getIntent().getStringExtra("URI");
        String ConNum=getIntent().getStringExtra("ConNum");

        TextView NameTxt =(TextView) findViewById(R.id.Name);
        TextView DateTxt =(TextView) findViewById(R.id.DATE);

        NameTxt.setText(Name);
        DateTxt.setText(Date);
        File file =new File();

        try {
            //이미지 설정
            ImageView image=(ImageView) findViewById(R.id.imageView);
            Bitmap bitmap =file.StringToBitmap(file.FileRead(getApplicationContext(),bitmapString));
            image.setImageBitmap(bitmap);
        }
        catch (Exception e){
        }

        //삭제 기능
        Button deleteBtu =(Button) findViewById(R.id.deleteBtu);
        deleteBtu.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB 열기
                DB.DbOpenHelper mDbOpenHelper = new DB.DbOpenHelper(getApplicationContext());
                mDbOpenHelper.open();
                mDbOpenHelper.create();
                // 커서 설정
                Cursor iCursor = mDbOpenHelper.sortColumns();
                iCursor.moveToPosition(Integer.parseInt(ConNum));
                //정보 받아오기
                int ID=iCursor.getInt(0);
                String name=iCursor.getString(1);
                String date=iCursor.getString(2);
                String requestCode=iCursor.getString(3);
                String uri=iCursor.getString(4);

                //DB 삭제
                mDbOpenHelper.deleteColumn(ID);
                //알림 삭제
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.cancel(Integer.parseInt(requestCode)); // cancel(알림 특정 id)
                //파일 삭제
                try{
                    file.delete("/data/data/com.example.giftcon_app/files"+uri);
                }
                catch (Exception e){}

                mDbOpenHelper.close();
                iCursor.close();
                Intent intent =new Intent(getApplicationContext(),conCheck.class);
                startActivity(intent);

            }
        });

        //맵 보여주기
        Button map =(Button) findViewById(R.id.map);
        map.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),kakaoMap.class);
                startActivity(intent);
            }
        });

    }

}