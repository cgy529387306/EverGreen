package com.android.mb.evergreen.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.Examine;
import com.android.mb.evergreen.greendao.ExamineDao;
import com.android.mb.evergreen.utils.NavigationHelper;

public class TestResultActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTvName;
    private TextView mTvTime;
    private TextView mTvNum;
    private TextView mTvUser;
    private TextView mTvNo;
    private TextView mTvSerial;
    private TextView mTvResult;
    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        initView();
        initOnClickListener();
        initData();
    }

    private void initView(){
        mTvTime = findViewById(R.id.tv_time);
        mTvName = findViewById(R.id.tv_name);
        mTvNum = findViewById(R.id.tv_num);
        mTvUser = findViewById(R.id.tv_user);
        mTvNo = findViewById(R.id.tv_no);
        mTvSerial = findViewById(R.id.tv_serial);
        mTvResult = findViewById(R.id.tv_result);
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_output).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    private void initData(){
        id = getIntent().getLongExtra("id",0);
        ExamineDao examineDao = GreenDaoManager.getInstance().getNewSession().getExamineDao();
        Examine examine = examineDao.loadByRowId(id);
        if (examine!=null){
            mTvTime.setText("测试时间："+examine.getTestDate());
            mTvName.setText("测试名称："+examine.getName());
            mTvNum.setText("测试编号："+examine.getNum());
            mTvUser.setText("用户名称："+examine.getUserName());
            mTvNo.setText("样品编号："+String.valueOf(examine.getId()));
            mTvSerial.setText("测试批号："+examine.getSerial());
            mTvResult.setText("测试结果："+examine.getResult());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_delete:
                doDelete();
                break;
            case R.id.btn_output:
                NavigationHelper.startActivity(TestResultActivity.this,OutputActivity.class,null,false);
                break;
            default:
                break;
        }
    }

    private void doDelete() {
        new AlertDialog.Builder(this).setMessage("确定要删除这条数据？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GreenDaoManager.getInstance().getNewSession().getCategoryDao().deleteByKey(id);
                        finish();
                    }
                }).setNegativeButton("取消", null).show();

    }
}
