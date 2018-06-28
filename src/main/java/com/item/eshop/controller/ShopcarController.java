package com.item.eshop.controller;

import com.item.eshop.model.Good;
import com.item.eshop.model.ShopcarGood;
import com.item.eshop.service.GoodService;
import com.item.eshop.service.ShopcarGoodService;

import com.item.eshop.util.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "ShopcarController", description = "购物车增删改查等访问接口")
@Controller
@RequestMapping("/shopcar")
public class ShopcarController {

    @Autowired
    GoodService goodService;

    @Autowired
    ShopcarGoodService shopcarGoodService;

    @ApiOperation(value="添加商品到购物车",notes="返回值（int):  1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "ShopcarGood", value = "添加商品", required = true ,dataType = "Object",paramType = "query")})
    @ResponseBody
    @PostMapping("/add")
    public int add(@RequestParam(value = "shopcarGood")String shopcarGood, HttpSession httpSession) {
        ShopcarGood shopcarGood1 = (ShopcarGood)JsonObject.toObject(shopcarGood,"shopcarGood");
        int user_id = (int) httpSession.getAttribute("user_id");
        shopcarGood1.setUserId(user_id);
        ShopcarGood shopcarGood2 = shopcarGoodService.selectByPrimaryKey(shopcarGood1.getGoodId(),user_id);
        if(shopcarGood2!=null) {
            int count = shopcarGood2.getCount();
            shopcarGood2.setCount(shopcarGood1.getCount()+count);
            return shopcarGoodService.updateByPrimaryKeySelective(shopcarGood2);
        }
        shopcarGood1.setId(null);
        return shopcarGoodService.insert(shopcarGood1);
    }

    @ApiOperation(value="删除该用户某id值的购物车商品",notes="返回值（int):  1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "购物车id", required = true ,dataType = "Integer",paramType = "query")})
    @ResponseBody
    @PostMapping("/delete")
    public int delete(@RequestParam(value = "id")Integer id, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        return shopcarGoodService.deleteByPrimaryKey(id,user_id);
    }

    @ApiOperation(value="删除某用户的所有购物车商品",notes="返回值（int):  1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "购物车id", required = true ,dataType = "Integer",paramType = "query")})
    @ResponseBody
    @PostMapping("/deleteByUser")
    public int deleteByUser(HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        return shopcarGoodService.deleteByUser(user_id);
    }

    @ApiOperation(value="更新购物车中的商品详情",notes="返回值（int):  1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "ShopcarGood", value = "更新购物车商品详情", required = true ,dataType = "Object",paramType = "query")})
    @ResponseBody
    @PostMapping("/update")
    public int update(@RequestParam(value = "shopcarGood")String shopcarGood,HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        ShopcarGood sg = (ShopcarGood) JsonObject.toObject(shopcarGood,"shopcarGood");
        sg.setUserId(user_id);
        return shopcarGoodService.updateByPrimaryKeySelective(sg);
    }

    @ApiOperation(value="根据用户id值查找所有购物车商品详情",notes="返回值(ShopcarGood对象)数组或NULL值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true ,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "num", value = "展示数量", required = true ,dataType = "Integer",paramType = "query")})
    @ResponseBody
    @PostMapping("/findByUser")
    public String findByUser(HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        List<ShopcarGood> sg =  shopcarGoodService.selectByUser(user_id);
        String ids = "";
        if(sg!=null)
            for(ShopcarGood s:sg)
                ids+=s.getGoodId()+",";
        System.out.println("ids:::"+ids);
        List<Good> goods = goodService.selectBySet(ids+",-2");
        System.out.println("ids:::"+JsonObject.toJson(goods));
        Map result = new HashMap();
        result.put("shopcar",sg);
        result.put("good",goods);
        return JsonObject.toJson(result);
    }

    //  delete :  chan 2018/4/7
//    @ResponseBody
//    @PostMapping("/find")
//    public String find(@RequestParam(value = "id")Integer id) {
//        return JsonObject.toJson(shopcarGoodService.selectByPrimaryKey(id));
//    }


    // ======================  admin background management system interface ( user interceptor) =============================

    // add :  chan  2018/4/4

    
}
