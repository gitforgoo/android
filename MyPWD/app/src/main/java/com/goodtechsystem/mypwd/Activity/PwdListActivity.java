package com.goodtechsystem.mypwd.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.bo.PwdDBHelper;
import com.goodtechsystem.mypwd.vo.PwdVO;

public class PwdListActivity extends AppCompatActivity {

    private PwdDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pwd_list);

        dbHelper = new PwdDBHelper(this);

        long oid = dbHelper.insertPwd("Google", "testforgoo9", "ga5ga2garg", "Test용", "Note20");

        PwdVO vo = dbHelper.selectPwd(oid);


    }
}