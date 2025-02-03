package com.goodtechsystem.mypwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.bo.UserBO;
import com.goodtechsystem.mypwd.util.EncDecUtil;
import com.goodtechsystem.mypwd.util.MyPWDApplication;
import com.goodtechsystem.mypwd.vo.PwdConst;
import com.goodtechsystem.mypwd.vo.UserVO;

public class RegisterActivity extends ActivityBase {

    private EditText tbxID;
    private EditText tbxName;
    private EditText tbxPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        //Toolbar 표시
        setupToolbar(R.id.toolbar);

        String type = this.getIntent().getStringExtra("type");
        if(PwdConst.TYPE_MODIFY.equals(type)){
            this.initData();
        }

        tbxID = findViewById(R.id.tbxId);
        tbxName = findViewById(R.id.tbxName);
        tbxPassword = findViewById(R.id.tbxPassword);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave_Click(v);
            }
        });
    }

    protected void initData() {
        MyPWDApplication app = (MyPWDApplication)getApplication();
        String userId = app.getUserId();

        if(userId == null || userId.isEmpty()){
            Toast.makeText(this.getApplicationContext(), getString(R.string.warn_data_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        UserBO bo = new UserBO(this);
        UserVO vo = bo.selectUser(userId);

        if (vo != null) {
            String id = new EncDecUtil().decryptString(vo.getId());
            String password = new EncDecUtil().decryptString(vo.getPassword());

            ((EditText) findViewById(R.id.tbxId)).setText(id);
            ((EditText) findViewById(R.id.tbxName)).setText(password);
            ((EditText) findViewById(R.id.tbxPassword)).setText(vo.getName());
        } else {
            Toast.makeText(this.getApplicationContext(), getString(R.string.warn_data_empty), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void btnSave_Click(View v){
        String id = tbxID.getText().toString();
        String name = tbxName.getText().toString();
        String password = tbxPassword.getText().toString();

        UserVO user = new UserVO();
        user.setID(id);
        user.setName(name);
        user.setPassword(password);

        UserBO bo = new UserBO(this);
        String type = this.getIntent().getStringExtra("type");
        if(PwdConst.TYPE_ADD.equals(type)) {
            bo.insertUser(user);
            Toast.makeText(this.getApplicationContext(), getString(R.string.register_complete), Toast.LENGTH_SHORT).show();
        } else if(PwdConst.TYPE_MODIFY.equals(type)){

            bo.updateUser(user);
            Toast.makeText(this.getApplicationContext(), getString(R.string.register_complete), Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}