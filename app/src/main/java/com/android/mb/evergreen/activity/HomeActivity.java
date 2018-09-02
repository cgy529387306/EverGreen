package com.android.mb.evergreen.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.utils.NavigationHelper;

public class HomeActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initOnClickListener();
    }

    private void initView(){
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_about).setOnClickListener(this);
        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_language).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                NavigationHelper.startActivity(HomeActivity.this,RegisterActivity.class,null,false);
                break;
            case R.id.btn_login:
                NavigationHelper.startActivity(HomeActivity.this,LoginActivity.class,null,false);
                break;
            case R.id.btn_about:
                NavigationHelper.startActivity(HomeActivity.this,AboutActivity.class,null,false);
                break;
            case R.id.btn_contact:
                NavigationHelper.startActivity(HomeActivity.this,ContactActivity.class,null,false);
                break;
            case R.id.btn_language:
                NavigationHelper.startActivity(HomeActivity.this,LoginActivity.class,null,false);
                break;
            case R.id.btn_setting:
                NavigationHelper.startActivity(HomeActivity.this,LoginActivity.class,null,false);
                break;
            default:
                break;
        }
    }
}
