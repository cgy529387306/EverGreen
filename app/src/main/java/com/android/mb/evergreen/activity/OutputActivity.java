package com.android.mb.evergreen.activity;

import android.os.Bundle;
import android.view.View;

import com.android.mb.evergreen.R;

public class OutputActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        initView();
        initOnClickListener();
    }

    private void initView(){

    }

    private void initOnClickListener() {
        findViewById(R.id.btn_email).setOnClickListener(this);
        findViewById(R.id.btn_print).setOnClickListener(this);
        findViewById(R.id.btn_sdcard).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_email:
                break;
            case R.id.btn_print:
                break;
            case R.id.btn_sdcard:
                break;
            default:
                break;
        }
    }
}
