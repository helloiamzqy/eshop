package com.item.eshop.mapper;

import com.item.eshop.model.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    // add: chan  2018/4/4
    List<Shop> selectMore(Integer page, Integer num);

    // add: chan  2018/4/16
    List<Shop> selectMoreByCategory(Integer valid, Integer page, Integer num);

}