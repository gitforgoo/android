package com.goodtechsystem.mypwd.bo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.goodtechsystem.mypwd.vo.PwdConst;
import com.goodtechsystem.mypwd.vo.PwdVO;
import java.util.ArrayList;
import java.util.Calendar;

public class PwdBO extends BoBase{

    public PwdBO(Context context) {
        super(context);
    }

    public long insertPwd(String site, String id, String pwd, String purpose, String usage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Calendar now = Calendar.getInstance();
        long oid = now.getTimeInMillis();

        values.put(PwdConst.PWD.COL_OID, oid);
        values.put(PwdConst.PWD.COL_SITE, site);
        values.put(PwdConst.PWD.COL_ID, id);
        values.put(PwdConst.PWD.COL_PWD, pwd);
        values.put(PwdConst.PWD.COL_PURPOSE, purpose);
        values.put(PwdConst.PWD.COL_USAGE, usage);

        db.insert(PwdConst.PWD.TABLE_NAME, null, values);
        db.close();
        return oid;
    }

    public ArrayList<PwdVO> selectAllPwd() {

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<PwdVO> ret = new ArrayList<PwdVO>();

        String[] fields = {PwdConst.PWD.COL_OID, PwdConst.PWD.COL_SITE, PwdConst.PWD.COL_ID, PwdConst.PWD.COL_PWD, PwdConst.PWD.COL_PURPOSE, PwdConst.PWD.COL_USAGE};

        Cursor cursor = db.query(PwdConst.PWD.TABLE_NAME, fields, null, null, null, null, null);

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

        SQLiteDatabase db = this.getReadableDatabase();

        PwdVO ret = new PwdVO();

        String[] fields = {PwdConst.PWD.COL_OID, PwdConst.PWD.COL_SITE, PwdConst.PWD.COL_ID, PwdConst.PWD.COL_PWD, PwdConst.PWD.COL_PURPOSE, PwdConst.PWD.COL_USAGE};
        String[] params = {String.valueOf(oid)};

        Cursor cursor = db.query(PwdConst.PWD.TABLE_NAME, fields, "oid=?", params, null, null, null);

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

        db.delete(PwdConst.PWD.TABLE_NAME, "oid=?", params);
    }

}
