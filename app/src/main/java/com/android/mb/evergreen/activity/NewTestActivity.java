package com.android.mb.evergreen.activity;

import android.os.Bundle;
import android.view.View;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.widget.CleanableEditText;

public class NewTestActivity extends BaseActivity implements View.OnClickListener{
    private CleanableEditText mEtName;
    private CleanableEditText mEtSerial;
    private CleanableEditText mEtNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        initView();
        initOnClickListener();
    }

    private void initView(){
        mEtName = findViewById(R.id.et_test_name);
        mEtSerial = findViewById(R.id.et_test_serial);
        mEtNum = findViewById(R.id.et_test_num);
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_history).setOnClickListener(this);
        findViewById(R.id.btn_manager).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_history:
                break;
            case R.id.btn_manager:
                break;
            case R.id.btn_setting:
                break;
            case R.id.btn_exit:
                break;
            default:
                break;
        }
    }
}
