package com.goodtechsystem.mypwd.bo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.goodtechsystem.mypwd.vo.PwdConst;
import com.goodtechsystem.mypwd.vo.PwdVO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class PwdBO extends BoBase{

    public PwdBO(Context context) {
        super(context);
    }

    public void insertPwd(PwdVO vo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Calendar now = Calendar.getInstance();
        long tmp = now.getTimeInMillis();
        String oid = String.valueOf(tmp);

        values.put(PwdConst.PWD.COL_OID, tmp);
        values.put(PwdConst.PWD.COL_SITE, vo.getSite());
        values.put(PwdConst.PWD.COL_ID, vo.getId());
        values.put(PwdConst.PWD.COL_PWD, vo.getPassword());
        values.put(PwdConst.PWD.COL_PURPOSE, vo.getPurpose());
        values.put(PwdConst.PWD.COL_REMARK, vo.getRemark());

        db.insert(PwdConst.PWD.TABLE_NAME, null, values);
        db.close();
    }

    public void updatePwd(PwdVO vo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(PwdConst.PWD.COL_OID, vo.getOid());
        values.put(PwdConst.PWD.COL_SITE, vo.getSite());
        values.put(PwdConst.PWD.COL_ID, vo.getId());
        values.put(PwdConst.PWD.COL_PWD, vo.getPassword());
        values.put(PwdConst.PWD.COL_PURPOSE, vo.getPurpose());
        values.put(PwdConst.PWD.COL_REMARK, vo.getRemark());

        String[] whereArgs = {vo.getOid()};

        db.update(PwdConst.PWD.TABLE_NAME, values, "oid=?", whereArgs);
        db.close();
    }

    public ArrayList<PwdVO> selectPwdBySearchCondition(Map<String, String> searchCondition) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] fields = {PwdConst.PWD.COL_OID, PwdConst.PWD.COL_SITE, PwdConst.PWD.COL_ID, PwdConst.PWD.COL_PWD, PwdConst.PWD.COL_PURPOSE, PwdConst.PWD.COL_REMARK};

        String selection = "";
        String[] selectionArgs = null;

        if(searchCondition != null && !searchCondition.isEmpty()){
            ArrayList<String> valueList = new ArrayList<>();
            for(String key : searchCondition.keySet()){
                selection = selection + key + "=?, ";
                String value = searchCondition.get(key);
                valueList.add(value);
            }

            if(selection != null && selection.length() > 0) {
                selection = selection.substring(0, selection.length() -2);
            }
            selectionArgs = valueList.toArray(new String[0]);
        }

        Cursor cursor = db.query(PwdConst.PWD.TABLE_NAME, fields, selection, selectionArgs, null, null, PwdConst.PWD.COL_SITE + " ASC, " + PwdConst.PWD.COL_ID + " ASC" );

        ArrayList<PwdVO> ret = fill(cursor);
        cursor.close();
        db.close();

        return ret;
    }

    public PwdVO selectPwd(String oid) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] fields = {PwdConst.PWD.COL_OID, PwdConst.PWD.COL_SITE, PwdConst.PWD.COL_ID, PwdConst.PWD.COL_PWD, PwdConst.PWD.COL_PURPOSE, PwdConst.PWD.COL_REMARK};
        String[] params = {oid};

        Cursor cursor = db.query(PwdConst.PWD.TABLE_NAME, fields, "oid=?", params, null, null, null);
        ArrayList<PwdVO> lst = fill(cursor);
        cursor.close();
        db.close();

        if(lst == null && lst.isEmpty()){
            return null;
        }

        return lst.get(0);
    }

    public ArrayList<String> selectSitesPwd(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] fields = {PwdConst.PWD.COL_SITE};

        Cursor cursor = db.query(PwdConst.PWD.TABLE_NAME, fields, null, null, PwdConst.PWD.COL_SITE, null, PwdConst.PWD.COL_SITE);
        ArrayList<String> lst = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lst.add(cursor.getString(cursor.getColumnIndexOrThrow(PwdConst.PWD.COL_SITE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lst;
    }

    public void deletePwd(String oid){
        PwdVO ret = new PwdVO();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] params = {oid};

        db.delete(PwdConst.PWD.TABLE_NAME, "oid=?", params);
    }

    protected ArrayList<PwdVO> fill(Cursor cursor){
        ArrayList<PwdVO> ret = new  ArrayList<PwdVO>();

        if (cursor.moveToFirst()) {
            do {
                PwdVO vo = new PwdVO();

                String oid = cursor.getString(cursor.getColumnIndexOrThrow(PwdConst.PWD.COL_OID));
                String site = cursor.getString(cursor.getColumnIndexOrThrow(PwdConst.PWD.COL_SITE));
                String id = cursor.getString(cursor.getColumnIndexOrThrow(PwdConst.PWD.COL_ID));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(PwdConst.PWD.COL_PWD));
                String purpose = cursor.getString(cursor.getColumnIndexOrThrow(PwdConst.PWD.COL_PURPOSE));
                String remark = cursor.getString(cursor.getColumnIndexOrThrow(PwdConst.PWD.COL_REMARK));

                vo.setOid(oid);
                vo.setSite(site);
                vo.setId(id);
                vo.setPassword(password);
                vo.setPurpose(purpose);
                vo.setRemark(remark);

                ret.add(vo);
            } while (cursor.moveToNext());
        }

        return ret;
    }

}
