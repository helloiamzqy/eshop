package com.item.eshop.service;

import com.item.eshop.model.ShopcarGood;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShopcarGoodService {
    int deleteByPrimaryKey(Integer id,Integer user_id);

    int deleteByUser(Integer user_id);

    int insert(ShopcarGood record);

    int insertSelective(ShopcarGood record);

    ShopcarGood selectByPrimaryKey(Integer id,Integer user_id);

    int updateByPrimaryKeySelective(ShopcarGood record);

    int updateByPrimaryKey(ShopcarGood record);

    List<ShopcarGood> selectByUser(Integer user_id);

    int deleteByGood(Integer good_id);
}
