package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.RecommendMapper;
import com.item.eshop.model.Recommend;
import com.item.eshop.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "recommendService")
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    RecommendMapper recommendMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return recommendMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Recommend record) {
        return recommendMapper.insert(record);
    }

    @Override
    public int insertSelective(Recommend record) {
        return recommendMapper.insertSelective(record);
    }

    @Override
    public Recommend selectByPrimaryKey(Integer id) {
        return recommendMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Recommend record) {
        return recommendMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Recommend record) {
        return recommendMapper.updateByPrimaryKey(record);
    }

    // add : chan 2018/4/4
    @Override
    public List<Recommend> selectByType(Integer r_type, Integer page, Integer num) {
        return recommendMapper.selectByType(r_type,page,num);
    }

    // add : chan 2018/4/4
    @Override
    public List<Recommend> selectMore(Integer page, Integer num) {
        return recommendMapper.selectMore(page,num);
    }
}
