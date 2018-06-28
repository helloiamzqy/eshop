package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.DebtRecordMapper;
import com.item.eshop.model.Debt;
import com.item.eshop.model.DebtRecord;
import com.item.eshop.service.DebtRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "DebtRecordService")
public class DebtRecordServiceImpl implements DebtRecordService {
    @Autowired
    DebtRecordMapper debtRecordMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return debtRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DebtRecord record) {
        return debtRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(DebtRecord record) {
        return debtRecordMapper.insertSelective(record);
    }

    @Override
    public DebtRecord selectByPrimaryKey(String id) {
        return debtRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(DebtRecord record) {
        return debtRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(DebtRecord record) {
        return debtRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<DebtRecord> selectMore(Integer page, Integer num) {
        return debtRecordMapper.selectMore(page,num);
    }

    @Override
    public List<DebtRecord> selectMoreByUserId(int user_id, Integer page, Integer num) {
        return debtRecordMapper.selectMoreByUserId(user_id, page, num);
    }
}
