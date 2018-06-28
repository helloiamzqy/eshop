package com.item.eshop.model;

import java.math.BigDecimal;
import java.util.Date;

public class Trade {
    private String id;

    private Date starttime;

    private BigDecimal amount;

    private String tradeGood;

    private BigDecimal factAmount;

    private String couponId;

    private Integer userId;

    private Integer pay_type;

    private Integer addressId;

    private Integer status;

    private Date endtime;

    private String address;

    private String image;

    private String pay_trade_no;

    public String getPay_trade_no() {
        return pay_trade_no;
    }

    public void setPay_trade_no(String pay_trade_no) {
        this.pay_trade_no = pay_trade_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTradeGood() {
        return tradeGood;
    }

    public void setTradeGood(String tradeGood) {
        this.tradeGood = tradeGood;
    }

    public BigDecimal getFactAmount() {
        return factAmount;
    }

    public void setFactAmount(BigDecimal factAmount) {
        this.factAmount = factAmount;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}