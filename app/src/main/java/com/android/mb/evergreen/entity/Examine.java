package com.android.mb.evergreen.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\8\8 0008.
 */
@Entity
public class Examine{
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String testDate;
    private String serial;
    private String num;
    private Long userId;
    private String userName;
    private String result;
    private String image;
    private boolean checked;

    
    @Generated(hash = 1999954313)
    public Examine(Long id, String name, String testDate, String serial, String num,
            Long userId, String userName, String result, String image,
            boolean checked) {
        this.id = id;
        this.name = name;
        this.testDate = testDate;
        this.serial = serial;
        this.num = num;
        this.userId = userId;
        this.userName = userName;
        this.result = result;
        this.image = image;
        this.checked = checked;
    }
    @Generated(hash = 1548243293)
    public Examine() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTestDate() {
        return this.testDate;
    }
    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getResult() {
        return this.result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getSerial() {
        return this.serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }
    public String getNum() {
        return this.num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public boolean getChecked() {
        return this.checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }



}
