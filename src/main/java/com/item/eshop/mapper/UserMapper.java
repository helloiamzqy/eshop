package com.item.eshop.mapper;

import com.item.eshop.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    // add :  chan  2018/4/4
    User selectByLogin(String phone, String passwd);

    // add : chan 2018/4/10
    User selectByPhone(String phone);

    List<User> selectMore(Integer page,Integer num);

    List<User> selectMoreByStatus(Integer page,Integer num,Integer status);

}