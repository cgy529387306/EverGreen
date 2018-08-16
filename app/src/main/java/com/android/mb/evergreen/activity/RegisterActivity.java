package com.android.mb.evergreen.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.ToastHelper;
import com.android.mb.evergreen.widget.ClearableEditText;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private ClearableEditText mEdtPhone;
    private ClearableEditText mEdtPwd;
    private ClearableEditText mEdtComfirmPwd;
    private ClearableEditText mEdtSurname;
    private ClearableEditText mEdtName;
    private ClearableEditText mEdtCompany;
    private TextView mBtnLogin;
    private TextView mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initOnClickListener();
    }

    private void initView(){
        mEdtPhone = findViewById(R.id.edt_user_phone);
        mEdtPwd = findViewById(R.id.edt_user_pwd);
        mEdtComfirmPwd = findViewById(R.id.edt_user_comfirmpwd);
        mEdtSurname = findViewById(R.id.edt_user_surname);
        mEdtName = findViewById(R.id.edt_user_name);
        mEdtCompany = findViewById(R.id.edt_user_company);
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnLogin = findViewById(R.id.btn_login);
    }

    private void initOnClickListener() {
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if (TextUtils.isEmpty(mEdtPhone.getText().toString())){
                    ToastHelper.showLongToast("手机号不能为空");
                    return;
                }
                NavigationHelper.startActivity(RegisterActivity.this,LoginActivity.class,null,true);
                break;
            case R.id.btn_login:
                NavigationHelper.startActivity(RegisterActivity.this,LoginActivity.class,null,true);
                break;

            default:
                break;
        }
    }
}
