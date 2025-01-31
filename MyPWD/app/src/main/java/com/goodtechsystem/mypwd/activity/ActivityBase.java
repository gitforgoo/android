package com.goodtechsystem.mypwd.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.goodtechsystem.mypwd.R;

public class ActivityBase extends AppCompatActivity {
    private boolean isBackPressedOnce = false;
    private Handler handler = new Handler();

    private long backKeyPressedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 뒤로가기 버튼 처리
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                /*
                if (isBackPressedOnce) {
                    finishAffinity(); // Activity 종료
                    System.gc(); // 메모리 해제 (가비지 컬렉션 호출)
                } else {
                    isBackPressedOnce = true;

                    Toast.makeText(ActivityBase.this, getString(R.string.warn_backbutton_press), Toast.LENGTH_SHORT).show();

                    // 2초 내에 다시 뒤로가기 버튼이 눌리지 않으면 초기화
                    handler.postDelayed(() -> isBackPressedOnce = false, 2000);

                    finish();
                }
                */
                if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                    backKeyPressedTime = System.currentTimeMillis();
                    Toast.makeText(ActivityBase.this, getString(R.string.warn_backbutton_press), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    finishAffinity();
                }
            }
        };

        // 뒤로가기 버튼 처리를 활성화
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
