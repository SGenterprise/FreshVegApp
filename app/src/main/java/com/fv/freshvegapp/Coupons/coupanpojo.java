package com.fv.freshvegapp.Coupons;

public class coupanpojo {
    public String couponid;
    public String valid,min,per,upto;

public coupanpojo(){}

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getUpto() {
        return upto;
    }

    public void setUpto(String upto) {
        this.upto = upto;
    }
}
