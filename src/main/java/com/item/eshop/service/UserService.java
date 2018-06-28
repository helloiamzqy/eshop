package com.item.eshop.service;

import com.item.eshop.model.User;

import java.util.List;

public interface UserService {
    int deleteUserById(int id);
    int register(User user, int code);
    User login(String phone, String passwd);
    int updateUser(User user);
    int checkupUser(int id, int status);
    User selectUser(int id);
    int sendSms(String phone);
    boolean logout(String session_id);
    User selectByPhone(String phone,int code);
    List<User> selectMore(Integer page,Integer num);
    List<User> selectMoreByStatus(Integer page,Integer num,Integer status);
    User findByPhone(String phone);
}
