package com.item.eshop.controller;

import com.item.eshop.mapper.GoodCategoryMapper;
import com.item.eshop.model.GoodCategory;
import com.item.eshop.service.GoodCategoryService;
import com.item.eshop.util.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/category")
public class GoodCategoryController {

    @Autowired
    GoodCategoryService goodCategoryService;

    @Autowired
    GoodCategoryMapper goodCategoryMapper;


    @PostMapping("/add")
    public int add(@RequestParam(value = "name")String name) {
        GoodCategory goodCategory = new GoodCategory();
        int sort_no = goodCategoryMapper.selectCountNum();
        goodCategory.setId(sort_no+1);
        goodCategory.setSort_no(sort_no+1);
        goodCategory.setName(name);
        return goodCategoryService.insert(goodCategory);
    }

    @PostMapping("/delete")
    public int delete(@RequestParam(value = "id")Integer id) {
        return goodCategoryService.deleteByPrimaryKey(id);
    }

    // ======================  admin background management system interface ( user interceptor) =============================

    @PostMapping("/updateName")
    public int updateName(@RequestParam(value = "name")String name,@RequestParam(value = "id")Integer id) {
        GoodCategory goodCategory = new GoodCategory();
        goodCategory.setId(id);
        goodCategory.setName(name);
        return goodCategoryService.updateByPrimaryKeySelective(goodCategory);
    }

    @PostMapping("/updateSort")
    public int updateSort(@RequestParam(value = "no")int no,@RequestParam(value = "move")int move,@RequestParam(value = "id")Integer id) {
        int result = goodCategoryMapper.updateBySort(no,no+move);
        if(result==1){
            return goodCategoryMapper.updateById(no+move,id);
        }
        return 0;
    }

    @PostMapping("/findById")
    public String find(@RequestParam(value = "id")Integer id) {
        return JsonObject.toJson(goodCategoryService.selectByPrimaryKey(id));
    }

    @PostMapping("/findMore")
    public String findMore() {
        return JsonObject.toJson(goodCategoryService.selectMore());
    }

    @PostMapping("/findBySort")
    public String findByValid() {
        return JsonObject.toJson(goodCategoryService.selectMoreBySort());
    }
}
