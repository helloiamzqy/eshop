package com.item.eshop.mapper;

import com.item.eshop.model.ShopcarGood;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopcarGoodMapper {
    int deleteByPrimaryKey(Integer id, Integer user_id);

    int deleteByUser(Integer user_id);

    int insert(ShopcarGood record);

    int insertSelective(ShopcarGood record);

    ShopcarGood selectByPrimaryKey(Integer id,Integer user_id);

    int updateByPrimaryKeySelective(ShopcarGood record);

    int updateByPrimaryKey(ShopcarGood record);

    List<ShopcarGood> selectByUser(Integer user_id);

    int deleteByGood(Integer good_id);
}