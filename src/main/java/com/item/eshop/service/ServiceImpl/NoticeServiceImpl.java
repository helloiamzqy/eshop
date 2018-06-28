package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.NoticeMapper;
import com.item.eshop.model.Notice;
import com.item.eshop.service.NoticeService;
import com.sun.tools.corba.se.idl.constExpr.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "NoticeService")
public class NoticeServiceImpl  implements NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return noticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Notice record) {
        return noticeMapper.insert(record);
    }

    @Override
    public int insertSelective(Notice record) {
        return noticeMapper.insertSelective(record);
    }

    @Override
    public Notice selectByPrimaryKey(Integer id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Notice record) {
        return noticeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Notice record) {
        return noticeMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Notice> selectMore(Integer page, Integer num) {
        return noticeMapper.selectMore(page,num);
    }

    @Override
    public List<Notice> selectMoreByCategory(Integer valid, Integer page, Integer num) {
        return noticeMapper.selectMoreByCategory(valid,page,num);
    }
}
