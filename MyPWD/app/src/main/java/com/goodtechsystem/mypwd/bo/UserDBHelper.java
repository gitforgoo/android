package com.goodtechsystem.mypwd.bo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.goodtechsystem.mypwd.vo.UserVO;

import java.lang.reflect.Array;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME =  "goodtechsystem.db";

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "t_user";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 사용자 추가
    public long addUser(String id, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    // 모든 사용자 가져오기
    public UserVO getUser(String id) {

        UserVO ret = new UserVO();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] fields = {COLUMN_ID, COLUMN_NAME, COLUMN_PASSWORD};
        String[] params = {id};

        Cursor cursor = db.query(TABLE_NAME, fields, "id=?", params, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String password = cursor.getString(2);

                ret.setID(id);
                ret.setName(name);
                ret.setPassword(password);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ret;
    }

    // 사용자 삭제
    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
    }
}
