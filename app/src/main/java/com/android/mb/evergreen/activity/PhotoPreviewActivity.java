package com.android.mb.evergreen.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.utils.AppHelper;
import com.android.mb.evergreen.utils.BitmapUtils;

import java.io.File;

/**
 * 预览图片界面
 *
 * @author Clock
 * @since 2016-05-13
 */
public class PhotoPreviewActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String EXTRA_PHOTO = "extra_photo";
    private ImageView mIvPreview;
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        initView();
    }

    private void initView(){
        mIvPreview = (ImageView) findViewById(R.id.iv_preview_photo);
        mFilePath = (String) getIntent().getSerializableExtra(EXTRA_PHOTO);
        Bitmap bitmap = BitmapFactory.decodeFile(mFilePath);
        mIvPreview.setImageBitmap(bitmap);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    public static void preview(Activity activity, String file) {
        Intent previewIntent = new Intent(activity, PhotoPreviewActivity.class);
        previewIntent.putExtra(EXTRA_PHOTO, file);
        activity.startActivity(previewIntent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            finish();
        }
    }
}
