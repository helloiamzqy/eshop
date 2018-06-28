package com.item.eshop.service;

import com.item.eshop.model.Good;

import java.util.List;

public interface

GoodService {
    int deleteByPrimaryKey(Integer id);

    int insert(Good record);

    int insertSelective(Good record);

    Good selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKey(Good record);

    //add : chan 2018/4/4
    List<Good> selectMore(Integer page,Integer num);

    //add : chan 2018/4/4
    List<Good> selectByCategory(Integer category,Integer page,Integer num);

    //add : chan 2018/4/18
    List<Good> selectByStatus(Integer status,Integer page,Integer num);

    //add : chan 2018/4/19
    List<Good> selectByOther(String other,Integer page,Integer num);

    List<Good> selectByName(String name, Integer page, Integer num);

    List<Good> selectBySort(Integer category, Integer page, Integer num);

    List<Good> selectBySet(String id_arr);
}
