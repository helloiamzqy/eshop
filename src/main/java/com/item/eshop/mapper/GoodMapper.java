package com.item.eshop.mapper;

import com.item.eshop.model.Good;
import com.item.eshop.model.User;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Good record);

    int insertSelective(Good record);

    Good selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKey(Good record);

    //add : chan 2018/4/4
    List<Good> selectMore(Integer shopId, Integer page, Integer num);

    //add : chan 2018/4/4
    List<Good> selectByCategory(Integer shopId,Integer category,Integer page,Integer num);

    //add : chan 2018/4/18
    List<Good> selectByStatus(Integer shopId,Integer status,Integer page,Integer num);

    List<Good> selectByOther(Integer shopId,String other, Integer page, Integer num);

    List<Good> selectByName(Integer shopId,String name, Integer page, Integer num);

    List<Good> selectBySort(Integer shopId,Integer category, Integer page, Integer num);

    List<Good> selectBySet(String id_arr);
}