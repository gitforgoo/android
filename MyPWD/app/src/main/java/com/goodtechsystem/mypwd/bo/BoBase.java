package com.goodtechsystem.mypwd.bo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BoBase {
    DBHelper dbHelper;

    public BoBase(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    protected SQLiteDatabase getWritableDatabase(){
        return dbHelper.getWritableDatabase();
    }

    protected SQLiteDatabase getReadableDatabase(){
        return dbHelper.getReadableDatabase();
    }
}
