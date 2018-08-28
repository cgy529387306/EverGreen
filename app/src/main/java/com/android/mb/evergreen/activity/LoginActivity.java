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

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private ClearableEditText mEdtLoginName;
    private TextView mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initOnClickListener();
    }

    private void initView(){
        mEdtLoginName = findViewById(R.id.edt_user_loginname);
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
                if (TextUtils.isEmpty(mEdtLoginName.getText().toString())){
                    ToastHelper.showLongToast("用户名不能为空");
                    return;
                }
                //TODO  账号有无密码
                NavigationHelper.startActivity(LoginActivity.this,SetPwdActivity.class,null,true);
//                NavigationHelper.startActivity(LoginActivity.this,MainActivity.class,null,true);
                break;
            default:
                break;
        }
    }
}
