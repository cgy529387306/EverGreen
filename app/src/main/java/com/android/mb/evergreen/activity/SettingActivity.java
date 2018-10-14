package com.android.mb.evergreen.activity;

import android.os.Bundle;
import android.view.View;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.utils.NavigationHelper;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initOnClickListener();
    }

    private void initView(){
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_test_type).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_test_type:
                NavigationHelper.startActivity(SettingActivity.this,ManageTypeActivity.class,null,false);
                break;
            default:
                break;
        }
    }
}
