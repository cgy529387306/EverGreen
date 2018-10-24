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
import com.android.mb.evergreen.entity.Examine;
import com.android.mb.evergreen.greendao.ExamineDao;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.ToastHelper;
import com.android.mb.evergreen.widget.CleanableEditText;
import com.android.mb.evergreen.widget.ZoomImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestImageActivity extends BaseActivity implements View.OnClickListener{

    private ZoomImageView mZoomImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);
        initView();
        initOnClickListener();
    }

    private void initView(){
        mZoomImageView = findViewById(R.id.zoomImage);
        mZoomImageView.setImageResource(R.mipmap.bg_home_top);
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }
}
