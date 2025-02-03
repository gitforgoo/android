package com.goodtechsystem.mypwd.bo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.goodtechsystem.mypwd.vo.PwdConst;
import com.goodtechsystem.mypwd.vo.UserVO;

public class UserBO extends BoBase {

    public UserBO(Context context) {
        super(context);
    }

    // 사용자 추가
    public void insertUser(UserVO user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PwdConst.USER.COLUMN_ID, user.getId());
        values.put(PwdConst.USER.COLUMN_NAME, user.getName());
        values.put(PwdConst.USER.COLUMN_PASSWORD, user.getPassword());

        db.insert(PwdConst.USER.TABLE_NAME, null, values);
        db.close();
    }

    // 모든 사용자 가져오기
    public UserVO selectUser(String id) {

        UserVO ret = new UserVO();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] fields = {PwdConst.USER.COLUMN_ID, PwdConst.USER.COLUMN_NAME, PwdConst.USER.COLUMN_PASSWORD};
        String[] params = {id};

        Cursor cursor = db.query(PwdConst.USER.TABLE_NAME, fields, "id=?", params, null, null, null);

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

    public long selectCountUser(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] fields = {"COUNT(*)"};

         Cursor cursor = db.query(PwdConst.USER.TABLE_NAME, fields, null, null, null, null, null);
         long count = 0;

         if(cursor.moveToFirst()){
             count = cursor.getLong(0);
         }

         return count;
    }

    // 사용자 삭제
    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PwdConst.USER.TABLE_NAME, PwdConst.USER.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
    }

    public void updateUser(UserVO user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PwdConst.USER.COLUMN_NAME, user.getName());
        values.put(PwdConst.USER.COLUMN_PASSWORD, user.getPassword());

        String[] whereArgs = {user.getId()};
        db.update(PwdConst.USER.TABLE_NAME, values, "id=?", whereArgs);
        db.close();
    }
}
