package com.android.mb.evergreen.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class Category {
    @Id
    private long id;

    private String name;

    @Generated(hash = 2035570615)
    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1150634039)
    public Category() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
