package com.item.eshop.mapper;

import com.item.eshop.model.Coupon;
import com.item.eshop.model.UserCoupon;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    UserCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);

    UserCoupon selectByUserAndCoupon(Integer user_id, Integer coupon_id);

    int useCoupon(Integer id,Integer user_id);

    List<UserCoupon> selectByUser(Integer user_id);

    List<Coupon> selectByUserValid(Integer user_id);
}