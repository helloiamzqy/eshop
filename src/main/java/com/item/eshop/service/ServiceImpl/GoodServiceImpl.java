package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.GoodMapper;
import com.item.eshop.model.Good;
import com.item.eshop.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "goodService")
public class GoodServiceImpl implements GoodService {

    @Autowired
    GoodMapper goodMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return goodMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Good record) {
        return goodMapper.insert(record);
    }

    @Override
    public int insertSelective(Good record) {
        return goodMapper.insertSelective(record);
    }

    @Override
    public Good selectByPrimaryKey(Integer id) {
        return goodMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Good record) {
        return goodMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Good record) {
        return goodMapper.updateByPrimaryKey(record);
    }

    //add : chan 2018/4/4
    @Override
    public List<Good> selectMore(Integer page, Integer num) {
        return goodMapper.selectMore(page,num);
    }

    //add : chan 2018/4/4
    @Override
    public List<Good> selectByCategory(Integer category, Integer page, Integer num) {
        return goodMapper.selectByCategory(category,page,num);
    }

    @Override
    public List<Good> selectByStatus(Integer status, Integer page, Integer num) {
        return goodMapper.selectByStatus(status, page, num);
    }

    @Override
    public List<Good> selectByOther(String other, Integer page, Integer num) {
        return goodMapper.selectByOther(other,page,num);
    }

    @Override
    public List<Good> selectByName(String name, Integer page, Integer num) {
        return goodMapper.selectByName(name, page, num);
    }

    @Override
    public List<Good> selectBySort(Integer category, Integer page, Integer num) {
        return goodMapper.selectBySort(category, page, num);
    }

    @Override
    public List<Good> selectBySet(String id_arr) {
        return goodMapper.selectBySet(id_arr);
    }


}
