package com.goodtechsystem.mypwd.Activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.bo.PwdBO;
import com.goodtechsystem.mypwd.vo.PwdVO;

import java.util.ArrayList;
import java.util.List;

public class PwdListActivity extends AppCompatActivity {

    private ArrayList<PwdVO> dataSource;
    private PwdListItemAdapter adapter;
    private RecyclerView rv_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pwd_list);

        rv_pwd = findViewById(R.id.rv_pwd);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_pwd.setLayoutManager(linearLayoutManager);

        PwdBO bo = new PwdBO(this);
        dataSource = bo.selectAllPwd();

        adapter = new PwdListItemAdapter(this, dataSource);
        rv_pwd.setAdapter(adapter);

    }
}