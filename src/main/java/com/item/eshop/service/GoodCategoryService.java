package com.item.eshop.service;

import com.item.eshop.model.Debt;
import com.item.eshop.model.GoodCategory;

import java.util.List;

public interface GoodCategoryService {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodCategory goodCategory);

    int insertSelective(GoodCategory goodCategory);

    Debt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodCategory goodCategory);

    int updateByPrimaryKey(GoodCategory goodCategory);

    // add: chan  2018/4/17
    List<GoodCategory> selectMore();

    // add: chan  2018/4/16
    List<GoodCategory> selectMoreBySort();

    int updateSortUp(int id,int min,int max);

    int updateSortDown(int id,int min,int max);
}
