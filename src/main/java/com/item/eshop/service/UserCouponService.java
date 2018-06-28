package com.item.eshop.service;

import com.item.eshop.model.Coupon;
import com.item.eshop.model.UserCoupon;

import java.util.List;

public interface UserCouponService {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    UserCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);

    UserCoupon selectByUserAndCoupon(Integer user_id, Integer coupon_id);

    List<UserCoupon> selectByUser(Integer user_id);

    List<Coupon> selectByUserValid(Integer user_id);
}
