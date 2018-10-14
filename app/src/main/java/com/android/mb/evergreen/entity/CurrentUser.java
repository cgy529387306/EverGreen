package com.android.mb.evergreen.entity;

import android.util.Log;

import com.android.mb.evergreen.utils.JsonHelper;
import com.android.mb.evergreen.utils.PreferencesHelper;


/**
 * 作者：cgy on 16/12/26 22:53
 * 邮箱：593960111@qq.com
 */
public class CurrentUser {
    private String account;
    private String name;
    private String org;
    private Long id;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    //region 单例
    private static final String TAG = CurrentUser.class.getSimpleName();
    private static final String USER = "CurrentUser";
    private static CurrentUser me;
    /**
     * 单例
     * @return 当前用户对象
     */
    public static CurrentUser getInstance() {
        if (me == null) {
            me = new CurrentUser();
        }
        return me;
    }
    /**
     * 出生
     * <p>尼玛！终于出生了！！！</p>
     * <p>调用此方法查询是否登录过</p>
     * @return 出生与否
     */
    public boolean isLogin() {
        String json = PreferencesHelper.getInstance().getString(USER);
        me = JsonHelper.fromJson(json, CurrentUser.class);
        return me != null;
    }

    public boolean login(User entity) {
        boolean born = false;
        String json = "";
        if (entity != null) {
            me.setId(entity.getId());
            me.setAccount(entity.getAccount());
            me.setName(entity.getName());
            me.setOrg(entity.getOrg());
            json = JsonHelper.toJson(me);
            born = me != null;
        }
        // 生完了
        if (!born) {
            Log.e(TAG, "尼玛，流产了！！！");
        } else {
            PreferencesHelper.getInstance().putString(USER,json);
        }
        return born;
    }

    // endregion 单例

    /**
     * 退出登录清理用户信息
     */
    public void loginOut() {
        me = null;
        PreferencesHelper.getInstance().putString(USER, "");
    }
}
