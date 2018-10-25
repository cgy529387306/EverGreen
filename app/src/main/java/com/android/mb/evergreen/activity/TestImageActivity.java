package com.android.mb.evergreen.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.utils.DialogHelper;
import com.android.mb.evergreen.widget.ZoomImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TestImageActivity extends BaseActivity implements View.OnClickListener{

    private ImageView mZoomImageView;
    private int mTestStep = 0;
    private TextView mTvC,mTvT;
    private String mTestName;
    private String mTestSerial;
    private String mTestNum;
    private String mImagePath;
    private Bitmap mBitmap;
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
        mZoomImageView = findViewById(R.id.zoomImage);
        mZoomImageView.setImageResource(R.mipmap.bg_home_top);
        mBitmap = BitmapFactory.decodeFile(mImagePath);
        comp(mBitmap);
        if (mBitmap==null){
            finish();
        }else{
            mZoomImageView.setImageBitmap(mBitmap);
        }

        mZoomImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mTestStep<2){
                    showDialog(event);
                }
                return false;
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
                mTvC.setText(String.format("C:%s", "请选择C区"));
                mTvT.setText(String.format("T:%s", "请选择T区"));
                break;
            case R.id.btn_test:

                break;
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void showDialog(final MotionEvent event){
        String message = mTestStep==0?"确认选择C区？":"确认选择T区？";
        DialogHelper.showConfirmDialog(this, "提示", message, true, R.string.dialog_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setImageColor(event);
                mTestStep++;
            }
        }, R.string.dialog_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void setImageColor(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();
        int color = mBitmap.getPixel(x, y);
        // 如果你想做的更细致的话 可以把颜色值的R G B 拿到做响应的处理
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int a = Color.alpha(color);
        String message = "r=" + r + ",g=" + g + ",b=" + b;
        Log.i("cgy", message);
        if (mTestStep==0){
            mTvC.setText(String.format("C:%s", message));
            mTvC.setTextColor(Color.argb(a,r, g, b));
        }else{
            mTvT.setText(String.format("T:%s", message));
            mTvT.setTextColor(Color.argb(a,r, g, b));
        }
    }

    private Bitmap comp(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {
            //判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            //这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*500分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 500f;//这里设置宽度为500f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
