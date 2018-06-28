package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.TradeGoodMapper;
import com.item.eshop.model.Trade;
import com.item.eshop.model.TradeGood;
import com.item.eshop.service.TradeGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "tradeGoodService")
public class TradeGoodServiceImpl implements TradeGoodService {

    @Autowired
    TradeGoodMapper tradeGoodMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tradeGoodMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TradeGood record) {
        return tradeGoodMapper.insert(record);
    }

    @Override
    public int insertSelective(TradeGood record) {
        return tradeGoodMapper.insertSelective(record);
    }

    @Override
    public TradeGood selectByPrimaryKey(Integer id) {
        return tradeGoodMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TradeGood record) {
        return tradeGoodMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TradeGood record) {
        return tradeGoodMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TradeGood> selectByTrade(String trade_id) {
        return tradeGoodMapper.selectByTrade(trade_id);
    }

    @Override
    public List<String> selectByGood(Integer good_id) {
        return tradeGoodMapper.selectByGood(good_id);
    }
}
