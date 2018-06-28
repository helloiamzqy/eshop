package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.ShopcarGoodMapper;
import com.item.eshop.model.ShopcarGood;
import com.item.eshop.service.ShopcarGoodService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "shopcarGoodService")
public class ShopcarGoodServiceImpl implements ShopcarGoodService {

    @Autowired
    ShopcarGoodMapper shopcarGoodMapper;

    @Override
    public int deleteByPrimaryKey(Integer id, Integer user_id) {
        return shopcarGoodMapper.deleteByPrimaryKey(id,user_id);
    }

    @Override
    public int deleteByUser(Integer user_id) {
        return shopcarGoodMapper.deleteByUser(user_id);
    }

    @Override
    public int insert(ShopcarGood record) {
        return shopcarGoodMapper.insert(record);
    }

    @Override
    public int insertSelective(ShopcarGood record) {
        return shopcarGoodMapper.insertSelective(record);
    }

    @Override
    public ShopcarGood selectByPrimaryKey(Integer id,Integer user_id) {
        return shopcarGoodMapper.selectByPrimaryKey(id,user_id);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopcarGood record) {
        return shopcarGoodMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShopcarGood record) {
        return shopcarGoodMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ShopcarGood> selectByUser(Integer user_id) {
        return shopcarGoodMapper.selectByUser(user_id);
    }

    @Override
    public int deleteByGood(Integer good_id) {
        return shopcarGoodMapper.deleteByGood(good_id);
    }

}
