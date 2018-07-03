package com.item.eshop.mapper;

import com.item.eshop.model.Trade;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Trade record);

    int insertSelective(Trade record);

    Trade selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Trade record);

    int updateByPrimaryKey(Trade record);

    // add  : chan   2018/4/4
    List<Trade> selectByUser(Integer user_id,Integer page,Integer num);

    List<Trade> selectByStatus(Integer shopId,Integer status,Integer page,Integer num);

    List<Trade> selectMore(Integer shopId,Integer page,Integer num);

    // add : chan 2018-4-21
    List<Trade> selectByUserAndStatus(Integer user_id,Integer status, Integer page, Integer num);

    Trade selectByIdAndUserId(String id,Integer user_id);

    // add: chan 2018-5-7
    List<Trade> selectBySet(Integer shopId,Integer user_id,String status);

    List<Trade> selectBySets(Integer shopId,String status,Integer page,Integer num);

    Integer updateTradeSuccess();

    int updateByIdAndStatusOne(String tradeId);
}