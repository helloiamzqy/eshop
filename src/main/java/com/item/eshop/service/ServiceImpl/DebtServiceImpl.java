package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.DebtMapper;
import com.item.eshop.model.Debt;
import com.item.eshop.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "debtService")
public class DebtServiceImpl implements DebtService {

    @Autowired
    DebtMapper debtMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return debtMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Debt record) {
        return debtMapper.insert(record);
    }

    @Override
    public int insertSelective(Debt record) {
        return debtMapper.insertSelective(record);
    }

    @Override
    public Debt selectByPrimaryKey(Integer id) {
        return debtMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Debt record) {
        return debtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Debt record) {
        return debtMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Debt> selectMore(Integer page, Integer num) {
        return debtMapper.selectMore((page-1)*num,num);
    }

    @Override
    public List<Debt> selectMoreByValid( Integer valid,Integer page, Integer num) {
        return debtMapper.selectMoreByValid(valid,(page-1)*num, num);
    }
}
