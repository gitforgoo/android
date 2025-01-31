package com.goodtechsystem.mypwd.bo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.goodtechsystem.mypwd.vo.PwdConst;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, PwdConst.DATABASE_NAME, null, PwdConst.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE " + PwdConst.USER.TABLE_NAME + " (" +
                PwdConst.USER.COLUMN_ID + " TEXT PRIMARY KEY, " +
                PwdConst.USER.COLUMN_NAME + " TEXT, " +
                PwdConst.USER.COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUserTableQuery);

        String createPwdTableQuery = "CREATE TABLE " + PwdConst.PWD.TABLE_NAME + " (" +
                PwdConst.PWD.COL_OID + " INTEGER PRIMARY KEY, " +
                PwdConst.PWD.COL_SITE + " TEXT, " +
                PwdConst.PWD.COL_ID + " TEXT, " +
                PwdConst.PWD.COL_PWD + " TEXT, " +
                PwdConst.PWD.COL_PURPOSE + " TEXT, " +
                PwdConst.PWD.COL_REMARK + " TEXT)";
        db.execSQL(createPwdTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PwdConst.USER.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PwdConst.PWD.TABLE_NAME);
        onCreate(db);
    }
}
