package com.goodtechsystem.mypwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.adapter.PwdListItemAdapter;
import com.goodtechsystem.mypwd.bo.PwdBO;
import com.goodtechsystem.mypwd.vo.PwdConst;
import com.goodtechsystem.mypwd.vo.PwdVO;

import java.util.ArrayList;

public class PwdListActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pwd_list);

        RecyclerView rv_pwd = findViewById(R.id.rv_pwd);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_pwd.setLayoutManager(linearLayoutManager);

        // Divider 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv_pwd.addItemDecoration(dividerItemDecoration);

        PwdBO bo = new PwdBO(this);
        ArrayList<PwdVO> dataSource = bo.selectAllPwd();

        PwdListItemAdapter adapter = new PwdListItemAdapter(this, dataSource);
        rv_pwd.setAdapter(adapter);

        Button btnAdd = findViewById(R.id.pwdList_btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdd_Click(v);
            }
        });
    }

    protected void btnAdd_Click(View v){
        Intent intent = new Intent(this, PwdDetailActivity.class);
        intent.putExtra("type", PwdConst.TYPE_ADD);
        startActivity(intent);
    }
}