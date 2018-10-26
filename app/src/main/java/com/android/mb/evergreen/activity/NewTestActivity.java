package com.android.mb.evergreen.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class NewTestActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTvTestName;
    private CleanableEditText mEtSerial;
    private CleanableEditText mEtNum;
    private TextView mTvName;
    public static final int REQUEST_CODE_ALBUM = 0xf4;
    public static final int REQUEST_CODE_PERMISSION = 0xd5;
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
        if (ContextCompat.checkSelfPermission(NewTestActivity.this,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            selectImage();
        }else{
            ActivityCompat.requestPermissions(NewTestActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_PERMISSION);
        }
    }

    /**
     * 图片选择
     */
    public void selectImage() {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        startActivityForResult(intent, REQUEST_CODE_ALBUM);
    }

    /**
     * 处理图片选择结果
     * @param data
     */
    private void handleImageSelect(Intent data) {
        if(null == data) {
            return;
        }
        List<String> selectImages = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        if(null == selectImages || 0 == selectImages.size()) {
            return;
        }
        String testName = mTvTestName.getText().toString().trim();
        String testSerial = mEtSerial.getText().toString().trim();
        String testNum = mEtNum.getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putString("testName",testName);
        bundle.putString("testSerial",testSerial);
        bundle.putString("testNum",testNum);
        bundle.putString("imagePath",selectImages.get(0));
        NavigationHelper.startActivity(NewTestActivity.this,TestImageActivity.class,bundle,false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ALBUM) {
            handleImageSelect(data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else{
                ToastHelper.showToast("权限已被拒绝,请到设置页面打开存储权限");
                startAppSettings();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //打开系统应用设置(ACTION_APPLICATION_DETAILS_SETTINGS:系统设置权限)
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
