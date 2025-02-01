package com.goodtechsystem.mypwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.bo.PwdBO;
import com.goodtechsystem.mypwd.util.EncDecUtil;
import com.goodtechsystem.mypwd.vo.PwdConst;
import com.goodtechsystem.mypwd.vo.PwdVO;

public class PwdDetailActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pwd_detail);

        //Toolbar 표시
        setupToolbar(R.id.toolbar);

        String type = this.getIntent().getStringExtra("type");
        if(PwdConst.TYPE_MODIFY.equals(type)){
            this.initData();
        }

        Button btnSave = findViewById(R.id.pwdDetail_btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave_click(v);
            }
        });
    }

    protected void initData(){
        PwdBO bo = new PwdBO(this);

        String oid = this.getIntent().getStringExtra("oid");
        if(oid != null) {
            PwdVO vo = bo.selectPwd(oid);

            if(vo != null) {
                String id = new EncDecUtil().decryptString(vo.getId());
                String password = new EncDecUtil().decryptString(vo.getPassword());

                ((EditText) findViewById(R.id.pwdDetail_tbxSite)).setText(vo.getSite());
                ((EditText) findViewById(R.id.pwdDetail_tbxId)).setText(id);
                ((EditText) findViewById(R.id.pwdDetail_tbxPassword)).setText(password);
                ((EditText) findViewById(R.id.pwdDetail_tbxPurpose)).setText(vo.getPurpose());
                ((EditText) findViewById(R.id.pwdDetail_tbxRemark)).setText(vo.getRemark());
            } else {
                Toast.makeText(this.getApplicationContext(), getString(R.string.warn_data_empty), Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void btnSave_click(View v){
        PwdBO bo = new PwdBO(this);

        String site = ((EditText)findViewById(R.id.pwdDetail_tbxSite)).getText().toString();
        String id = ((EditText)findViewById(R.id.pwdDetail_tbxId)).getText().toString();
        String password = ((EditText)findViewById(R.id.pwdDetail_tbxPassword)).getText().toString();
        String purpose = ((EditText)findViewById(R.id.pwdDetail_tbxPurpose)).getText().toString();
        String remark = ((EditText)findViewById(R.id.pwdDetail_tbxRemark)).getText().toString();

        id = new EncDecUtil().encryptString(id);
        password = new EncDecUtil().encryptString(password);

        PwdVO pwd = new PwdVO();
        pwd.setSite(site);
        pwd.setId(id);
        pwd.setPassword(password);
        pwd.setPurpose(purpose);
        pwd.setRemark(remark);

        String type = getIntent().getStringExtra("type");
        if(PwdConst.TYPE_ADD.equals(type)){
            bo.insertPwd(pwd);
            Toast.makeText(this.getApplicationContext(), getString(R.string.pwd_insert_success), Toast.LENGTH_SHORT).show();
        } else {
            String oid = this.getIntent().getStringExtra("oid");
            pwd.setOid(oid);
            bo.updatePwd(pwd);
            Toast.makeText(this.getApplicationContext(), getString(R.string.pwd_udpate_success), Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, PwdListActivity.class);
        startActivity(intent);
    }
}