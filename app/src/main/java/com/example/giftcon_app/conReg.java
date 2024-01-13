package com.example.giftcon_app;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class conReg extends AppCompatActivity {

    private AlarmManager alarmManager; //알림 설정용 알림 매니저
    @RequiresApi(api = Build.VERSION_CODES.O)//알림 등록 처리 위한 API 요청

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_reg);

        // 유효기간
        // 유효기간 - 변수 선언
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // 유효기간 - 화면에서 아이디로 버튼 위젯 찾기
        Button dateBtu = (Button) findViewById(R.id.dateBtu);
        TextView dateView = (TextView) findViewById(R.id.dateView);

        // 유효기간 - 날짜 픽업 위젯 선언
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateView.setText(year + "-" + (month + 1) + "-" + dayOfMonth); //년-월-일 구성
            }
        }, mYear, mMonth, mDay);

        // 유효기간 - 버튼 누를 시 실행
        dateBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateBtu.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });

        // 이미지 업로드
        // 이미지 업로드 - 화면에서 아이디로 버튼 위젯 찾기
        Button imgset = (Button) findViewById(R.id.imgset);
        ImageView conimg = (ImageView) findViewById(R.id.conimg);

        final Uri[] Imguri = new Uri[1]; //URI 저장 할 변수

        // 이미지 업로드 -
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        // 이미지 업로드 - 선택 이미지로 이미지 뷰 변경
                        conimg.setImageURI(uri);
                        // URI 저장
                        Imguri[0] = uri;


                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        // 이미지 업로드 - 버튼 누를 시 실행
        imgset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이미지 업로드 - 포토 픽커
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());

            }
        });


        // 저장 기능
        // 이름(Name) / 날짜(Date) / 이미지 경로 (ImgUrl)

        // 저장 기능 - 화면에서 아이디로 버튼 위젯 찾기
        Button regconEnd = (Button) findViewById(R.id.regconEnd);
        EditText nameTxt = (EditText) findViewById(R.id.nameTxt);

        //푸시알림을 보내기 위해, 시스템에서 알림 서비스를 생성해주는 코드
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        // 저장 기능 - 버튼 누를 시 실행
        regconEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DB로 쓰기
                //DB 생성
                DB.DbOpenHelper mDbOpenHelper = new DB.DbOpenHelper(getApplicationContext());
                mDbOpenHelper.open();
                mDbOpenHelper.create();

                // DB 저장된 콘의 requestCode 받아오기
                // DB 커서 설정
                Cursor iCursor = mDbOpenHelper.sortColumns();
                int requestCode;//코드 저장용 변수
                int ConNum= iCursor.getCount(); //콘 개수 받아오기
                if(ConNum>=1) { //저장 된 콘이 있을 경우 가장 최근에 저장 된 콘의 코드 받아온다.
                    iCursor.moveToLast();
                    requestCode= Integer.parseInt(iCursor.getString(3));
                    iCursor.moveToFirst();
                    for(int i=0;i<ConNum;i++){ //리퀘스트 코드가 겹치지 않게 한다.
                        if(Integer.parseInt(iCursor.getString(3))==requestCode){
                            requestCode++;
                        }
                        iCursor.moveToNext();
                    }

                }
                else {
                    //저장된 값이 없을 경우 첫번째 코드는 0
                    requestCode =0;
                }

                // 저장 기능 -:W 저장 할 것들 값 받아오기
                String Name = nameTxt.getText().toString(); //콘 이름
                String Date = dateView.getText().toString(); //콘 날짜

                //SettingDB 생성
                SettingDB.DbOpenHelper SmDbOpenHelper = new SettingDB.DbOpenHelper(getApplicationContext());
                SmDbOpenHelper.open();
                SmDbOpenHelper.create();

                String Timeset=null; //시간 받아올 변수

                // SettingDB 커서 설정
                Cursor SiCursor = SmDbOpenHelper.sortColumn();

                // SettingDB 저장된 콘 개수 받아오기
                int TimeNum= SiCursor.getCount();
                if(TimeNum>=1) { //저장 된 시간이 있을 경우 받아온다.
                    SiCursor.moveToFirst();
                    Timeset = SiCursor.getString(1);
                }
                else {
                    SettingDB.DbOpenHelper.insertColumn("12:00:00"); //저장된 값이 없을 경우 정오에 울림
                    Timeset ="12:00:00";
                }
                SiCursor.close();
                SmDbOpenHelper.close();

                //DB에 쓰기
                try {
                    if (Name != null && Date != null && Date.equals("TextView")==false && String.valueOf(Imguri[0])!="null") { //모든 값이 입력 되어야만 실행
                            try {
                                //이미지 파일로 저장
                                File ConFile= new File();

                                String StringImguri= String.valueOf(Imguri[0]);
                                String result = StringImguri.substring(StringImguri.lastIndexOf("/")+1);

                                NotificationSetting N=new NotificationSetting();
                                DB.DbOpenHelper.insertColumn(Name, Date, result, String.valueOf(requestCode)); //DB 저장

                                N.setNotice(Date,Timeset,getApplicationContext(),alarmManager,requestCode,Name); //알림 설정 yyyy-MM-dd HH:mm:ss 구성

                                ConFile.FileWrite(getApplicationContext(),result,Imguri[0]);



                            } catch (Exception e) {
                                Log.d("erro", String.valueOf(e)+" / 저장 중 오류");
                            }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "입력되지 않은 값이 있습니다!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "저장 실패", Toast.LENGTH_SHORT).show();
                }
                mDbOpenHelper.close();
            }
        });
    }
}





