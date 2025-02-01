package com.goodtechsystem.mypwd.util;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;

import androidx.core.util.Pair;

import com.goodtechsystem.mypwd.bo.PwdBO;
import com.goodtechsystem.mypwd.vo.PwdConst;
import com.goodtechsystem.mypwd.vo.PwdVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    private String jsonFileName;

    private Context context;

    public JsonHelper(Context context){
        this.context = context;
        this.jsonFileName = "MyPWD_BackUp.json";
    }

    public void saveListToJson(List<PwdVO> data, ParcelFileDescriptor fileDescriptor) {
        JSONArray jsonArray = new JSONArray();

        for (PwdVO vo : data) {
            JSONObject userObject = new JSONObject();
            try {
                userObject.put(PwdConst.PWD.COL_OID, vo.getOid());
                userObject.put(PwdConst.PWD.COL_SITE, vo.getSite());
                userObject.put(PwdConst.PWD.COL_ID, vo.getId());
                userObject.put(PwdConst.PWD.COL_PWD, vo.getPassword());
                userObject.put(PwdConst.PWD.COL_PURPOSE, vo.getPurpose());
                userObject.put(PwdConst.PWD.COL_REMARK, vo.getRemark());
                jsonArray.put(userObject);
            } catch (JSONException e) {
                e.printStackTrace();
                System.err.println("JSON 객체 생성 실패");
                return;
            }
        }

        String jsonString = jsonArray.toString(); // 들여쓰기 2칸으로 포맷팅

        DataOutputStream dos = null;
        try {
            FileOutputStream fos = new FileOutputStream(fileDescriptor.getFileDescriptor());
            dos = new DataOutputStream(fos);
            dos.write(jsonString.getBytes(StandardCharsets.UTF_8));
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("JSON 파일 저장 실패");
        } finally {
            if(dos != null) {
                try {
                    dos.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveJsonToData(ParcelFileDescriptor jsonFileDescriptor){
        List<PwdVO> data = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(jsonFileDescriptor.getFileDescriptor());
            DataInputStream dis = new DataInputStream(fis);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
            reader.close();

            JSONArray jsonArray = new JSONArray(jsonStringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userObject = jsonArray.getJSONObject(i);
                String oid = userObject.getString(PwdConst.PWD.COL_OID);
                String site = userObject.getString(PwdConst.PWD.COL_SITE);
                String id = userObject.getString(PwdConst.PWD.COL_ID);
                String pwd = userObject.getString(PwdConst.PWD.COL_PWD);
                String purpose = userObject.getString(PwdConst.PWD.COL_PURPOSE);
                String remark = userObject.getString(PwdConst.PWD.COL_REMARK);

                PwdVO vo = new PwdVO();
                vo.setOid(oid);
                vo.setSite(site);
                vo.setId(id);
                vo.setPassword(pwd);
                vo.setPurpose(purpose);
                vo.setRemark(remark);

                data.add(vo);
            }

            if(data.size() > 0){
                PwdBO bo = new PwdBO(context);
                for(PwdVO vo : data){
                    bo.insertPwd(vo);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("JSON 파일 읽기 실패");
        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println("JSON 파싱 실패");
        }
    }
}
