package com.item.eshop.mapper;

import com.item.eshop.model.Debt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Debt record);

    int insertSelective(Debt record);

    Debt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Debt record);

    int updateByPrimaryKey(Debt record);

    // add: chan  2018/4/4
    List<Debt> selectMore(Integer page, Integer num);

    // add: chan  2018/4/16
    List<Debt> selectMoreByValid(Integer valid,Integer page, Integer num);

    int updateValidToPay();

    int updateValidToExpire();

    Integer[] selectForRePay();

}