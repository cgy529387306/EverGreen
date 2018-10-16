package com.android.mb.evergreen.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.adapter.ExamineAdapter;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.CurrentUser;
import com.android.mb.evergreen.entity.Examine;
import com.android.mb.evergreen.greendao.ExamineDao;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class QueryResultActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private ExamineAdapter mAdapter;
    private String testName;
    private String startTime;
    private String endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_result);
        initView();
        initOnClickListener();
        refreshData();
    }

    private void initView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ExamineAdapter(R.layout.item_examine,new ArrayList());
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
            case R.id.btn_output:
                doOutput();
                break;
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void doOutput(){
        NavigationHelper.startActivity(QueryResultActivity.this,OutputActivity.class,null,false);
    }

    public void refreshData(){
        testName = getIntent().getStringExtra("testName");
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        List<Examine> dataList = new ArrayList<>();
        if (CurrentUser.getInstance().isAdmin()){
            dataList = GreenDaoManager.getInstance().getNewSession().getExamineDao().queryBuilder().where(ExamineDao.Properties.Name.eq(testName)).build().list();
        }else{
            dataList = GreenDaoManager.getInstance().getNewSession().getExamineDao().queryBuilder().where(ExamineDao.Properties.UserId.eq(CurrentUser.getInstance().getId())).where(ExamineDao.Properties.Name.eq(testName)).build().list();
        }
        mAdapter.setNewData(dataList);
    }

    private void doDelete() {
        final List<Examine> selectCategory = new ArrayList<>();
        for (Examine examine : mAdapter.getData()) {
            if (examine.getChecked()) {
                selectCategory.add(examine);
            }
        }
        if (Helper.isEmpty(selectCategory)) {
            ToastHelper.showLongToast("请选择要删除的数据");
        } else {
            new AlertDialog.Builder(this).setMessage("确定要删除这些数据？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (Examine examine : selectCategory) {
                                GreenDaoManager.getInstance().getNewSession().getExamineDao().delete(examine);
                            }
                            refreshData();
                        }
                    }).setNegativeButton("取消", null).show();

        }

    }



}
