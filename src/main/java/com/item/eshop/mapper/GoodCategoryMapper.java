package com.item.eshop.mapper;

import com.item.eshop.model.Debt;
import com.item.eshop.model.GoodCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodCategoryMapper {
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

    int updateSortUp(int min,int max);

    int updateSortDown(int min,int max);

    int updateBySort(int newSort,int oldSort);

    int selectCountNum();

    int updateById(int sort_no,int id);
}