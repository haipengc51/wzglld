package com.jiekai.wzglld.entity;

import com.jiekai.wzglld.entity.base.BaseEntity;

/**
 * Created by LaoWu on 2018/3/12.
 */

public class UpdateEntity extends BaseEntity {
    private String LB;
    private int VERSION;
    private String INFO;
    private String FORCE;
    private String PATH;

    public String getLB() {
        return LB;
    }

    public void setLB(String LB) {
        this.LB = LB;
    }

    public int getVERSION() {
        return VERSION;
    }

    public void setVERSION(int VERSION) {
        this.VERSION = VERSION;
    }

    public String getINFO() {
        return INFO;
    }

    public void setINFO(String INFO) {
        this.INFO = INFO;
    }

    public String getFORCE() {
        return FORCE;
    }

    public void setFORCE(String FORCE) {
        this.FORCE = FORCE;
    }

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }
}
