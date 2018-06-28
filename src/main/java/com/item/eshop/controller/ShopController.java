package com.item.eshop.controller;
import com.item.eshop.model.Shop;
import com.item.eshop.service.ShopService;
import com.item.eshop.util.JsonObject;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Api(value = "ShopController", description = "（后台管理系统接口）商铺管理等访问接口")
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    // ======================  admin background management system interface ( user interceptor) =============================

    @PostMapping("/add")
    public int add(@RequestParam(value = "name")String name,
                   @RequestParam(value = "introduce")String introduce,
                   @RequestParam(value = "contact")String contact,
                   @RequestParam(value = "school_id")int school_id,
                   @RequestParam(value = "address")String address,
                   @RequestParam(value = "image")String image,
                   @RequestParam(value = "status")int status,
                    HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        Shop shop = new Shop();
        shop.setUserId(user_id);
        shop.setName(name);
        shop.setIntroduce(introduce);
        shop.setStatus(status);
        shop.setImage(image);
        shop.setSchoolId(school_id);
        shop.setContact(contact);
        shop.setAddress(address);
        return shopService.insertSelective(shop);
    }

    @PostMapping("/update")
    public int add(@RequestParam(value = "id")String id,
                   @RequestParam(value = "name")String name,
                   @RequestParam(value = "introduce")String introduce,
                   @RequestParam(value = "contact")String contact,
                   @RequestParam(value = "school_id")int school_id,
                   @RequestParam(value = "address")String address,
                   @RequestParam(value = "image")String image,
                   @RequestParam(value = "status")int status,
                   HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        Shop shop = new Shop();
        shop.setName(name);
        shop.setIntroduce(introduce);
        shop.setStatus(status);
        shop.setImage(image);
        shop.setSchoolId(school_id);
        shop.setContact(contact);
        shop.setAddress(address);
        return shopService.updateByPrimaryKeySelective(shop);
    }

    @PostMapping("/delete")
    public int delete(@RequestParam(value = "id")Integer id) {
        return shopService.deleteByPrimaryKey(id);
    }

    @PostMapping("/findByCategory")
    public String findByCategory(@RequestParam(value = "category")Integer category,@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num) {
        return JsonObject.toJson(shopService.selectMoreByCategory(category,(page-1)*num,num));
    }

    @PostMapping("/findShop")
    public String findByUser() {
        int id = 0;
        return JsonObject.toJson(shopService.selectByPrimaryKey(id));
    }

    // ======================  admin background management system interface ( user interceptor) =============================

    @PostMapping("/findById")
    public String find(@RequestParam(value = "id")Integer id) {
        return JsonObject.toJson(shopService.selectByPrimaryKey(id));
    }

    // add :  chan  2018/4/4
    @PostMapping("/findMore")
    public String findMore(@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num) {
        System.out.println("page:"+page+",num:"+num);
        return JsonObject.toJson(shopService.selectMore((page-1)*num,num));
    }

}
