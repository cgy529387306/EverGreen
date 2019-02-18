package com.android.mb.evergreen.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.name;

/**
 * 图片工具类 &lt;br&gt;Created 2015年7月2日 15:32:22
 *
 * @author wangds
 * @see
 */
public class ImageUtil {

    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = (float)w / (float)width;
        float scaleHeight = (float)h / (float)height;
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @return角度 获取从相册中选中图片的角度.
     */
    public static float readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片，使图片保持正确的方向.
     */
    public static Bitmap rotateBitmapNotRecycle(Bitmap bitmap, int degree) {
        if(degree == 0) {
            return bitmap;
        } else {
            Matrix matrix = new Matrix();
            matrix.postRotate((float)degree);
            Bitmap tempBmp = bitmap;
            try {
                tempBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            } catch (OutOfMemoryError var5) {
                var5.printStackTrace();
            }

            return tempBmp;
        }
    }


    /**
     * 旋转图片，使图片保持正确的方向.
     */
    public static Bitmap rotaingImageView(Bitmap bitmap, int degree){
        if(degree == 0) {
            return bitmap;
        }

        // 根据旋转角度，生成旋转矩阵
        Bitmap returnBm = null;

        Matrix matrix = new Matrix();
       // matrix.setRotate(degree, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        matrix.postRotate(degree);

        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }

    /**
     * @param bitmap  把旋转图片弄为正常后的对象
     * @param path   要保存的图片路径
     * @return
     */
    public static String saveBitmapFile(Bitmap bitmap, String path) {
        Log.e("ImageUtil", "saveImg name := " + name);
        File file = new File(path);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    public static boolean saveImg(Bitmap bitmap, String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.mkdirs();
            }

            final String name = file.getName();
            Log.e("ImageUtil", "saveImg name := " + name);
            int dot = name.lastIndexOf('.');
            String fileExtension = "";//文件扩展名
            if (dot != -1 && dot < name.length() - 1) {
                fileExtension = name.substring(dot).toLowerCase();
            }

            FileOutputStream outputStream = new FileOutputStream(file);     //构建输出流
          //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);  //compress到输出outputStream
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            if (".png".equals(fileExtension)) {
                Log.e("ImageUtil", "saveImg PNG");
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            } else {
                Log.e("ImageUtil", "saveImg JPEG");
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            }

            bos.flush();
            bos.close();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 通过图片文件,将其转为Bitmap对象
     * @param fileName
     * @return
    */
    public static Bitmap getLocalOrNetBitmap(String fileName) {
        Bitmap bitmap = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static final int IMAGE_MAX_WIDTH = 600;
    private static final int IMAGE_MAX_HEIGHT = 400;

    /**
     * 图片缩放处理
     *
     * @param imageFile 照片文件
     * @return 缩放的倍数
     */
    public static int getZoomScale(String strImgPath, File imageFile) {
        int scale = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(strImgPath, options);
        while (options.outWidth / scale >= IMAGE_MAX_WIDTH
                || options.outHeight / scale >= IMAGE_MAX_HEIGHT) {
            scale *= 2;
        }
        return scale;
    }



}
