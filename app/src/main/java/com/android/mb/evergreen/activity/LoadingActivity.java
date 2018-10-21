package com.android.mb.evergreen.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.CurrentUser;
import com.android.mb.evergreen.entity.User;
import com.android.mb.evergreen.greendao.UserDao;
import com.android.mb.evergreen.utils.PreferencesHelper;


/**
 * 引导页
 * @author cgy
 */

public class LoadingActivity extends AppCompatActivity {
    private static final int LOADING_TIME_OUT = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 去除信号栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        insertTestData();
        new Handler().postDelayed(new Runnable() {

            public void run() {
                if (CurrentUser.getInstance().isLogin()){
                    startActivity(new Intent(LoadingActivity.this,TestImageActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(LoadingActivity.this,HomeActivity.class));
                    finish();
                }

            }

        }, LOADING_TIME_OUT);
    }

    private void insertTestData(){
        boolean isFirstIn = PreferencesHelper.getInstance().getBoolean("isFirstIn",true);
        if (isFirstIn){
            PreferencesHelper.getInstance().putBoolean("isFirstIn",false);
            UserDao userDao = GreenDaoManager.getInstance().getNewSession().getUserDao();
            userDao.insert(new User(null,"admin1","111111","系统管理","管理员1",true));
            userDao.insert(new User(null,"admin2","222222","系统管理","管理员2",true));
            userDao.insert(new User(null,"admin3","333333","系统管理","管理员3",true));
        }
    }


}
