package com.item.eshop.service;

import com.item.eshop.model.Recommend;

import java.util.List;

public interface RecommendService {
    int deleteByPrimaryKey(Integer id);

    int insert(Recommend record);

    int insertSelective(Recommend record);

    Recommend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Recommend record);

    int updateByPrimaryKey(Recommend record);

    // add : chan 2018/4/4
    List<Recommend> selectByType(Integer r_type, Integer page, Integer num);

    // add : chan 2018/4/4
    List<Recommend> selectMore(Integer page,Integer num);
}
