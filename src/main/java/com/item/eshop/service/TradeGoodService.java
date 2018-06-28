package com.item.eshop.service;

import com.item.eshop.model.TradeGood;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TradeGoodService {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeGood record);

    int insertSelective(TradeGood record);

    TradeGood selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeGood record);

    int updateByPrimaryKey(TradeGood record);

    List<TradeGood> selectByTrade(String trade_id);

    List<String> selectByGood(Integer good_id);
}
