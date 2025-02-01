package com.goodtechsystem.mypwd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.bo.UserBO;
import com.goodtechsystem.mypwd.vo.UserVO;

public class LoginActivity extends ActivityBase {

    private EditText tbxId;
    private EditText tbxPassword;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //Toolbar 표시
        setupToolbar(R.id.toolbar);

        // 뒤로가기 버튼 처리
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                    backKeyPressedTime = System.currentTimeMillis();
                    Toast.makeText(LoginActivity.super.getApplicationContext(), getString(R.string.warn_backbutton_press), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    finishAffinity();
                }
            }
        };

        // 뒤로가기 버튼 처리를 활성화
        getOnBackPressedDispatcher().addCallback(this, callback);

        tbxId = findViewById(R.id.tbxId);
        tbxPassword = findViewById(R.id.tbxPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        Button btnShow = findViewById(R.id.btnShow);
        btnShow.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return btnShow_Click(v, event);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin_Click(v);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegister_Click(v);
            }
        });
    }

    private void btnLogin_Click(View v){
        String id = tbxId.getText().toString();
        String password = tbxPassword.getText().toString();

        UserBO bo = new UserBO(this);
        UserVO user = bo.selectUser(id);

        if(user != null){
            if(user.getPassword() !=null && password.equals(user.getPassword())){
                Toast.makeText(this.getApplicationContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                changeActivity(PwdListActivity.class);
            } else {
                Toast.makeText(this.getApplicationContext(), getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getApplicationContext(),  getString(R.string.login_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    private void btnRegister_Click(View v){
        changeActivity(RegisterActivity.class);
    }

    private void changeActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    private boolean btnShow_Click(View v, MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            //버튼 누른 경우 - 비밀번호 보이기
            tbxPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            //커서 위치를 맨뒤로 이동
            tbxPassword.setSelection(tbxPassword.getText().length());
        } else if(event.getAction() == MotionEvent.ACTION_UP){
            //버튼에서 손을 뗀 경우 - 비밀번호 숨기기
            tbxPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            //커서 위치를 맨뒤로 이동
            tbxPassword.setSelection(tbxPassword.getText().length());
        }
        return true;
    }
}