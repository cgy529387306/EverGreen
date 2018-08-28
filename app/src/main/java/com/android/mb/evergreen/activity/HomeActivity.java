package com.android.mb.evergreen.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.utils.NavigationHelper;

public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private TextView mBtnRegister;
    private TextView mBtnLogin;
    private TextView mBtnAboutUs;
    private TextView mBtnContactUs;
    private TextView mBtnSwitchLanguage;
    private TextView mBtnSetting;
    private TextView mTvVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initOnClickListener();
    }

    private void initView(){
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnAboutUs = findViewById(R.id.btn_aboutus);
        mBtnContactUs = findViewById(R.id.btn_contactus);
        mBtnSwitchLanguage = findViewById(R.id.btn_switchlanguage);
        mBtnSetting = findViewById(R.id.btn_setting);
        mTvVersionCode = findViewById(R.id.tv_versioncode);
    }

    private void initOnClickListener() {
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mBtnAboutUs.setOnClickListener(this);
        mBtnContactUs.setOnClickListener(this);
        mBtnSwitchLanguage.setOnClickListener(this);
        mBtnSetting.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                NavigationHelper.startActivity(HomeActivity.this,RegisterActivity.class,null,true);
                break;
            case R.id.btn_login:
                NavigationHelper.startActivity(HomeActivity.this,LoginActivity.class,null,true);
                break;
            case R.id.btn_aboutus:
                break;
            case R.id.btn_contactus:
                break;
            case R.id.btn_switchlanguage:
                break;
            case R.id.btn_setting:
                break;

            default:
                break;
        }
    }
}
