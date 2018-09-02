package com.android.mb.evergreen.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.widget.CleanableEditText;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private CleanableEditText mEtAccount;
    private CleanableEditText mEtPwd;
    private CleanableEditText mEtConfirmPwd;
    private CleanableEditText mEtName;
    private CleanableEditText mEtOrg;
    private TextView mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initOnClickListener();
    }

    private void initView(){
        mEtAccount = findViewById(R.id.et_account);
        mEtPwd = findViewById(R.id.et_password);
        mEtConfirmPwd = findViewById(R.id.et_confirm_pwd);
        mEtName = findViewById(R.id.et_name);
        mEtOrg = findViewById(R.id.et_org);
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_forget_pwd).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                NavigationHelper.startActivity(RegisterActivity.this,LoginActivity.class,null,true);
                break;
            case R.id.btn_forget_pwd:
                NavigationHelper.startActivity(RegisterActivity.this,SetPwdActivity.class,null,true);
                break;
            case R.id.btn_register:
//                if (TextUtils.isEmpty(mEtAccount.getText().toString())){
//                    ToastHelper.showLongToast("手机号不能为空");
//                    return;
//                }
                NavigationHelper.startActivity(RegisterActivity.this,LoginActivity.class,null,true);
                break;
            default:
                break;
        }
    }
}
