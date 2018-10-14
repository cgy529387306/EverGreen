package com.android.mb.evergreen.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class Category {
    @Id(autoincrement = true)
    private Long id;

    private String name;

    private String insertDate;

    private Long userId;

    private boolean isChecked;

    @Generated(hash = 772723380)
    public Category(Long id, String name, String insertDate, Long userId,
            boolean isChecked) {
        this.id = id;
        this.name = name;
        this.insertDate = insertDate;
        this.userId = userId;
        this.isChecked = isChecked;
    }

    @Generated(hash = 1150634039)
    public Category() {
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

    public String getInsertDate() {
        return this.insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
