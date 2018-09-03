package com.android.mb.evergreen.activity;

import android.os.Bundle;
import android.view.View;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.widget.CleanableEditText;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private CleanableEditText mEtAccount;
    private CleanableEditText mEtPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initOnClickListener();
    }

    private void initView(){
        mEtAccount = findViewById(R.id.et_account);
        mEtPwd = findViewById(R.id.et_password);
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_forget_pwd).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_login:
//                if (TextUtils.isEmpty(mEdtLoginName.getText().toString())){
//                    ToastHelper.showLongToast("用户名不能为空");
//                    return;
//                }
                //TODO  账号有无密码
                NavigationHelper.startActivity(LoginActivity.this,ManagerActivity.class,null,false);
                break;
            case R.id.btn_register:
                NavigationHelper.startActivity(LoginActivity.this,RegisterActivity.class,null,true);
                break;
            case R.id.btn_forget_pwd:
                NavigationHelper.startActivity(LoginActivity.this,SetPwdActivity.class,null,true);
                break;
            default:
                break;
        }
    }
}
