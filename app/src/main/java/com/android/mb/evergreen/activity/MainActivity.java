package com.android.mb.evergreen.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.adapter.MyFragmentPagerAdapter;
import com.android.mb.evergreen.fragment.CategoryFragment;
import com.android.mb.evergreen.fragment.HistoryFragment;
import com.android.mb.evergreen.fragment.HomeFragment;
import com.android.mb.evergreen.utils.ToastHelper;
import com.android.mb.evergreen.widget.FragmentViewPager;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private FragmentViewPager fragmentViewPager;
    private ArrayList<Fragment> fragmentList;
    private TextView btn_home,btn_type,btn_history,btn_version;
    private TextView tv_title;
    private ImageView iv_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        btn_home = findViewById(R.id.btn_home);
        btn_type = findViewById(R.id.btn_type);
        btn_history = findViewById(R.id.btn_history);
        btn_version = findViewById(R.id.btn_version);
        tv_title = findViewById(R.id.tv_title);
        iv_camera = findViewById(R.id.iv_camera);
        fragmentViewPager = (FragmentViewPager) findViewById(R.id.fragmentViewPager);

        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CategoryFragment());
        fragmentList.add(new HistoryFragment());
        fragmentList.add(new HomeFragment());
        fragmentViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        fragmentViewPager.setOffscreenPageLimit(fragmentList.size());
        btn_home.setOnClickListener(this);
        btn_type.setOnClickListener(this);
        btn_history.setOnClickListener(this);
        btn_version.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        showPager(0);
    }

    private void showPager(int position){
        if (position==0){
            iv_camera.setVisibility(View.VISIBLE);
            iv_camera.setImageResource(R.mipmap.ic_camera);
            tv_title.setText(R.string.mb_home_text);
        }else if (position==1){
            iv_camera.setVisibility(View.VISIBLE);
            iv_camera.setImageResource(R.mipmap.ic_add);
            tv_title.setText(R.string.mb_type_text);
        }else if (position==2){
            iv_camera.setVisibility(View.GONE);
            tv_title.setText(R.string.mb_history_text);
        }else if (position==3){
            iv_camera.setVisibility(View.GONE);
            tv_title.setText(R.string.mb_version_text);
        }

        btn_home.setSelected(position==0);
        btn_type.setSelected(position==1);
        btn_history.setSelected(position==2);
        btn_version.setSelected(position==3);
        fragmentViewPager.setCurrentItem(position);
    }




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
            case R.id.iv_camera:

                break;
            default:
                break;
        }
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
