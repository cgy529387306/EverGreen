package com.android.mb.evergreen.activity;

import android.os.Bundle;
import android.view.View;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.widget.CleanableEditText;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private CleanableEditText mEdtLoginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initOnClickListener();
    }

    private void initView(){
        mEdtLoginName = findViewById(R.id.edt_user_name);
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
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
            default:
                break;
        }
    }
}
