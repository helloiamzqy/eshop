package com.item.eshop.service.ServiceImpl;


import com.item.eshop.mapper.CouponMapper;
import com.item.eshop.model.Coupon;
import com.item.eshop.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "couponService")
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponMapper couponMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return couponMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Coupon record) {
        return couponMapper.insert(record);
    }

    @Override
    public int insertSelective(Coupon record) {
        return couponMapper.insertSelective(record);
    }

    @Override
    public Coupon selectByPrimaryKey(Integer id) {
        return couponMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Coupon record) {
        return couponMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Coupon record) {
        return couponMapper.updateByPrimaryKey(record);
    }

    // add : chan  2018/4/4
    @Override
    public List<Coupon> selectMore(Integer page, Integer num) {
        return couponMapper.selectMore(page,num);
    }
}
