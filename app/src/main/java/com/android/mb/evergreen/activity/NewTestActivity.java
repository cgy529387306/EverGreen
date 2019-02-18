package com.android.mb.evergreen.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.camera.CameraColorPickerPreview;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.Category;
import com.android.mb.evergreen.entity.CurrentUser;
import com.android.mb.evergreen.entity.Examine;
import com.android.mb.evergreen.greendao.ExamineDao;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.ToastHelper;
import com.android.mb.evergreen.widget.CleanableEditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewTestActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTvTestName;
    private CleanableEditText mEtSerial;
    private CleanableEditText mEtNum;
    private TextView mTvName;
    public static final int REQUEST_CODE_ALBUM = 0xf4;
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
        startActivityForResult(new Intent(this, TestActivity.class), REQUEST_CODE_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ALBUM) {
            handleImageSelect(data);
        }
    }

    /**
     * 处理图片选择结果
     */
    private void handleImageSelect(Intent data) {
        if (data==null){
            return;
        }
        String imagePath = data.getStringExtra("imagePath");
        List<int[]> color2List = CameraColorPickerPreview.color2List;
        int[] t = CameraColorPickerPreview.colorEnd(color2List);
        BigDecimal tBigDecimal = RgbToGray(t);
        String tvT_Data = "\nT线平均值RGB色码:" + t[0] + "," + t[1] + "," + t[2] + "\n" + "跟公式套用后结果：" + tBigDecimal;
//        tvT.setText(tvT_Data);

        List<int[]> colorList = CameraColorPickerPreview.colorList;
        int[] c = CameraColorPickerPreview.colorEnd(colorList);
        BigDecimal cBigDecimal = RgbToGray(c);
        String tvC_Data = "\nC线平均值RGB色码:" + c[0] + "," + c[1] + "," + c[2] + "\n" + "跟公式套用后结果：" + cBigDecimal;
//        tvC.setText(tvC_Data);

        Double cData1 = new Double(String.valueOf(cBigDecimal));
        Double cData = new Double(185.00);
        String result;
        if (cData1 >= cData) {
            result = "Invalid : "+"\n C 线数值:"+cBigDecimal+"\n T 线数值:"+tBigDecimal + "\n C 线大于185.00";
        } else {
            Double tData1 = new Double(String.valueOf(tBigDecimal));
            if (135 > tData1) {
                result = "Negative : " +"\n C 线数值:"+cBigDecimal+"\n T 线数值:"+tBigDecimal + "\n C 线数值小于185.00" + "\n T 线数值小于135.00" ;
            } else {
                result = "Positive : " +"\n C 线数值:"+cBigDecimal+"\n T 线数值:"+tBigDecimal + "\n C 线数值小于185.00" + "\n T 线数值大于135.00" ;
            }
        }

        String testName = mTvTestName.getText().toString().trim();
        String testSerial = mEtSerial.getText().toString().trim();
        String testNum = mEtNum.getText().toString().trim();
        ExamineDao examineDao = GreenDaoManager.getInstance().getNewSession().getExamineDao();
        Examine examine = new Examine(null,testName, Helper.date2String(new Date()),testSerial,testNum, CurrentUser.getInstance().getId(),CurrentUser.getInstance().getName(),result,imagePath,false);
        long id = examineDao.insert(examine);
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        NavigationHelper.startActivity(NewTestActivity.this,TestResultActivity.class,bundle,false);
    }

    private BigDecimal RgbToGray(int[] data) {
//        计算公式 gray=0.299red+0.587green+0.114blue
        BigDecimal redData = new BigDecimal("0.299").multiply(new BigDecimal(data[0]));
        BigDecimal greenData = new BigDecimal("0.587").multiply(new BigDecimal(data[1]));
        BigDecimal blueData = new BigDecimal("0.114").multiply(new BigDecimal(data[2]));
        return redData.add(greenData).add(blueData).setScale(2, RoundingMode.DOWN);
    }
}
