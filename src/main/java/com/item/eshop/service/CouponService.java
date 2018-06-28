package com.item.eshop.service;

import com.item.eshop.model.Coupon;

import java.util.List;

public interface CouponService {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    // add : chan  2018/4/4
    List<Coupon> selectMore(Integer page, Integer num);
}
