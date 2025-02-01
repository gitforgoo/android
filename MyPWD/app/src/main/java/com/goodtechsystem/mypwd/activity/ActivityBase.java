package com.goodtechsystem.mypwd.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.bo.PwdBO;
import com.goodtechsystem.mypwd.util.JsonHelper;
import com.goodtechsystem.mypwd.vo.PwdVO;

import java.util.ArrayList;

public class ActivityBase extends AppCompatActivity {

    protected long backKeyPressedTime = 0L;

    protected Toolbar toolbar;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    protected void setupToolbar(int toolbarId) {
        toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item != null) {
            if (item.getItemId() == R.id.mi_backup) {
                backupData();
                return true;
            } else if (item.getItemId() == R.id.mi_restore) {
                restoreData();
                return true;
            } else if(item.getItemId() == R.id.mi_terminate){
                appTerminate();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    protected void backupData(){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        openDirectoryLauncher.launch(intent);
    }

    protected void restoreData(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // 모든 파일을 선택하도록 설정
        intent.addCategory(Intent.CATEGORY_OPENABLE); // 선택 가능한 파일만 열리도록 설정
        //startActivityForResult(intent, 1); // 파일 선택기 호출
        openFileLauncher.launch(intent);

    }

    private ActivityResultLauncher<Intent> openDirectoryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    try {
                        ParcelFileDescriptor jsonFileDescriptor = this.getContentResolver().openFileDescriptor(result.getData().getData(), "w");

                        PwdBO bo = new PwdBO(this);
                        ArrayList<PwdVO> lst = bo.selectAllPwd();

                        JsonHelper jsonHelper = new JsonHelper(this);
                        jsonHelper.saveListToJson(lst, jsonFileDescriptor);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

    private final ActivityResultLauncher<Intent> openFileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    try {
                        ParcelFileDescriptor jsonFileDescriptor = getContentResolver().openFileDescriptor(result.getData().getData(), "r");

                        JsonHelper jsonHelper = new JsonHelper(this);
                        jsonHelper.saveJsonToData(jsonFileDescriptor);

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

    protected void appTerminate(){
        finishAffinity();;
    }
}
