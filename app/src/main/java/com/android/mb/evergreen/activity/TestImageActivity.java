package com.android.mb.evergreen.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.CurrentUser;
import com.android.mb.evergreen.entity.Examine;
import com.android.mb.evergreen.greendao.ExamineDao;
import com.android.mb.evergreen.utils.DialogHelper;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.NavigationHelper;
import com.android.mb.evergreen.utils.ProjectHelper;
import com.android.mb.evergreen.utils.ToastHelper;

import java.util.Date;

public class TestImageActivity extends BaseActivity implements View.OnClickListener{

    private ImageView mZoomImageView;
    private int mTestStep = 0;
    private TextView mTvC,mTvT;
    private TextView mResult;
    private ImageView mIvC,mIvT;
    private String mTestName;
    private String mTestSerial;
    private String mTestNum;
    private String mImagePath;
    private Bitmap mBitmap;
    private int mTRGB;
    private int mCRGB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);
        initView();
        initOnClickListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(){
        mTestName = getIntent().getStringExtra("testName");
        mTestSerial = getIntent().getStringExtra("testSerial");
        mTestNum = getIntent().getStringExtra("testNum");
        mImagePath = getIntent().getStringExtra("imagePath");
        mTvC = findViewById(R.id.tv_c);
        mTvT = findViewById(R.id.tv_t);
        mIvC = findViewById(R.id.iv_c);
        mIvT = findViewById(R.id.iv_t);
        mResult = findViewById(R.id.tv_result);
        mZoomImageView = findViewById(R.id.zoomImage);
        mBitmap = BitmapFactory.decodeFile(mImagePath);
        if (mBitmap==null){
            finish();
        }else{
            mZoomImageView.setImageBitmap(mBitmap);
        }

        mZoomImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int evX = (int) event.getX();//get x coordinate
                final int evY = (int) event.getY();//get y coordinate
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mTestStep<2){
                        showDialog(evX,evY);
                    }
                }
                return true;
            }
        });
    }

    private void initOnClickListener() {
        findViewById(R.id.btn_reset).setOnClickListener(this);
        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                mTestStep = 0;
                mIvC.setBackgroundColor(getResources().getColor(R.color.transparent));
                mIvT.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case R.id.btn_test:
                doTest();
                break;
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void showDialog(final int x, final int y){
        String message = mTestStep==0?"确认选择C区？":"确认选择T区？";
        DialogHelper.showConfirmDialog(this, "提示", message, true, R.string.dialog_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setImageColor(x,y);
                mTestStep++;
            }
        }, R.string.dialog_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void setImageColor(int x,int y){
        int color = getColor(mZoomImageView,x,y);
        // 如果你想做的更细致的话 可以把颜色值的R G B 拿到做响应的处理
        if (mTestStep==0){
            mCRGB = color;
            mIvC.setBackgroundColor(color);
        }else{
            mTRGB =color;
            mIvT.setBackgroundColor(color);
        }
    }

    private int getColor(ImageView selectedImage, int evX, int evY) {
        selectedImage.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(selectedImage.getDrawingCache());
        selectedImage.setDrawingCacheEnabled(false);
        return bitmap.getPixel(evX, evY);//it will return selected pixal
    }

    private void doTest(){
        if (mTestStep==0){
            ToastHelper.showToast("请选择C区");
            return;
        }

        if (mTestStep==1){
            ToastHelper.showToast("请选择T区");
            return;
        }

        int cr = Color.red(mCRGB);
        int cg = Color.green(mCRGB);
        int cb = Color.blue(mCRGB);

        int tr = Color.red(mTRGB);
        int tg = Color.green(mTRGB);
        int tb = Color.blue(mTRGB);

        String result = "CRGB:"+ ProjectHelper.toHex(cr,cg,cb)+"  CR:"+cr+"  CG:"+cg+"  CB:"+cb
                +"\nTRGB:"+ProjectHelper.toHex(tr,tg,tb)+"  TR:"+tr+"  TG:"+tg+"  TB:"+tb;
        mResult.setText(result);
        ExamineDao examineDao = GreenDaoManager.getInstance().getNewSession().getExamineDao();
        Examine examine = new Examine(null,mTestName, Helper.date2String(new Date()),mTestSerial,mTestNum, CurrentUser.getInstance().getId(),CurrentUser.getInstance().getName(),result,mImagePath,false);
        long id = examineDao.insert(examine);
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        NavigationHelper.startActivity(TestImageActivity.this,TestResultActivity.class,bundle,false);
    }

}
