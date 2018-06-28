package com.item.eshop.mapper;

import com.item.eshop.model.Debt;
import com.item.eshop.model.DebtRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(DebtRecord record);

    int insertSelective(DebtRecord record);

    DebtRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DebtRecord record);

    int updateByPrimaryKey(DebtRecord record);

    List<DebtRecord> selectMore(Integer page, Integer num);

    List<DebtRecord> selectMoreByUserId(int user_id, Integer page, Integer num);

}