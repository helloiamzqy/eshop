package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.AddressMapper;
import com.item.eshop.model.Address;
import com.item.eshop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    @Override
    public int deleteByPrimaryKey(Integer id,Integer user_id) {
        return addressMapper.deleteByPrimaryKey(id,user_id);
    }

    @Override
    public int insert(Address record) {
        return addressMapper.insert(record);
    }

    @Override
    public int insertSelective(Address record) {
        return addressMapper.insertSelective(record);
    }

    @Override
    public Address selectByPrimaryKey(Integer id) {
        return addressMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Address record) {
        return addressMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Address record) {
        return addressMapper.updateByPrimaryKey(record);
    }

    // add :  chan  2018/4/4
    @Override
    public List<Address> selectByUserId(Integer user_id) {
        return addressMapper.selectByUserId(user_id);
    }
}
