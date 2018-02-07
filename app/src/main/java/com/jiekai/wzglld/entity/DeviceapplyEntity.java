package com.jiekai.wzglld.entity;

import com.jiekai.wzglld.entity.base.BaseEntity;

import java.sql.Timestamp;

/**
 * Created by laowu on 2017/12/18.
 */

public class DeviceapplyEntity extends BaseEntity {
    private int SQID;
    private String SYDH;
    private String SYJH;
    private String SQR;
    private Timestamp SQSJ;
    private Timestamp SHSJ;
    private String SHR;
    private String SQBZ;
    private String SHBZ;
    private String SHYJ;
    private String czrname;

    public int getSQID() {
        return SQID;
    }

    public void setSQID(int SQID) {
        this.SQID = SQID;
    }

    public String getSYDH() {
        return SYDH;
    }

    public void setSYDH(String SYDH) {
        this.SYDH = SYDH;
    }

    public String getSYJH() {
        return SYJH;
    }

    public void setSYJH(String SYJH) {
        this.SYJH = SYJH;
    }

    public String getSQR() {
        return SQR;
    }

    public void setSQR(String SQR) {
        this.SQR = SQR;
    }

    public Timestamp getSQSJ() {
        return SQSJ;
    }

    public void setSQSJ(Timestamp SQSJ) {
        this.SQSJ = SQSJ;
    }

    public Timestamp getSHSJ() {
        return SHSJ;
    }

    public void setSHSJ(Timestamp SHSJ) {
        this.SHSJ = SHSJ;
    }

    public String getSHR() {
        return SHR;
    }

    public void setSHR(String SHR) {
        this.SHR = SHR;
    }

    public String getSQBZ() {
        return SQBZ;
    }

    public void setSQBZ(String SQBZ) {
        this.SQBZ = SQBZ;
    }

    public String getSHBZ() {
        return SHBZ;
    }

    public void setSHBZ(String SHBZ) {
        this.SHBZ = SHBZ;
    }

    public String getSHYJ() {
        return SHYJ;
    }

    public void setSHYJ(String SHYJ) {
        this.SHYJ = SHYJ;
    }

    public String getCzrname() {
        return czrname;
    }

    public void setCzrname(String czrname) {
        this.czrname = czrname;
    }
}
