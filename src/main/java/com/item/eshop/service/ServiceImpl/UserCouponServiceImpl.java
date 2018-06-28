package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.CouponMapper;
import com.item.eshop.mapper.UserCouponMapper;
import com.item.eshop.model.Coupon;
import com.item.eshop.model.UserCoupon;
import com.item.eshop.service.UserCouponService;
import com.item.eshop.util.JsonObject;
import com.item.eshop.util.LaterDate;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userCouponService")
public class UserCouponServiceImpl implements UserCouponService {

    @Autowired
    UserCouponMapper userCouponMapper;

    @Autowired
    CouponMapper couponMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userCouponMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserCoupon record) {
        UserCoupon uc = selectByUserAndCoupon(record.getUserId(),record.getCouponId());
        if(uc!=null){
            return -2;
        }
        Coupon coupon = couponMapper.selectByPrimaryKey(record.getCouponId());
        if(coupon==null){
            return -3;
        }
        if(couponMapper.reduceOne(record.getCouponId())==0){
            return -1;
        }
        uc = record;
        uc.setId(null);
        uc.setValid(1);
        System.out.println(JsonObject.toJson(coupon));
        uc.setDeadline(LaterDate.plusDay(coupon.getDay()));
        return userCouponMapper.insert(uc);
    }

    @Override
    public int insertSelective(UserCoupon record) {
        return  insert(record);
    }

    @Override
    public UserCoupon selectByPrimaryKey(Integer id) {
        return userCouponMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserCoupon record) {
        userCouponMapper.useCoupon(record.getId(),record.getCouponId());
        return userCouponMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserCoupon record) {
        return userCouponMapper.updateByPrimaryKey(record);
    }

    @Override
    public UserCoupon selectByUserAndCoupon(Integer user_id, Integer coupon_id) {
        return userCouponMapper.selectByUserAndCoupon(user_id, coupon_id);
    }

    @Override
    public List<UserCoupon> selectByUser(Integer user_id) {
        return userCouponMapper.selectByUser(user_id);
    }

    @Override
    public List<Coupon> selectByUserValid(Integer user_id) {
        return userCouponMapper.selectByUserValid(user_id);
    }
}
