package com.item.eshop.service;

import com.item.eshop.model.Debt;
import com.item.eshop.model.DebtRecord;

import java.util.List;

public interface DebtRecordService {
    int deleteByPrimaryKey(String id);

    int insert(DebtRecord record);

    int insertSelective(DebtRecord record);

    DebtRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DebtRecord record);

    int updateByPrimaryKey(DebtRecord record);

    List<DebtRecord> selectMore(Integer page, Integer num);

    List<DebtRecord> selectMoreByUserId(int user_id, Integer page, Integer num);
}
