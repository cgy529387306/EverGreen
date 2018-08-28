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

    @Generated(hash = 1721324)
    public Category(Long id, String name, String insertDate) {
        this.id = id;
        this.name = name;
        this.insertDate = insertDate;
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



}
