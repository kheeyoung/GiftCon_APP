package com.example.giftcon_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DB {
    //DB 관련

    //콘 등록용
    //DB 생성을 위한 테이블 column 생성
    public final class DataBases {

        public final class CreateDB implements BaseColumns {
            public static final String NAME = "name";
            public static final String Date = "date";
            public static final String Image = "image";

            public static final String RequestCode = "requestCode";

            public static final String _TABLENAME0 = "usertable";
            public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                    +_ID+" integer primary key autoincrement, "
                    +NAME+" text not null , "
                    +Date+" text not null , "
                    +RequestCode+" text not null , "
                    +Image+" text not null);"; //이미지는 비트맵>String 로 저장
        }
    }

    //DB 생성 클래스
    public static class DbOpenHelper {
        private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
        private static final int DATABASE_VERSION = 2;
        public static SQLiteDatabase mDB;
        private DatabaseHelper mDBHelper;
        private Context mCtx;


        private class DatabaseHelper extends SQLiteOpenHelper {

            public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }

            @Override
            public void onCreate(SQLiteDatabase db){
                db.execSQL(DataBases.CreateDB._CREATE0);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
                db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME0);
                onCreate(db);
            }
        }

        //생성자
        public DbOpenHelper(Context context){
            this.mCtx = context;
        }

        //열기
        public DbOpenHelper open() throws SQLException {
            mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
            mDB = mDBHelper.getWritableDatabase();
            return this;
        }
        //생성
        public void create(){
            mDBHelper.onCreate(mDB);
        }
        //닫기
        public void close(){
            mDB.close();
        }
        //데이터 삽입
        public static long insertColumn(String name, String date, String image, String requestCode){
            ContentValues values = new ContentValues();
            values.put(DataBases.CreateDB.NAME, name);
            values.put(DataBases.CreateDB.Date, date);
            values.put(DataBases.CreateDB.Image, image);
            values.put(DataBases.CreateDB.RequestCode, requestCode);
            return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
        }

        //데이터 가져오기 (칼럼 가져오기)
        // 이름
        public Cursor sortColumnName(){
            Cursor c = mDB.rawQuery( "SELECT name FROM usertable;", null);
            return c;
        }
        // 날짜
        public Cursor sortColumnDate(){
            Cursor c = mDB.rawQuery( "SELECT date FROM usertable;", null);
            return c;
        }
        // 이미지
        public Cursor sortColumnImage(){
            Cursor c = mDB.rawQuery( "SELECT image FROM usertable;", null);
            return c;
        }
        // 코드
        public Cursor sortColumnrequestCode(){
            Cursor c = mDB.rawQuery( "SELECT requestCode FROM usertable;", null);
            return c;
        }
        //전부 다
        public Cursor sortColumns(){
            Cursor c = mDB.rawQuery( "SELECT * FROM usertable;", null);
            return c;
        }

        //데이터 업데이트
        public boolean updateColumn(long id, String name, String date , String image, String requestCode){
            ContentValues values = new ContentValues();
            values.put(DataBases.CreateDB.NAME, name);
            values.put(DataBases.CreateDB.Date, date);
            values.put(DataBases.CreateDB.Image, image);
            values.put(DataBases.CreateDB.RequestCode, requestCode);
            return mDB.update(DataBases.CreateDB._TABLENAME0, values, "_id=" + id, null) > 0;
        }

        //데이터 삭제
        // Delete All
        public void deleteAllColumns() {
            mDB.delete(DataBases.CreateDB._TABLENAME0, null, null);
        }

        // Delete Column
        public boolean deleteColumn(long id){
            return mDB.delete(DataBases.CreateDB._TABLENAME0, "_id="+id, null) > 0;
        }


    }


}
