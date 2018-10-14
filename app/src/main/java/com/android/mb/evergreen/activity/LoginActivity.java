package com.android.mb.evergreen.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.CurrentUser;
import com.android.mb.evergreen.entity.User;
import com.android.mb.evergreen.greendao.UserDao;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.ToastHelper;
import com.android.mb.evergreen.widget.CleanableEditText;

import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private CleanableEditText mEtAccount;
    private CleanableEditText mEtPwd;
    private TextView mTvHint;
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
        mTvHint = findViewById(R.id.tv_hint);
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
                doLogin();
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

    private void doLogin(){
        String account = mEtAccount.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(account)){
            ToastHelper.showLongToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            ToastHelper.showLongToast("请输入密码");
            return;
        }
        UserDao userDao = GreenDaoManager.getInstance().getNewSession().getUserDao();
        List<User> userList = userDao.queryBuilder().where(UserDao.Properties.Account.eq(account)).list();
        if (userList==null || userList.size()==0){
            ToastHelper.showLongToast("用户名不存在");
            mTvHint.setVisibility(View.VISIBLE);
            mTvHint.setText("用户名不存在");
        }else {
            User user = userList.get(0);
            if (pwd.equals(user.getPassword())){
                mTvHint.setVisibility(View.GONE);
                ToastHelper.showLongToast("登录成功");
                CurrentUser.getInstance().login(user);
                NavigationHelper.startActivity(LoginActivity.this,ManagerActivity.class,null,true);
            }else{
                ToastHelper.showLongToast("密码不正确");
                mTvHint.setVisibility(View.VISIBLE);
                mTvHint.setText("密码不正确");
            }
        }

    }
}
