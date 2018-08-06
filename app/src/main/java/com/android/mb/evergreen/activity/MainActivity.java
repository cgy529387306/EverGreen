package com.android.mb.evergreen.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.adapter.MyFragmentPagerAdapter;
import com.android.mb.evergreen.fragment.HomeFragment;
import com.android.mb.evergreen.utils.ToastHelper;
import com.android.mb.evergreen.widget.FragmentViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FragmentViewPager fragmentViewPager;
    private ArrayList<Fragment> fragmentList;
    private TextView btn_home,btn_type,btn_history,btn_version;
    private TextView tv_title;
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
        fragmentViewPager = (FragmentViewPager) findViewById(R.id.fragmentViewPager);

        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        fragmentViewPager.setOffscreenPageLimit(fragmentList.size());
        btn_home.setOnClickListener(this);
        btn_type.setOnClickListener(this);
        btn_history.setOnClickListener(this);
        btn_version.setOnClickListener(this);
        showPager(0);
    }


    private void showPager(int position){
        if (position==0){
            tv_title.setText("首页");
        }else if (position==1){
            tv_title.setText("样品种类");
        }else if (position==2){
            tv_title.setText("历史数据");
        }else if (position==3){
            tv_title.setText("版本更新");
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
            default:
                break;
        }
    }

    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private long mLastClickTimeMills = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTimeMills > DOUBLE_CLICK_INTERVAL) {
            ToastHelper.showToast("再按一次返回退出");
            mLastClickTimeMills = System.currentTimeMillis();
            return;
        }
        finish();
    }

    // endregion 双击返回
}
