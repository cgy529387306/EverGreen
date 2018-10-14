package com.android.mb.evergreen.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.Category;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.ToastHelper;
import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryDataActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTvTestName;
    private TextView mTvStartTime;
    private TextView mTvEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_data);
        initView();
        initOnClickListener();
    }

    private void initView(){
        mTvTestName = findViewById(R.id.tv_test_name);
        mTvStartTime = findViewById(R.id.tv_start_time);
        mTvEndTime = findViewById(R.id.tv_end_time);
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_test_name).setOnClickListener(this);
        findViewById(R.id.btn_start_time).setOnClickListener(this);
        findViewById(R.id.btn_end_time).setOnClickListener(this);
        findViewById(R.id.btn_history).setOnClickListener(this);
        findViewById(R.id.btn_manager).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_name:
                showTypeList();
                break;
            case R.id.btn_start_time:
                showStartTimePicker();
                break;
            case R.id.btn_end_time:
                showEndTimePicker();
                break;
            case R.id.btn_history:
                break;
            case R.id.btn_manager:
                break;
            case R.id.btn_setting:
                break;
            case R.id.btn_exit:
                break;
            case R.id.btn_query:
                doQuery();
                break;
            default:
                break;
        }
    }

    private void showStartTimePicker(){
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTvStartTime.setText(Helper.date2String(date,"yyyy-MM-dd"));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();

        pvTime.show();
    }

    private void showEndTimePicker(){
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTvEndTime.setText(Helper.date2String(date,"yyyy-MM-dd"));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();

        pvTime.show();
    }

    /**
     * 列表
     */
    private void showTypeList() {
        final List<Category> dataList = GreenDaoManager.getInstance().getNewSession().getCategoryDao().queryBuilder().build().list();
        if (Helper.isEmpty(dataList)){
            ToastHelper.showLongToast("暂无检测类型，请先添加检测类型");
            NavigationHelper.startActivity(QueryDataActivity.this,ManageTypeActivity.class,null,false);
        }else{
            List<String> titleList = new ArrayList<>();
            for (Category category:dataList){
                titleList.add(category.getName());
            }
            String[] strings = new String[titleList.size()];
            titleList.toArray(strings);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("检测类型");
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setItems(strings, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (dataList.size()>which){
                        Category category = dataList.get(which);
                        mTvTestName.setText(category.getName());
                    }
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    private void doQuery(){
        String testName = mTvTestName.getText().toString().trim();
        String startTime = mTvStartTime.getText().toString().trim();
        String endTime = mTvEndTime.getText().toString().trim();
        if (Helper.isEmpty(testName)){
            ToastHelper.showLongToast("请选择检测类型");
            return;
        }
        if (Helper.isEmpty(startTime)){
            ToastHelper.showLongToast("请选择开始时间");
            return;
        }
        if (Helper.isEmpty(endTime)){
            ToastHelper.showLongToast("请选择结束时间");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("testName",testName);
        bundle.putString("startTime",startTime);
        bundle.putString("endTime",endTime);
        NavigationHelper.startActivity(QueryDataActivity.this,QueryResultActivity.class,bundle,false);
    }

}
