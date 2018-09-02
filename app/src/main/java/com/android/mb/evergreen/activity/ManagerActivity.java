package com.android.mb.evergreen.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.android.mb.evergreen.R;

public class ManagerActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        initView();
        initOnClickListener();
    }

    private void initView(){
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_new_test).setOnClickListener(this);
        findViewById(R.id.btn_manager_data).setOnClickListener(this);
        findViewById(R.id.btn_test_setting).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_new_test:
                break;
            case R.id.btn_manager_data:
                break;
            case R.id.btn_test_setting:
                break;
            default:
                break;
        }
    }
}
