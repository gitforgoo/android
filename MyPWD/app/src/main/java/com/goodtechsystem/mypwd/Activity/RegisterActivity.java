package com.goodtechsystem.mypwd.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.bo.UserDBHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText tbxID;
    private EditText tbxName;
    private EditText tbxPassword;

    private Button btnSave;
    private Button btnCancel;

    UserDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        tbxID = findViewById(R.id.tbxId);
        tbxName = findViewById(R.id.tbxName);
        tbxPassword = findViewById(R.id.tbxPassword);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        dbHelper = new UserDBHelper(this);

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

        dbHelper.addUser(id, name, password);

        Toast.makeText(this.getApplicationContext(), getString(R.string.register_complete), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void btnCancel_Click(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}