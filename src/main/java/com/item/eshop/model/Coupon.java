package com.item.eshop.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Coupon {
    private Integer id;

    private BigDecimal total;

    private BigDecimal minus;

    private Date deadline;

    private Integer day;

    private Integer valid;

    private Integer count;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getMinus() {
        return minus;
    }

    public void setMinus(BigDecimal minus) {
        this.minus = minus;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}