package com.goodtechsystem.mypwd.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.bo.UserBO;
import com.goodtechsystem.mypwd.vo.UserVO;

public class LoginActivity extends AppCompatActivity {

    private EditText tbxId;
    private EditText tbxPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        if(this.getSupportActionBar() != null) {
            this.getSupportActionBar().show();
        }

        tbxId = findViewById(R.id.tbxId);
        tbxPassword = findViewById(R.id.tbxPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

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

}