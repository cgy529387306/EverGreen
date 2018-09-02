package com.android.mb.evergreen.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.android.mb.evergreen.R;


/**
 * 引导页
 * @author cgy
 */

public class LoadingActivity extends AppCompatActivity {
    private static final int LOADING_TIME_OUT = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 去除信号栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                startActivity(new Intent(LoadingActivity.this,HomeActivity.class));
                finish();
            }

        }, LOADING_TIME_OUT);
    }


}
