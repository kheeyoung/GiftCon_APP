package com.example.giftcon_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationSetting {
    NotificationSetting(){}
    //알람매니저에 알람등록 처리

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setNotice(String alarmDateValue, String alarmTimeValue ,Context C, AlarmManager alarmManager,int requestCode) {

        //알람을 수신할 수 있도록 하는 리시버로 인텐트 요청
        Intent receiverIntent = new Intent(C, NotificationReceiver.class);
        receiverIntent.putExtra("content", "알람등록 테스트");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(C, requestCode, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT); //리퀘스트 코드 수정 필요


        //등록한 알람날짜 포맷을 밀리초로 변경한기 위한 코드
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date datetime = null;

        try {
            datetime = dateFormat.parse(alarmDateValue + " "+alarmTimeValue);
            //날짜 비교하기
            Date currentDateTime = new Date();
            int result = currentDateTime.compareTo(datetime);
                //date타입으로 변경된 알람시간을 캘린더 타임에 등록
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(datetime);
                //알람 설정
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(C, "저장성공:"+datetime, Toast.LENGTH_SHORT).show();
        }
        catch (ParseException e) {
            Toast.makeText(C, "저장 실패: 유효기간을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }

    }
}
