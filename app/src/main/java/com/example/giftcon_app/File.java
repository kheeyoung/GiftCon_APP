package com.example.giftcon_app;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class File {

    public void File(){}
    public void FileWrite(Context context, String StringUri, Uri imageUri){
        //파일에 저장할 비트맵 준비
        Bitmap bitmap = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.getContentResolver(), imageUri));
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //파일 이름
        String fileName = StringUri;
        
        try{
            //파일 생성 후 쓰기
            //파일 오픈 ( 파일명, 가시성 모드 )
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            //파일 기록
            fos.write(bitmap.getByteCount());
            //파일 close
            fos.close();
        }catch(IOException e){
            Log.d( this.getClass().getName(), e.getMessage() );
            e.printStackTrace();
        }
    }
    

    public void FileRead(Context context,String filename){
        try{
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            //임의의 길이로 문자열을 읽는다.
            StringBuilder sb = new StringBuilder();
            char[] inputBuffer = new char[2048];
            int end;
            // 버퍼에 데이터를 채운다.
            while(( end = isr.read(inputBuffer)) != -1 ){
                sb.append(inputBuffer, 0, end);
            }
            // 바이트를 문자열로 변환한다.
            String readString = sb.toString();
            Log.i("LOG_TAG", "Read string: " + readString);

        }catch(IOException e){

            e.printStackTrace();

        }
    }

}
