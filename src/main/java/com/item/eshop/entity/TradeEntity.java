package com.item.eshop.entity;

import com.item.eshop.model.Address;
import com.item.eshop.model.Good;
import com.item.eshop.model.Trade;

import java.util.List;

public class TradeEntity {
//    private Address address;
    private Trade trade;
    private String address;
    private List<Good> goods;   // TradeGood.count -> good.count

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }
}
