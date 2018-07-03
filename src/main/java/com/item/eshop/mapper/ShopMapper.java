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

    List<Shop> selectMore(Integer page, Integer num);

    List<Shop> selectMoreByCategory(Integer valid, Integer page, Integer num);

    Shop selectByUser(Integer id);
    //add zheng 2018.6.30
    List<Shop> selectShopBySchoolId(Integer id);
}