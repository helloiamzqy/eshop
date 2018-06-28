package com.item.eshop.service;

import com.item.eshop.model.Address;

import java.util.List;

public interface AddressService {
    int deleteByPrimaryKey(Integer id,Integer user_id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    // add :  chan  2018/4/4
    List<Address> selectByUserId(Integer user_id);
}
