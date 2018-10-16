package com.android.mb.evergreen.entity;

/**
 * Created by cgy on 18/10/13.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018\8\8 0008.
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    private String account;
    private String password;
    private String org;
    private String name;
    private boolean isAdmin;
    @Generated(hash = 1997057306)
    public User(Long id, String account, String password, String org, String name,
            boolean isAdmin) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.org = org;
        this.name = name;
        this.isAdmin = isAdmin;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getOrg() {
        return this.org;
    }
    public void setOrg(String org) {
        this.org = org;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean getIsAdmin() {
        return this.isAdmin;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


}
