package com.item.eshop.mapper;

import com.item.eshop.model.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressMapper {
    int deleteByPrimaryKey(Integer id,Integer user_id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    // add :  chan  2018/4/4
    List<Address> selectByUserId(Integer id);
}