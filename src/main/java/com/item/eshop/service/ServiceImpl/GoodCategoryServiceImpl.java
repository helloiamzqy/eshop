package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.GoodCategoryMapper;
import com.item.eshop.model.Debt;
import com.item.eshop.model.GoodCategory;
import com.item.eshop.service.GoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "goodCategoryService")
public class GoodCategoryServiceImpl implements GoodCategoryService {

    @Autowired
    GoodCategoryMapper goodCategoryMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return goodCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(GoodCategory goodCategory) {
        GoodCategory gc = goodCategory;
        gc.setId(null);
        return goodCategoryMapper.insert(gc);
    }

    @Override
    public int insertSelective(GoodCategory goodCategory) {
        GoodCategory gc = goodCategory;
        gc.setId(null);
        return goodCategoryMapper.insertSelective(gc);
    }

    @Override
    public Debt selectByPrimaryKey(Integer id) {
        return goodCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(GoodCategory goodCategory) {
        GoodCategory gc = goodCategory;
        gc.setSort_no(null);
        return goodCategoryMapper.updateByPrimaryKeySelective(goodCategory);
    }

    @Override
    public int updateByPrimaryKey(GoodCategory goodCategory) {
        return 0;
    }

    @Override
    public List<GoodCategory> selectMore() {
        return goodCategoryMapper.selectMore();
    }

    @Override
    public List<GoodCategory> selectMoreBySort() {
        return goodCategoryMapper.selectMoreBySort();
    }

    @Override
    public int updateSortUp(int id, int min, int max) {
        int result = goodCategoryMapper.updateSortUp(min,max);
        if(result==0)
            return 0;
        GoodCategory gc = new GoodCategory();
        gc.setId(id);
        gc.setSort_no(min);
        return goodCategoryMapper.updateByPrimaryKeySelective(gc);
    }

    @Override
    public int updateSortDown(int id, int min, int max) {
        int result = goodCategoryMapper.updateSortDown(min,max);
        if(result==0)
            return 0;
        GoodCategory gc = new GoodCategory();
        gc.setId(id);
        gc.setSort_no(max);
        return goodCategoryMapper.updateByPrimaryKeySelective(gc);
    }
}
