package com.jiekai.wzglld.entity;

import com.jiekai.wzglld.entity.base.BaseEntity;

import java.sql.Timestamp;

/**
 * Created by LaoWu on 2018/1/15.
 */

public class DeviceinspectionEntity extends BaseEntity {
    private int ID;
    private String SBBH;
    private Timestamp CZSJ;
    private String CZR;
    private String BZ;
    private String SHYJ;
    private Timestamp SHSJ;
    private String SHR;
    private String SHBZ;
    private String czrname;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSBBH() {
        return SBBH;
    }

    public void setSBBH(String SBBH) {
        this.SBBH = SBBH;
    }

    public Timestamp getCZSJ() {
        return CZSJ;
    }

    public void setCZSJ(Timestamp CZSJ) {
        this.CZSJ = CZSJ;
    }

    public String getCZR() {
        return CZR;
    }

    public void setCZR(String CZR) {
        this.CZR = CZR;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getSHYJ() {
        return SHYJ;
    }

    public void setSHYJ(String SHYJ) {
        this.SHYJ = SHYJ;
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

    public String getSHBZ() {
        return SHBZ;
    }

    public void setSHBZ(String SHBZ) {
        this.SHBZ = SHBZ;
    }

    public String getCzrname() {
        return czrname;
    }

    public void setCzrname(String czrname) {
        this.czrname = czrname;
    }
}
