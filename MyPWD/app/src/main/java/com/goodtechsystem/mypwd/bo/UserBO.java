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
    public void insertUser(String id, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PwdConst.USER.COLUMN_ID, id);
        values.put(PwdConst.USER.COLUMN_NAME, name);
        values.put(PwdConst.USER.COLUMN_PASSWORD, password);

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

    // 사용자 삭제
    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PwdConst.USER.TABLE_NAME, PwdConst.USER.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
    }
}
