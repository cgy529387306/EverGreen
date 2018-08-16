package com.android.mb.evergreen.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.ToastHelper;
import com.android.mb.evergreen.widget.ClearableEditText;

public class SetPwdActivity extends BaseActivity implements View.OnClickListener{

    private ClearableEditText mEdtPwd;
    private TextView mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpwd);
        initView();
        initOnClickListener();
    }

    private void initView(){
        mEdtPwd = findViewById(R.id.edt_user_pwd);
        mBtnLogin = findViewById(R.id.btn_login);
    }

    private void initOnClickListener() {
        mBtnLogin.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(mEdtPwd.getText().toString())){
                    ToastHelper.showLongToast("密码不能为空");
                    return;
                }
                NavigationHelper.startActivity(SetPwdActivity.this,MainActivity.class,null,true);
                break;
            default:
                break;
        }
    }
}
