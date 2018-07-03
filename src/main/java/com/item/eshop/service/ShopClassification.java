package com.item.eshop.service;

import com.item.eshop.model.Province;
import com.item.eshop.model.School;
import com.item.eshop.model.Shop;

import java.util.List;

/**
 * add zheng 2018-6-30
 * 店铺分类查询接口
 */
public interface ShopClassification {
    List<Province> getAllProvice();
    List<School> getAllSchool();
    List<School> getSchoolByProvice(Integer id);
    //获取属于该学校的商铺
    List<Shop> selectShopBySchoolId(Integer id);
    //模糊查询
     List<School> selectSchoolByName(String name);
}
