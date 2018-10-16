package com.android.mb.evergreen.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.Category;
import com.android.mb.evergreen.entity.User;
import com.android.mb.evergreen.greendao.UserDao;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.ToastHelper;
import com.android.mb.evergreen.widget.CleanableEditText;

import java.util.Date;
import java.util.List;

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
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_login:
                NavigationHelper.startActivity(RegisterActivity.this,LoginActivity.class,null,true);
                break;
            case R.id.btn_forget_pwd:
                NavigationHelper.startActivity(RegisterActivity.this,SetPwdActivity.class,null,true);
                break;
            case R.id.btn_register:
                doRegister();
                break;
            default:
                break;
        }
    }

    private void doRegister(){
        String account = mEtAccount.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String confirmPwd = mEtConfirmPwd.getText().toString().trim();
        String name = mEtName.getText().toString().trim();
        String org = mEtOrg.getText().toString().trim();
        if (TextUtils.isEmpty(account)){
            ToastHelper.showLongToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            ToastHelper.showLongToast("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(name)){
            ToastHelper.showLongToast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(org)){
            ToastHelper.showLongToast("请输入机构名");
            return;
        }
        if (!pwd.equals(confirmPwd)){
            ToastHelper.showLongToast("密码不一致，请确认后重新输入");
            return;
        }
        UserDao userDao = GreenDaoManager.getInstance().getNewSession().getUserDao();

        List<User> userList = userDao.queryBuilder().where(UserDao.Properties.Account.eq(account)).list();
        if (userList==null || userList.size()==0){
            userDao.insert(new User(null,account,pwd,org,name,false));
            ToastHelper.showLongToast("注册成功");
            NavigationHelper.startActivity(RegisterActivity.this,LoginActivity.class,null,true);
        }else {
            ToastHelper.showLongToast("用户名已存在");
        }

    }

}
