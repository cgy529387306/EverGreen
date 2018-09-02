package com.android.mb.evergreen.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.android.mb.evergreen.R;

public class AboutActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        initOnClickListener();
    }

    private void initView(){
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                finish();
                break;
            case R.id.btn_exit:
                finish();
                break;
            default:
                break;
        }
    }
}
