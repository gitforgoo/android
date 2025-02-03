package com.goodtechsystem.mypwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
import java.util.HashMap;
import java.util.Map;

public class PwdListActivity extends ActivityBase {

    private RecyclerView rv_pwd;

    private Spinner rv_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pwd_list);

        //Toolbar 표시
        setupToolbar(R.id.toolbar);

        rv_pwd = findViewById(R.id.rv_pwd);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_pwd.setLayoutManager(linearLayoutManager);

        rv_spinner = findViewById(R.id.rv_spinner);

        // Divider 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv_pwd.addItemDecoration(dividerItemDecoration);

        initSiteSpinner();
        initDataList(null);

        Button btnAdd = findViewById(R.id.pwdList_btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdd_Click(v);
            }
        });

        Button rv_btn_search = findViewById(R.id.rv_btn_search);
        rv_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_btn_search_Click(v);
            }
        });
    }

    protected void initSiteSpinner(){
        PwdBO bo = new PwdBO(this);
        //site 취득
        ArrayList<String> siteList = bo.selectSitesPwd();
        // ArrayAdapter 설정
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, siteList);
        rv_spinner.setAdapter(spinnerAdapter);
    }

    protected void initDataList(Map<String, String> searchCondition){
        PwdBO bo = new PwdBO(this);
        //전체 목록 취득
        ArrayList<PwdVO> dataSource = bo.selectPwdBySearchCondition(searchCondition);
        PwdListItemAdapter adapter = new PwdListItemAdapter(this, dataSource);
        rv_pwd.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    protected void btnAdd_Click(View v){
        Intent intent = new Intent(this, PwdDetailActivity.class);
        intent.putExtra("type", PwdConst.TYPE_ADD);
        startActivity(intent);
    }

    protected void rv_btn_search_Click(View v){
        String site = rv_spinner.getSelectedItem().toString();

        HashMap<String, String> map = new HashMap<>();
        map.put(PwdConst.PWD.COL_SITE, site);

       initDataList(map);
    }
}