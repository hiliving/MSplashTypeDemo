package com.practice.dev.msplashtypedemo.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.practice.dev.msplashtypedemo.R;

/**
 * 自动页显示完后，进入的主页面
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
