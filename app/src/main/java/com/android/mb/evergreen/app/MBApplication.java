package com.android.mb.evergreen.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.mb.evergreen.entity.CurrentUser;
import com.android.mb.evergreen.entity.User;
import com.android.mb.evergreen.utils.Helper;
import com.facebook.stetho.Stetho;


/**
 * 主要用来获取全局的Context
 * @author cgy
 *
 */
public class MBApplication extends Application {

	private static final String TAG = MBApplication.class.getSimpleName();
	
	private static Context sInstance;

	/**
	 * 获取全局Application对象
	 * @since 2013.08.02 修改错误提示内容为Application by pcqpcq
	 * @return
	 */
	public static Context getInstance(){
		if (Helper.isNull(sInstance)) {
			Log.e(TAG, "THE APPLICATION OF YOUR PROJECT MUST BE 'MBApplication', OR SOMEONE EXTEND FROM IT");
		}
		return sInstance;
	}

    /**
     * 初始化
     * <p>若未配置manifest可使用此方法初始化</p>
     * @param application 全局context
     */
    public static void init(Context application) {
        sInstance = application;
    }

    @Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		Stetho.initializeWithDefaults(this);
	}

}
