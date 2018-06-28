package com.item.eshop.service;

import com.item.eshop.model.Notice;

import java.util.List;

public interface NoticeService {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    // add: chan  2018/4/4
    List<Notice> selectMore(Integer page, Integer num);

    // add: chan  2018/4/16
    List<Notice> selectMoreByCategory(Integer valid, Integer page, Integer num);
}
