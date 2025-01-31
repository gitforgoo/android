package com.goodtechsystem.mypwd.vo;

import com.goodtechsystem.mypwd.util.EncDecUtil;

public class PwdVO {

    private String oid;
    private String site;

    private String id;
    private String password;
    private String purpose;
    private String remark;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getId() {
        //return id;

        return new EncDecUtil().decryptString(id);
    }

    public void setId(String id) {
        //this.id = id;
        this.id = new EncDecUtil().encryptString(id);
    }

    public String getPassword() {
        //return password;
        return new EncDecUtil().decryptString(password);
    }

    public void setPassword(String password) {
        //this.password = password;
        this.password = new EncDecUtil().encryptString(password);
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
