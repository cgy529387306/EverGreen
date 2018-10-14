package com.android.mb.evergreen.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.adapter.CategoryAdapter;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.Category;
import com.android.mb.evergreen.entity.CurrentUser;
import com.android.mb.evergreen.greendao.CategoryDao;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.ToastHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageTypeActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        initView();
        initOnClickListener();
        refreshData();
    }

    private void initView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CategoryAdapter(R.layout.item_category,new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                doDelete();
                break;
            case R.id.btn_add:
                doAdd();
                break;
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void doAdd(){
        final View view = LayoutInflater.from(this).inflate(R.layout.layout_edit, null);//这里必须是final的
        final EditText et = (EditText) view.findViewById(R.id.et_input);
        new AlertDialog.Builder(this).setTitle("请输入检测类型")
                .setView(view)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = et.getText().toString();
                        CategoryDao categoryDao = GreenDaoManager.getInstance().getNewSession().getCategoryDao();
                        List<Category> categoryList = categoryDao.queryBuilder().where(CategoryDao.Properties.Name.eq(name)).list();
                        if (TextUtils.isEmpty(name)){
                            ToastHelper.showToast("请输入检测类型");
                        }else if (Helper.isNotEmpty(categoryList)){
                            ToastHelper.showLongToast("检测类型已存在");
                        }else{
                            Category category = new Category(null,name,Helper.date2String(new Date()), CurrentUser.getInstance().getId(),false);
                            categoryDao.insert(category);
                            refreshData();
                        }
                    }
                }).setNegativeButton("取消",null).show();

    }

    public void refreshData(){
        List<Category> dataList = GreenDaoManager.getInstance().getNewSession().getCategoryDao().queryBuilder().build().list();
        mAdapter.setNewData(dataList);
    }

    private void doDelete() {
        final List<Category> selectCategory = new ArrayList<>();
        for (Category category : mAdapter.getData()) {
            if (category.getIsChecked()) {
                selectCategory.add(category);
            }
        }
        if (Helper.isEmpty(selectCategory)) {
            ToastHelper.showLongToast("请选择要删除的类型");
        } else {
            new AlertDialog.Builder(this).setMessage("确定要删除这些类型？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (Category category : selectCategory) {
                                GreenDaoManager.getInstance().getNewSession().getCategoryDao().delete(category);
                            }
                            refreshData();
                        }
                    }).setNegativeButton("取消", null).show();

        }

    }



}
