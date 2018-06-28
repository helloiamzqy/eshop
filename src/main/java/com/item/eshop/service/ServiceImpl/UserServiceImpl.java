package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.UserMapper;
import com.item.eshop.model.User;
import com.item.eshop.service.UserService;
import com.item.eshop.util.Cache;
import com.item.eshop.util.MD5;
import com.item.eshop.util.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import static java.sql.Types.NULL;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public int deleteUserById(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int register(User user, int code) {
        if(user.getPhone()==null||user.getPasswd()==null||code==0)
            return 0;
        String result = Cache.getRedis(user.getPhone());
        System.out.print(result+",,,"+code);
        if(result==null)
            return 1;
        if(Integer.parseInt(result)!=code)
            return 2;
        if(userMapper.selectByPhone(user.getPhone())!=null){
            return 3;
        }
        User u = user;
        u.setId(null);
        u.setStatus(1);
        if(userMapper.insertSelective(user)==0)
            return 4;
        return 5;
    }

    @Override
    public User login(String phone, String passwd) {
        User user = userMapper.selectByLogin(phone,passwd);
        if(user==null)
            return null;
        user.setPasswd("");
        return user;
    }

    @Override
    public int updateUser(User user) {
        User u = user;
//        u.setPhone(null);
//        u.setStatus(NULL);
        return userMapper.updateByPrimaryKeySelective(u);
    }

    @Override
    public int checkupUser(int id, int status) {
        User user = new User();
        user.setStatus(status);
        user.setId(id);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User selectUser(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int sendSms(String phone){
        int code = Sms.send(phone);
        System.out.print("code:"+code);
        if(code==0) return 0;
        Cache.setRedis(phone,code+"");
        return 1;
    }

    @Override
    public boolean logout(String session_id) {
        if(Cache.delRedis(session_id)==0){
            return false;
        }
        return true;
    }

    @Override
    public User selectByPhone(String phone,int code) {
        String cache = Cache.getRedis(phone);
        if(cache==null||Integer.parseInt(cache)!=code)
            return null;
        return userMapper.selectByPhone(phone);
    }

    @Override
    public List<User> selectMore(Integer page, Integer num) {
        return userMapper.selectMore((page-1)*num,num);
    }

    @Override
    public List<User> selectMoreByStatus(Integer page, Integer num, Integer status) {
        return userMapper.selectMoreByStatus((page-1)*num,num,status);
    }

    @Override
    public User findByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

}
