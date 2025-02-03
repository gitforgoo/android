package com.goodtechsystem.mypwd.util;

import android.app.Application;

public class MyPWDApplication extends Application {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
