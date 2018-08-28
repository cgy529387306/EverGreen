package com.android.mb.evergreen.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.adapter.MyFragmentPagerAdapter;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.Category;
import com.android.mb.evergreen.entity.Examine;
import com.android.mb.evergreen.fragment.CategoryFragment;
import com.android.mb.evergreen.fragment.HistoryFragment;
import com.android.mb.evergreen.fragment.HomeFragment;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.ToastHelper;
import com.android.mb.evergreen.widget.FragmentViewPager;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private FragmentViewPager mFragmentViewPager;
    private ArrayList<Fragment> mFragmentList;
    private TextView mBtnHome,mBtnType,mBtnHistory,mBtnVersion;
    private TextView mTvTitle;
    private ImageView mIvAction;
    public static final int REQUEST_CODE_ALBUM = 0xf4;
    private CategoryFragment mCategoryFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mBtnHome = findViewById(R.id.btn_home);
        mBtnType = findViewById(R.id.btn_type);
        mBtnHistory = findViewById(R.id.btn_history);
        mBtnVersion = findViewById(R.id.btn_version);
        mTvTitle = findViewById(R.id.tv_title);
        mIvAction = findViewById(R.id.iv_action);
        mFragmentViewPager = (FragmentViewPager) findViewById(R.id.fragmentViewPager);

        mCategoryFragment = new CategoryFragment();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(mCategoryFragment);
        mFragmentList.add(new HistoryFragment());
        mFragmentList.add(new HistoryFragment());
        mFragmentViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mFragmentViewPager.setOffscreenPageLimit(mFragmentList.size());
        mBtnHome.setOnClickListener(this);
        mBtnType.setOnClickListener(this);
        mBtnHistory.setOnClickListener(this);
        mBtnVersion.setOnClickListener(this);
        mIvAction.setOnClickListener(this);
        showPager(0);
    }

    private void showPager(int position){
        if (position==0){
            mIvAction.setVisibility(View.VISIBLE);
            mIvAction.setImageResource(R.mipmap.ic_camera);
            mTvTitle.setText(R.string.mb_home_text);
        }else if (position==1){
            mIvAction.setVisibility(View.VISIBLE);
            mIvAction.setImageResource(R.mipmap.ic_add);
            mTvTitle.setText(R.string.mb_type_text);
        }else if (position==2){
            mIvAction.setVisibility(View.GONE);
            mTvTitle.setText(R.string.mb_history_text);
        }else if (position==3){
            mIvAction.setVisibility(View.GONE);
            mTvTitle.setText(R.string.mb_version_text);
        }

        mBtnHome.setSelected(position==0);
        mBtnType.setSelected(position==1);
        mBtnHistory.setSelected(position==2);
        mBtnVersion.setSelected(position==3);
        mFragmentViewPager.setCurrentItem(position);
    }


    /**
     * 图片选择
     */
    public void selectImage() {
        Intent intent = new Intent(MainActivity.this, MultiImageSelectorActivity.class);
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
        PhotoPreviewActivity.preview(this, selectImages.get(0));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ALBUM) {
            handleImageSelect(data);
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                showPager(0);
                break;

            case R.id.btn_type:
                showPager(1);
                break;
            case R.id.btn_history:
                showPager(2);
                break;
            case R.id.btn_version:
                showPager(3);
                break;
            case R.id.iv_action:
                if (mFragmentViewPager.getCurrentItem()==0){
                    RxPermissions.getInstance(MainActivity.this)
                            .request(Manifest.permission.READ_EXTERNAL_STORAGE)//多个权限用","隔开
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean aBoolean) {
                                    if (aBoolean) {
                                        selectImage();
                                    } else {
                                        Log.i("permissions", "btn_more_sametime：" + aBoolean);
                                    }
                                }
                            });
                }else if (mFragmentViewPager.getCurrentItem()==1){
                    showAlert();
                }
                break;
            default:
                break;
        }
    }


    public void showAlert(){
        final View view = LayoutInflater.from(this).inflate(R.layout.layout_edit, null);//这里必须是final的
        final EditText et = (EditText) view.findViewById(R.id.et_input);
        new AlertDialog.Builder(this).setTitle("请输入检测类型")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = et.getText().toString();
                        if (TextUtils.isEmpty(name)){
                            ToastHelper.showToast("请输入检测类型");
                        }else{
                            insertData(name);
                        }
                    }
                }).setNegativeButton("取消",null).show();
    }

    private void insertData(String name){
        Category category = new Category(null,name,Helper.date2String(new Date()));
        GreenDaoManager.getInstance().getNewSession().getCategoryDao().insert(category);
        mCategoryFragment.refreshData();
    }

    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private long mLastClickTimeMills = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTimeMills > DOUBLE_CLICK_INTERVAL) {
            ToastHelper.showToast(getString(R.string.mb_exit_hint));
            mLastClickTimeMills = System.currentTimeMillis();
            return;
        }
        finish();
    }
}
