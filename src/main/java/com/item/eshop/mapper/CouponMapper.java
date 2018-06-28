package com.item.eshop.mapper;

import com.item.eshop.model.Coupon;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    // add :  chan  2018/4/4
    List<Coupon> selectMore(Integer page,Integer num);

    //add : chan  2018/4/13
    int reduceOne(Integer id);
}