package com.goodtechsystem.mypwd.bo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.goodtechsystem.mypwd.vo.PwdVO;
import java.util.ArrayList;
import java.util.Calendar;

public class PwdDBHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME =  "goodtechsystem.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "t_pwd";

    private static  final String COL_OID = "oid";

    private static  final String COL_SITE = "site";

    private static  final String COL_ID = "id";

    private static  final String COL_PWD = "pwd";

    private static  final String COL_PURPOSE = "purpose";

    private static  final String COL_USAGE = "usage";

    public PwdDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_OID + " INTEGER PRIMARY KEY, " +
                COL_SITE + " TEXT, " +
                COL_ID + " TEXT, " +
                COL_PWD + " TEXT, " +
                COL_PURPOSE + " TEXT, " +
                COL_USAGE + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertPwd(String site, String id, String pwd, String purpose, String usage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Calendar now = Calendar.getInstance();
        long oid = now.getTimeInMillis();
        values.put(COL_OID, oid);

        values.put(COL_SITE, site);
        values.put(COL_ID, id);
        values.put(COL_PWD, pwd);
        values.put(COL_PURPOSE, purpose);
        values.put(COL_USAGE, usage);

        db.insert(TABLE_NAME, null, values);
        db.close();
        return oid;
    }

    public ArrayList<PwdVO> selectAllPwd() {

        ArrayList<PwdVO> ret = new ArrayList<PwdVO>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] fields = {COL_OID, COL_SITE, COL_ID, COL_PWD, COL_PURPOSE, COL_USAGE};

        Cursor cursor = db.query(TABLE_NAME, fields, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                PwdVO vo = new PwdVO();

                long oid = cursor.getLong(0);
                String site = cursor.getString(1);
                String id = cursor.getString(2);
                String pwd = cursor.getString(3);
                String purpose = cursor.getString(4);
                String usage = cursor.getString(5);

                vo.setOid(oid);
                vo.setSite(site);
                vo.setId(id);
                vo.setPwd(pwd);
                vo.setPurpose(purpose);
                vo.setUsage(usage);

                ret.add(vo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ret;
    }

    public PwdVO selectPwd(long oid) {

        PwdVO ret = new PwdVO();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] fields = {COL_OID, COL_SITE, COL_ID, COL_PWD, COL_PURPOSE, COL_USAGE};
        String[] params = {String.valueOf(oid)};

        Cursor cursor = db.query(TABLE_NAME, fields, "oid=?", params, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                long lOid = cursor.getLong(0);
                String site = cursor.getString(1);
                String id = cursor.getString(2);
                String pwd = cursor.getString(3);
                String purpose = cursor.getString(4);
                String usage = cursor.getString(5);

                ret.setOid(lOid);
                ret.setSite(site);
                ret.setId(id);
                ret.setPwd(pwd);
                ret.setPurpose(purpose);
                ret.setUsage(usage);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ret;
    }

    public void deletePwd(long oid){
        PwdVO ret = new PwdVO();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] params = {String.valueOf(oid)};

        db.delete(TABLE_NAME, "oid=?", params);
    }

}
