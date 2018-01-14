package com.jiekai.wzglld.entity;

import com.jiekai.wzglld.entity.base.BaseEntity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by LaoWu on 2018/1/5.
 * 现场上传记录的实体类
 */

public class DevicelogEntity extends BaseEntity {
    private int ID = -1;
    private String JLZLMC;
    private String SBBH;
    private String DH;
    private String JH;
    private Timestamp JLSJ;
    private String CZR;
    private String BZ;
    private String SHYJ;
    private String SHR;
    private Date SHSJ;
    private String SHBZ;
    private String czrname;
    private String shrname;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getJLZLMC() {
        return JLZLMC;
    }

    public void setJLZLMC(String JLZLMC) {
        this.JLZLMC = JLZLMC;
    }

    public String getSBBH() {
        return SBBH;
    }

    public void setSBBH(String SBBH) {
        this.SBBH = SBBH;
    }

    public String getDH() {
        return DH;
    }

    public void setDH(String DH) {
        this.DH = DH;
    }

    public String getJH() {
        return JH;
    }

    public void setJH(String JH) {
        this.JH = JH;
    }

    public Timestamp getJLSJ() {
        return JLSJ;
    }

    public void setJLSJ(Timestamp JLSJ) {
        this.JLSJ = JLSJ;
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

    public String getSHR() {
        return SHR;
    }

    public void setSHR(String SHR) {
        this.SHR = SHR;
    }

    public Date getSHSJ() {
        return SHSJ;
    }

    public void setSHSJ(Date SHSJ) {
        this.SHSJ = SHSJ;
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

    public String getShrname() {
        return shrname;
    }

    public void setShrname(String shrname) {
        this.shrname = shrname;
    }
}
