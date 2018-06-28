package com.item.eshop.model;

import java.math.BigDecimal;
import java.util.Date;

public class DebtRecord {
    private String id;
    private int debt_id;
    private Date usetime;
    private BigDecimal cost;
    private String out_trade_no;
    private String trade_no;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDebt_id() {
        return debt_id;
    }

    public void setDebt_id(int debt_id) {
        this.debt_id = debt_id;
    }

    public Date getUsetime() {
        return usetime;
    }

    public void setUsetime(Date usetime) {
        this.usetime = usetime;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
