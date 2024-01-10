package com.example.giftcon_app;

import android.app.AlarmManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {

    //알람
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //타이틀 텍스트 뷰
        TextView Title =(TextView)findViewById(R.id.timeSettingNow);

        //타임 피커
        TimePicker TimePicker=(TimePicker) findViewById(R.id.Time);
        int PickHour=TimePicker.getHour();
        int PickMinute=TimePicker.getMinute();

         String Time= String.valueOf(PickHour+":"+PickMinute+":00");

        //DB 생성
        SettingDB.DbOpenHelper SmDbOpenHelper = new SettingDB.DbOpenHelper(getApplicationContext());
        SmDbOpenHelper.open();
        SmDbOpenHelper.create();

        String Timeset=null;

        // 커서 설정
        Cursor iCursor = SmDbOpenHelper.sortColumn();
        // 저장된 콘 개수 받아오기
        int TimeNum= iCursor.getCount();
        if(TimeNum>=1) {
            iCursor.moveToFirst();
            Timeset = iCursor.getString(1);
            Title.setText(Timeset);
        }
        else {Title.setText("저장된 시간이 없습니다.");}
        iCursor.close();

        Button TimeSetiingBut=(Button) findViewById(R.id.TimeSetiingBut);
        TimeSetiingBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), Time, Toast.LENGTH_SHORT).show(); //시간 확인
                try {
                    NotificationSetting N=new NotificationSetting();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        //N.setNotice(Time,getApplicationContext(),alarmManager); //알림 설정
                        Toast.makeText(getApplicationContext(), "알림 설정 성공", Toast.LENGTH_SHORT).show();

                        if(TimeNum>=1) { //이미 저장된 시간이 있으면 업데이트
                            SettingDB.DbOpenHelper.updateColumn(0, Time); //DB 저장
                            Toast.makeText(getApplicationContext(), "알림 저장 성공", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            SettingDB.DbOpenHelper.insertColumn(Time); //없으면 DB 저장
                        }
                    }
                } catch (Exception e) {
                    Log.d("erro", String.valueOf(e));
                }






                //SmDbOpenHelper.close();

            }
        });

    }
}