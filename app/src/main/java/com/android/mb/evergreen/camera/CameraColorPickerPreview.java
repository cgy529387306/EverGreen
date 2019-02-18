package com.android.mb.evergreen.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.TextureView;
import android.view.View;

import com.android.mb.evergreen.activity.TestActivity;

import java.util.ArrayList;
import java.util.List;


public class CameraColorPickerPreview extends TextureView implements TextureView.SurfaceTextureListener,
        Camera.PreviewCallback {

    protected static final int POINTER_RADIUS = 5;
    protected Camera mCamera;
    protected Camera.Size mPreviewSize;
    protected int[] mSelectedColor;
    protected OnColorSelectedListener mOnColorSelectedListener;

    TestActivity activity;

    public CameraColorPickerPreview(TestActivity context, Camera camera) {
        super(context);
        activity = context;
        mCamera = camera;
        mCamera.getParameters().getPreviewFormat();
        this.setSurfaceTextureListener(this);
        mPreviewSize = mCamera.getParameters().getPreviewSize();
        mSelectedColor = new int[3];
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            mCamera.setPreviewTexture(surface);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    public static List<int[]> colorList = new ArrayList<>();
    public static List<int[]> color2List = new ArrayList<>();

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (CameraColorPickerPreview.isBtnSan) {
            return;
        }

        if (mOnColorSelectedListener != null) {

            //1、通过WindowManager获取
            DisplayMetrics dm = new DisplayMetrics();

            //4、通过类直接取
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);


            //设置左边的RGB
            colorList.clear();
            int[] view1 = toRGB(data, activity.colorLeft1);
            int[] view2 = toRGB(data, activity.colorLeft2);
            int[] view3 = toRGB(data, activity.colorLeft3);
            int[] view4 = toRGB(data, activity.colorLeft4);
            int[] view5 = toRGB(data, activity.colorLeft5);
            int[] view6 = toRGB(data, activity.colorLeft6);
            int[] view7 = toRGB(data, activity.colorLeft7);
            int[] view8 = toRGB(data, activity.colorLeft8);
            int[] view9 = toRGB(data, activity.colorLeft9);
            int[] view10 = toRGB(data, activity.colorLeft10);
            int[] view11 = toRGB(data, activity.colorLeft11);
            int[] view12 = toRGB(data, activity.colorLeft12);
            int[] view13 = toRGB(data, activity.colorLeft13);
            int[] view14 = toRGB(data, activity.colorLeft14);
            int[] view15 = toRGB(data, activity.colorLeft15);
            int[] view16 = toRGB(data, activity.colorLeft16);
            int[] view17 = toRGB(data, activity.colorLeft17);
            int[] view18 = toRGB(data, activity.colorLeft18);
            int[] view19 = toRGB(data, activity.colorLeft19);
            int[] view20 = toRGB(data, activity.colorLeft20);
            int[] view21 = toRGB(data, activity.colorLeft21);
            int[] view22 = toRGB(data, activity.colorLeft22);
            int[] view23 = toRGB(data, activity.colorLeft23);
            int[] view24 = toRGB(data, activity.colorLeft24);
            int[] view25 = toRGB(data, activity.colorLeft25);
            int[] view26 = toRGB(data, activity.colorLeft26);
            int[] view27 = toRGB(data, activity.colorLeft27);
            int[] view28 = toRGB(data, activity.colorLeft28);
            int[] view29 = toRGB(data, activity.colorLeft29);
            int[] view30 = toRGB(data, activity.colorLeft30);


            //设左边右边的RGB
            color2List.clear();
            int[] viewRight1 = toRGB2(data, activity.colorRight1);
            int[] viewRight2 = toRGB2(data, activity.colorRight2);
            int[] viewRight3 = toRGB2(data, activity.colorRight3);
            int[] viewRight4 = toRGB2(data, activity.colorRight4);
            int[] viewRight5 = toRGB2(data, activity.colorRight5);
            int[] viewRight6 = toRGB2(data, activity.colorRight6);
            int[] viewRight7 = toRGB2(data, activity.colorRight7);
            int[] viewRight8 = toRGB2(data, activity.colorRight8);
            int[] viewRight9 = toRGB2(data, activity.colorRight9);
            int[] viewRight10 = toRGB2(data, activity.colorRight10);
            int[] viewRight11 = toRGB2(data, activity.colorRight11);
            int[] viewRight12 = toRGB2(data, activity.colorRight12);
            int[] viewRight13 = toRGB2(data, activity.colorRight13);
            int[] viewRight14 = toRGB2(data, activity.colorRight14);
            int[] viewRight15 = toRGB2(data, activity.colorRight15);
            int[] viewRight16 = toRGB2(data, activity.colorRight16);
            int[] viewRight17 = toRGB2(data, activity.colorRight17);
            int[] viewRight18 = toRGB2(data, activity.colorRight18);
            int[] viewRight19 = toRGB2(data, activity.colorRight19);
            int[] viewRight20 = toRGB2(data, activity.colorRight20);
            int[] viewRight21 = toRGB2(data, activity.colorRight21);
            int[] viewRight22 = toRGB2(data, activity.colorRight22);
            int[] viewRight23 = toRGB2(data, activity.colorRight23);
            int[] viewRight24 = toRGB2(data, activity.colorRight24);
            int[] viewRight25 = toRGB2(data, activity.colorRight25);
            int[] viewRight26 = toRGB2(data, activity.colorRight26);
            int[] viewRight27 = toRGB2(data, activity.colorRight27);
            int[] viewRight28 = toRGB2(data, activity.colorRight28);
            int[] viewRight29 = toRGB2(data, activity.colorRight29);
            int[] viewRight30 = toRGB2(data, activity.colorRight30);
        }

    }

    public static int[] colorEnd(List<int[]> colorList) {
        int[] colorEnd = new int[3];

        for (int i = 0; i < colorList.size(); i++) {
            colorEnd[0] += colorList.get(i)[0];
        }
        colorEnd[0] = colorEnd[0] / colorList.size();

        for (int i = 0; i < colorList.size(); i++) {
            colorEnd[1] += colorList.get(i)[1];
        }
        colorEnd[1] = colorEnd[1] / colorList.size();

        for (int i = 0; i < colorList.size(); i++) {
            colorEnd[2] += colorList.get(i)[2];
        }
        colorEnd[2] = colorEnd[2] / colorList.size();


        return colorEnd;
    }


    //获取右边的RGB
    private int[] toRGB(byte[] data, View view) {
        //获取在整个屏幕内的绝对坐标
        int[] location = new int[2];
        view.getLocationOnScreen(location);//一个控件在其整个屏幕上的坐标位置

        int midX1 = (12 + location[1]) * mPreviewSize.width / 2160;
        int midY1 = (12 + location[0]) * mPreviewSize.height / 1080;
        midY1 = mPreviewSize.height - midY1;
        //获取RGB值
        int[] mSelectedColor = new int[3];
        mSelectedColor[0] = 0;
        mSelectedColor[1] = 0;
        mSelectedColor[2] = 0;
        for (int i = 0; i <= POINTER_RADIUS; i++) {
            for (int j = 0; j <= POINTER_RADIUS; j++) {
                addColorFromYUV420(data, mSelectedColor, (i * POINTER_RADIUS + j + 1), (midX1 - POINTER_RADIUS) + i, (midY1 - POINTER_RADIUS) + j, mPreviewSize.width, mPreviewSize.height);
            }
        }
        colorList.add(mSelectedColor);
        return mSelectedColor;
    }

    //获取左边的RGB
    private int[] toRGB2(byte[] data, View view) {
        //获取在整个屏幕内的绝对坐标
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int midX1 = (12 + location[1]) * mPreviewSize.width / 2160;
        int midY1 = (12 + location[0]) * mPreviewSize.height / 1080;
        midY1 = mPreviewSize.height - midY1;
        //获取RGB值
        int[] mSelectedColor = new int[3];
        mSelectedColor[0] = 0;
        mSelectedColor[1] = 0;
        mSelectedColor[2] = 0;
        for (int i = 0; i <= POINTER_RADIUS; i++) {
            for (int j = 0; j <= POINTER_RADIUS; j++) {
                addColorFromYUV420(data, mSelectedColor, (i * POINTER_RADIUS + j + 1),
                        (midX1 - POINTER_RADIUS) + i, (midY1 - POINTER_RADIUS) + j,
                        mPreviewSize.width, mPreviewSize.height);
            }
        }
//        mOnColorSelectedListener.onColorSelected(Color.rgb(mSelectedColor[0], mSelectedColor[1], mSelectedColor[2]));
        color2List.add(mSelectedColor);
        return mSelectedColor;
    }

    protected void addColorFromYUV420(byte[] data, int[] averageColor, int count, int x, int y, int width, int height) {
        final int size = width * height;
        int tt = y * width + x;
        final int Y = data[tt] & 0xff;
        final int xby2 = x / 2;
        final int yby2 = y / 2;

        int Vlengh = size + 2 * xby2 + yby2 * width;

        final float V = (float) (data[Vlengh] & 0xff) - 128.0f;
        final float U = (float) (data[size + 2 * xby2 + 1 + yby2 * width] & 0xff) - 128.0f;

        float Yf = 1.164f * ((float) Y) - 16.0f;
        int red = (int) (Yf + 1.596f * V);
        int green = (int) (Yf - 0.813f * V - 0.391f * U);
        int blue = (int) (Yf + 2.018f * U);

        red = red < 0 ? 0 : red > 255 ? 255 : red;
        green = green < 0 ? 0 : green > 255 ? 255 : green;
        blue = blue < 0 ? 0 : blue > 255 ? 255 : blue;

        averageColor[0] += (red - averageColor[0]) / count;
        averageColor[1] += (green - averageColor[1]) / count;
        averageColor[2] += (blue - averageColor[2]) / count;
    }

    public static boolean isBtnSan = false;

    public void setOnColorSelectedListener(OnColorSelectedListener onColorSelectedListener) {
        mOnColorSelectedListener = onColorSelectedListener;
    }

    public interface OnColorSelectedListener {
        void onColorSelected(int newColor);
    }


}