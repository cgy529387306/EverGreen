package com.android.mb.evergreen.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.Category;
import com.android.mb.evergreen.entity.CurrentUser;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.ToastHelper;
import com.android.mb.evergreen.widget.CleanableEditText;

import java.util.ArrayList;
import java.util.List;

public class NewTestActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTvTestName;
    private CleanableEditText mEtSerial;
    private CleanableEditText mEtNum;
    private TextView mTvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        initView();
        initOnClickListener();
    }

    private void initView(){
        String name = CurrentUser.getInstance().getName();
        mTvName = findViewById(R.id.tv_name);
        mTvName.setText("欢迎回来"+name);
        mTvTestName = findViewById(R.id.tv_test_name);
        mEtSerial = findViewById(R.id.et_test_serial);
        mEtNum = findViewById(R.id.et_test_num);
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_test_name).setOnClickListener(this);
        findViewById(R.id.btn_history).setOnClickListener(this);
        findViewById(R.id.btn_manager).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);
        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_name:
                showTypeList();
                break;
            case R.id.btn_history:
                break;
            case R.id.btn_manager:
                break;
            case R.id.btn_test:
                doTest();
                break;
            case R.id.btn_setting:
                break;
            case R.id.btn_exit:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 列表
     */
    private void showTypeList() {
        final List<Category> dataList = GreenDaoManager.getInstance().getNewSession().getCategoryDao().queryBuilder().build().list();
        if (Helper.isEmpty(dataList)){
            ToastHelper.showLongToast("暂无检测类型，请先添加检测类型");
            NavigationHelper.startActivity(NewTestActivity.this,ManageTypeActivity.class,null,false);
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

    private void doTest(){
        String testName = mTvTestName.getText().toString().trim();
        String testSerial = mEtSerial.getText().toString().trim();
        String testNum = mEtNum.getText().toString().trim();
        if (Helper.isEmpty(testName)){
            ToastHelper.showLongToast("请选择检测类型");
            return;
        }
        if (Helper.isEmpty(testSerial)){
            ToastHelper.showLongToast("请输入测试批号");
            return;
        }
        if (Helper.isEmpty(testNum)){
            ToastHelper.showLongToast("请输入样品编号");
            return;
        }
        NavigationHelper.startActivity(NewTestActivity.this,TestImageActivity.class,null,false);

//        ExamineDao examineDao = GreenDaoManager.getInstance().getNewSession().getExamineDao();
//        Examine examine = new Examine(null,testName,Helper.date2String(new Date()),testSerial,testNum,CurrentUser.getInstance().getId(),CurrentUser.getInstance().getName(),"阳性","test",false);
//        long id = examineDao.insert(examine);
//        Bundle bundle = new Bundle();
//        bundle.putLong("id",id);
//        NavigationHelper.startActivity(NewTestActivity.this,TestResultActivity.class,bundle,false);
    }
}
