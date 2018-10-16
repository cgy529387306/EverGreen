package com.android.mb.evergreen.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.app.MBApplication;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.Category;
import com.android.mb.evergreen.entity.CurrentUser;
import com.android.mb.evergreen.entity.User;
import com.android.mb.evergreen.greendao.CategoryDao;
import com.android.mb.evergreen.greendao.UserDao;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.PreferencesHelper;

import java.util.Date;
import java.util.List;

public class ManagerActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        initView();
        initOnClickListener();
        insertTestData();
    }

    private void initView(){
        String name = CurrentUser.getInstance().getName();
        mTvName = findViewById(R.id.tv_name);
        mTvName.setText("欢迎回来"+name);
        findViewById(R.id.btn_test_setting).setVisibility(CurrentUser.getInstance().isAdmin()?View.VISIBLE:View.GONE);
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_new_test).setOnClickListener(this);
        findViewById(R.id.btn_manager_data).setOnClickListener(this);
        findViewById(R.id.btn_test_setting).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                CurrentUser.getInstance().loginOut();
                NavigationHelper.startActivity(ManagerActivity.this,LoginActivity.class,null,true);
                break;
            case R.id.btn_new_test:
                NavigationHelper.startActivity(ManagerActivity.this,NewTestActivity.class,null,false);
                break;
            case R.id.btn_manager_data:
                NavigationHelper.startActivity(ManagerActivity.this,QueryDataActivity.class,null,false);
                break;
            case R.id.btn_test_setting:
                NavigationHelper.startActivity(ManagerActivity.this,SettingActivity.class,null,false);
                break;
            default:
                break;
        }
    }

    private void insertTestData(){
        CategoryDao categoryDao = GreenDaoManager.getInstance().getNewSession().getCategoryDao();
        List<Category> categoryList = categoryDao.queryBuilder().list();
        if (Helper.isEmpty(categoryList)){
            Category category1 = new Category(null,"检测类型1",Helper.date2String(new Date()), CurrentUser.getInstance().getId(),false);
            categoryDao.insert(category1);

            Category category2 = new Category(null,"检测类型2",Helper.date2String(new Date()), CurrentUser.getInstance().getId(),false);
            categoryDao.insert(category2);

            Category category3 = new Category(null,"检测类型3",Helper.date2String(new Date()), CurrentUser.getInstance().getId(),false);
            categoryDao.insert(category3);
        }
    }
}
