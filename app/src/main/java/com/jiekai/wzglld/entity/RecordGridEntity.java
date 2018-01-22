package com.jiekai.wzglld.entity;

import com.jiekai.wzglld.entity.base.BaseEntity;

/**
 * Created by laowu on 2017/12/7.
 */

public class RecordGridEntity extends BaseEntity {
    private String name;
    private Class activity;
    private int icon;

    public RecordGridEntity(String name, Class activity, int icon) {
        this.name = name;
        this.activity = activity;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
