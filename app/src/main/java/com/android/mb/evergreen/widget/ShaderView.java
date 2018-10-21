package com.android.mb.evergreen.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.mb.evergreen.R;

public class ShaderView extends View {
    private  Bitmap bitmap;
    private ShapeDrawable drawable;
    // 放大镜的半径
    private static final int RADIUS = 100;
    // 放大倍数
    private static final int FACTOR = 4;
    private final Matrix matrix = new Matrix();

    public ShaderView(Context context) {
        super(context);
        init();
    }


    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_home_top);
        bitmap = bmp;
        BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(bmp,
                bmp.getWidth() * FACTOR, bmp.getHeight() * FACTOR, true), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // 圆形的drawable
        drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setShader(shader);
        drawable.setBounds(0, 0, RADIUS * 2, RADIUS * 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        // 这个位置表示的是，画shader的起始位置
        matrix.setTranslate(RADIUS - x * FACTOR, RADIUS - y * FACTOR);
        drawable.getPaint().getShader().setLocalMatrix(matrix);

        // bounds，就是那个圆的外切矩形
        drawable.setBounds(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS);
        invalidate();
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
        drawable.draw(canvas);
    }
}