package com.practice.dev.msplashtypedemo;

import android.app.Application;

import com.practice.dev.msplashtypedemo.utils.SPUtils;

/**
 * Created by HY on 2017/5/8.
 */

public class App extends Application {

    public SPUtils sp;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = new SPUtils(getApplicationContext(), "splash");
    }
}
