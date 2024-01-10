package com.example.giftcon_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class SettingDB {
    //DB 관련

    //콘 등록용
    //DB 생성을 위한 테이블 column 생성
    public final class DataBases {

        public final class CreateDB implements BaseColumns {
            public static final String Time = "Time";
            public static final String _TABLENAME0 = "Settingtable";
            public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                    +_ID+" integer primary key autoincrement, "
                    +Time+" text not null);";
        }
    }

    //DB 생성 클래스
    public static class DbOpenHelper {
        private static final String DATABASE_NAME = "InnerSetDatabase(SQLite).db";
        private static final int DATABASE_VERSION = 1;
        public static SQLiteDatabase SmDB;
        private DatabaseHelper SmDBHelper;
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
            SmDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
            SmDB = SmDBHelper.getWritableDatabase();
            return this;
        }
        //생성
        public void create(){
            SmDBHelper.onCreate(SmDB);
        }
        //닫기
        public void close(){
            SmDB.close();
        }
        //데이터 삽입
        public static long insertColumn(String name){
            ContentValues values = new ContentValues();
            values.put(DataBases.CreateDB.Time, name);
            return SmDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
        }

        //데이터 가져오기 (칼럼 가져오기)
        public Cursor sortColumn(){
            Cursor c = SmDB.rawQuery( "SELECT * FROM Settingtable;", null);
            return c;
        }


        //데이터 업데이트
        public static boolean updateColumn(long id, String name){
            ContentValues values = new ContentValues();
            values.put(DataBases.CreateDB.Time, name);
            return SmDB.update(DataBases.CreateDB._TABLENAME0, values, "_id=" + id, null) > 0;
        }

        //데이터 삭제
        // Delete All
        public void deleteAllColumns() {
            SmDB.delete(DataBases.CreateDB._TABLENAME0, null, null);
        }

        // Delete Column
        public boolean deleteColumn(long id){
            return SmDB.delete(DataBases.CreateDB._TABLENAME0, "_id="+id, null) > 0;
        }


    }


}

