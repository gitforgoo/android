package com.goodtechsystem.mypwd.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.bo.UserBO;

public class RegisterActivity extends AppCompatActivity {

    private EditText tbxID;
    private EditText tbxName;
    private EditText tbxPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        tbxID = findViewById(R.id.tbxId);
        tbxName = findViewById(R.id.tbxName);
        tbxPassword = findViewById(R.id.tbxPassword);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave_Click(v);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancel_Click(v);
            }
        });

    }

    private void btnSave_Click(View v){
        String id = tbxID.getText().toString();
        String name = tbxName.getText().toString();
        String password = tbxPassword.getText().toString();

        UserBO bo = new UserBO(this);
        bo.insertUser(id, name, password);

        Toast.makeText(this.getApplicationContext(), getString(R.string.register_complete), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void btnCancel_Click(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}