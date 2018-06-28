package com.item.eshop.model;

import java.math.BigDecimal;

public class Debt {
    private Integer id;

    private BigDecimal max_limit;

    private BigDecimal cost;

    private Integer valid;   // add :   chan  2018/4/4

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMax_limit() {
        return max_limit;
    }

    public void setMax_limit(BigDecimal max_limit) {
        this.max_limit = max_limit;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}