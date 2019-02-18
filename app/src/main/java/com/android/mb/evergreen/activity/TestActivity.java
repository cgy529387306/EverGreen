package com.android.mb.evergreen.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.camera.CameraColorPickerPreview;
import com.android.mb.evergreen.camera.Cameras;
import com.android.mb.evergreen.utils.ImageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity implements CameraColorPickerPreview.OnColorSelectedListener {

    @BindView(R.id.tv_data)
    public TextView tvData;
    @BindView(R.id.pointer_ring)
    public View pointerRing;
    @BindView(R.id.color_pointer)
    View colorPointer;
    @BindView(R.id.color_left1)
    public View colorLeft1;
    @BindView(R.id.color_left2)
    public View colorLeft2;
    @BindView(R.id.color_left3)
    public View colorLeft3;
    @BindView(R.id.color_left4)
    public View colorLeft4;
    @BindView(R.id.color_left5)
    public View colorLeft5;
    @BindView(R.id.color_left6)
    public View colorLeft6;
    @BindView(R.id.color_left7)
    public  View colorLeft7;
    @BindView(R.id.color_left8)
    public  View colorLeft8;
    @BindView(R.id.color_left9)
    public View colorLeft9;
    @BindView(R.id.color_left10)
    public View colorLeft10;
    @BindView(R.id.camera_container)
    public FrameLayout cameraContainer;
    @BindView(R.id.color_right1)
    public View colorRight1;
    @BindView(R.id.color_right2)
    public  View colorRight2;
    @BindView(R.id.color_right3)
    public View colorRight3;
    @BindView(R.id.color_right4)
    public View colorRight4;
    @BindView(R.id.color_right5)
    public View colorRight5;
    @BindView(R.id.color_right6)
    public View colorRight6;
    @BindView(R.id.color_right7)
    public View colorRight7;
    @BindView(R.id.color_right8)
    public  View colorRight8;
    @BindView(R.id.color_right9)
    public View colorRight9;
    @BindView(R.id.color_right10)
    public View colorRight10;
    @BindView(R.id.color_right11)
    public View colorRight11;
    @BindView(R.id.color_right12)
    public  View colorRight12;
    @BindView(R.id.color_right13)
    public View colorRight13;
    @BindView(R.id.color_right14)
    public View colorRight14;
    @BindView(R.id.color_right15)
    public View colorRight15;
    @BindView(R.id.color_right16)
    public View colorRight16;
    @BindView(R.id.color_right17)
    public  View colorRight17;
    @BindView(R.id.color_right18)
    public View colorRight18;
    @BindView(R.id.color_right19)
    public View colorRight19;
    @BindView(R.id.color_right20)
    public View colorRight20;
    @BindView(R.id.color_right21)
    public View colorRight21;
    @BindView(R.id.color_right22)
    public View colorRight22;
    @BindView(R.id.color_right23)
    public View colorRight23;
    @BindView(R.id.color_right24)
    public  View colorRight24;
    @BindView(R.id.color_right25)
    public View colorRight25;
    @BindView(R.id.color_right26)
    public View colorRight26;
    @BindView(R.id.color_right27)
    public View colorRight27;
    @BindView(R.id.color_right28)
    public View colorRight28;
    @BindView(R.id.color_right29)
    public View colorRight29;
    @BindView(R.id.color_right30)
    public View colorRight30;
    @BindView(R.id.color_left11)
    public View colorLeft11;
    @BindView(R.id.color_left12)
    public View colorLeft12;
    @BindView(R.id.color_left13)
    public View colorLeft13;
    @BindView(R.id.color_left14)
    public View colorLeft14;
    @BindView(R.id.color_left15)
    public View colorLeft15;
    @BindView(R.id.color_left16)
    public View colorLeft16;
    @BindView(R.id.color_left17)
    public View colorLeft17;
    @BindView(R.id.color_left18)
    public View colorLeft18;
    @BindView(R.id.color_left19)
    public View colorLeft19;
    @BindView(R.id.color_left20)
    public View colorLeft20;
    @BindView(R.id.color_left21)
    public View colorLeft21;
    @BindView(R.id.color_left22)
    public View colorLeft22;
    @BindView(R.id.color_left23)
    public View colorLeft23;
    @BindView(R.id.color_left24)
    public View colorLeft24;
    @BindView(R.id.color_left25)
    public View colorLeft25;
    @BindView(R.id.color_left26)
    public View colorLeft26;
    @BindView(R.id.color_left27)
    public View colorLeft27;
    @BindView(R.id.color_left28)
    public View colorLeft28;
    @BindView(R.id.color_left29)
    public View colorLeft29;
    @BindView(R.id.color_left30)
    public View colorLeft30;
    private Camera mCamera;
    private CameraAsyncTask mCameraAsyncTask;
    private int mSelectedColor;
    private CameraColorPickerPreview mCameraPreview;
    private FrameLayout mPreviewContainer;
    private static final int REQUEST_CAMERA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        CameraColorPickerPreview.isBtnSan = false;
        hideNavigationBar();
        mPreviewContainer = findViewById(R.id.camera_container);
    }

    private void hideNavigationBar() {
//        全屏
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }


    @Override
    public void onColorSelected(int color) {
        mSelectedColor = color;
        pointerRing.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            //闪光灯
            final Camera.Parameters parameters = c.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            c.setParameters(parameters);
            c.autoFocus(new Camera.AutoFocusCallback() {
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success){
                        camera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。
                        if (!Build.MODEL.equals("KORIDY H30")) {
                            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续自动对焦
                            camera.setParameters(parameters);
                        } else {
                            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                            camera.setParameters(parameters);
                        }
                     }
                }
            });
            c.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(TestActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TestActivity.this,
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
            } else {
                mCameraAsyncTask = new CameraAsyncTask();
                mCameraAsyncTask.execute();
            }
        } else {
            mCameraAsyncTask = new CameraAsyncTask();
            mCameraAsyncTask.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//同意权限
//                mCameraAsyncTask = new CameraAsyncTask();
//                mCameraAsyncTask.execute();   //会和onResume重复调用
            } else {
                //不同意权限那就去死吧
                TestActivity.this.finish();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCameraAsyncTask != null) {
            mCameraAsyncTask.cancel(true);
        }
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
        if (mCameraPreview != null) {
            mPreviewContainer.removeView(mCameraPreview);
        }
    }

    private class CameraAsyncTask extends AsyncTask<Void, Void, Camera> {

        protected FrameLayout.LayoutParams mPreviewParams;

        @Override
        protected Camera doInBackground(Void... params) {
            Camera camera = getCameraInstance();
            int w = mPreviewContainer.getWidth();
            int h = mPreviewContainer.getHeight();
            if (camera != null) {
                Camera.Parameters cameraParameters = camera.getParameters();
                Camera.Size bestSize = Cameras.getBestPreviewSize(
                        cameraParameters.getSupportedPreviewSizes()
                        , mPreviewContainer.getWidth()
                        , mPreviewContainer.getHeight()
                        , true);
                cameraParameters.setPreviewSize(bestSize.width, bestSize.height);
                camera.setParameters(cameraParameters);
                Cameras.setCameraDisplayOrientation(TestActivity.this, camera);
                int[] adaptedDimension = Cameras.getProportionalDimension(
                        bestSize
                        , mPreviewContainer.getWidth()
                        , mPreviewContainer.getHeight()
                        , true);
                mPreviewParams = new FrameLayout.LayoutParams(adaptedDimension[0], adaptedDimension[1]);
                mPreviewParams.gravity = Gravity.CENTER;
            }
            return camera;
        }

        @Override
        protected void onPostExecute(Camera camera) {
            super.onPostExecute(camera);
            if (!isCancelled()) {
                mCamera = camera;
                if (mCamera != null) {
                    mCameraPreview = new CameraColorPickerPreview(TestActivity.this, mCamera);
                    mCameraPreview.setOnColorSelectedListener(TestActivity.this);
                    mPreviewContainer.addView(mCameraPreview, 0, mPreviewParams);
                }
            }
        }

        @Override
        protected void onCancelled(Camera camera) {
            super.onCancelled(camera);
            if (camera != null) {
                camera.release();
            }
        }
    }


    public void btn_san(View view) {
        if (CameraColorPickerPreview.colorList.size() == 30 && mCamera!=null) {
            mCamera.takePicture(new Camera.ShutterCallback() {
                @Override
                public void onShutter() {

                }
            }, null, mPictureCallback);
        }
    }

    private final Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            SaveThread st = new SaveThread(data);
            st.start();
        }
    };

    class SaveThread extends Thread {
        private byte[] mData;

        public SaveThread(byte[] data) {
            this.mData = data;
        }

        @Override
        public void run() {
            FileOutputStream b = null;
            try {
                // 保存到自定义路径
                if (!new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + File.separator + "EverGreen").exists()) {
                    new File(Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + File.separator + "EverGreen")
                            .mkdirs();
                }
                String imagePath = Environment.getExternalStorageDirectory()
                        .getAbsolutePath()
                        + File.separator
                        + "EverGreen"
                        + File.separator + new Date().getTime() + ".png";
                b = new FileOutputStream(imagePath);
                b.write(mData);
                b.flush();
                b.close();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    Intent mediaScanIntent = new Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(new File(imagePath)); //out is your output file
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);
                } else {
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
                            + Environment.getExternalStorageDirectory())));
                }
                Bitmap mSourceBitmap = ImageUtil.getLocalOrNetBitmap(imagePath);
                if(mSourceBitmap != null) {
                    Bitmap bitmapTrans = ImageUtil.rotaingImageView(mSourceBitmap, 90);
                    if(bitmapTrans != null) {
                        ImageUtil.saveImg(bitmapTrans, imagePath);
                    }
                }
                Intent i = new Intent();
                i.putExtra("imagePath", imagePath);
                setResult(RESULT_OK, i);
                finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
