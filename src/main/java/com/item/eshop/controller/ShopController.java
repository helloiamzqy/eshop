package com.item.eshop.controller;
import com.item.eshop.model.Shop;
import com.item.eshop.service.ShopClassification;
import com.item.eshop.service.ShopService;
import com.item.eshop.util.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Api(value= "ShopController", description = "（后台管理系统接口）商铺管理等访问接口")
@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopClassification shopClassification;

    @Autowired
    ShopService shopService;

    public void setShopClassification(ShopClassification shopClassification) {
        this.shopClassification = shopClassification;
    }

    // ======================  admin background management system interface ( user interceptor) =============================
    @ResponseBody
    @PostMapping("/add")
    @ApiOperation(value="添加shop商铺（后台）",notes="返回值int 1:成功 0:失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商铺名", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "introduce", value = "商铺介绍", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "contact", value = "联系方式", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "school_id", value = "学校id", required = true ,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "address", value = "地址", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态 1:营业 0:休息", required = true ,dataType = "String",paramType = "query"),
            //@ApiImplicitParam(name = "image", value = "商铺图标", required = true ,dataType = "String",paramType = "query"),
    })
    public int add(@RequestParam(value = "name")String name,
                   @RequestParam(value = "introduce")String introduce,
                   @RequestParam(value = "contact")String contact,
                   @RequestParam(value = "school_id")int school_id,
                   @RequestParam(value = "address")String address,
//                   @RequestParam(value = "image")String image,
                   @RequestParam(value = "status")int status,
                    HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        Shop shop = new Shop();
        shop.setUser_id(user_id);
        shop.setName(name);
        shop.setIntroduce(introduce);
        shop.setStatus(status);
        shop.setImage(null);
        shop.setSchool_id(school_id);
        shop.setContact(contact);
        shop.setAddress(address);
        return shopService.insertSelective(shop);
    }

    @ResponseBody
    @PostMapping("/update")
    @ApiOperation(value="更改商铺信息（后台）",notes="返回值int 1:成功 0:失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商铺名", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "introduce", value = "商铺介绍", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "contact", value = "联系方式", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "school_id", value = "学校id", required = true ,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "address", value = "地址", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态 1:营业 0:休息", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "id", value = "商铺id", required = true ,dataType = "int",paramType = "query"),
            //@ApiImplicitParam(name = "image", value = "商铺图标", required = true ,dataType = "String",paramType = "query"),
    })
    public int update(@RequestParam(value = "id")int id,
                   @RequestParam(value = "name")String name,
                   @RequestParam(value = "introduce")String introduce,
                   @RequestParam(value = "contact")String contact,
                   @RequestParam(value = "school_id")int school_id,
                   @RequestParam(value = "address")String address,
                   //@RequestParam(value = "image")String image,
                   @RequestParam(value = "status")int status,
                   HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        Shop shop = new Shop();
        shop.setName(name);
        shop.setIntroduce(introduce);
        shop.setStatus(status);
        //shop.setImage(image);
        shop.setSchool_id(school_id);
        shop.setContact(contact);
        shop.setAddress(address);
        shop.setId(id);
        shop.setUser_id(user_id);
        return shopService.updateByPrimaryKeySelective(shop);
    }

    @ResponseBody
    @PostMapping("/delete")
    @ApiOperation(value="删除商铺（后台）",notes="返回值int 1:成功 0:失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商铺id", required = true ,dataType = "int",paramType = "query"),
    })
    public int delete(@RequestParam(value = "id")Integer id) {
        return shopService.deleteByPrimaryKey(id);
    }

    @ResponseBody
    @PostMapping("/findByCategory")
//    @ApiOperation(value="根据分类查找",notes="返回值int 1:成功 0:失败")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "商铺名", required = true ,dataType = "String",paramType = "query"),
//            @ApiImplicitParam(name = "introduce", value = "商铺介绍", required = true ,dataType = "String",paramType = "query"),
//            @ApiImplicitParam(name = "contact", value = "联系方式", required = true ,dataType = "String",paramType = "query"),
//            @ApiImplicitParam(name = "school_id", value = "学校id", required = true ,dataType = "int",paramType = "query"),
//            @ApiImplicitParam(name = "address", value = "地址", required = true ,dataType = "String",paramType = "query"),
//            @ApiImplicitParam(name = "status", value = "状态 1:营业 0:休息", required = true ,dataType = "String",paramType = "query"),
//            @ApiImplicitParam(name = "id", value = "商铺id", required = true ,dataType = "int",paramType = "query"),
//            //@ApiImplicitParam(name = "image", value = "商铺图标", required = true ,dataType = "String",paramType = "query"),
//    })
    public String findByCategory(@RequestParam(value = "category")Integer category,@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num) {
        return JsonObject.toJson(shopService.selectMoreByCategory(category,(page-1)*num,num));
    }

    @ResponseBody
    @PostMapping("/findByUserId")
    @ApiOperation(value="查询我的商铺信息（后台）",notes="商铺对象 json格式")
    public String findByUser(HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        return JsonObject.toJson(shopService.selectByUser(user_id));
    }

    // ======================  admin background management system interface ( user interceptor) =============================
    @ResponseBody
    @PostMapping("/findById")
    @ApiOperation(value="根据商铺id查询商铺信息（后台）",notes="商铺对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商铺id", required = true ,dataType = "int",paramType = "query"),
            //@ApiImplicitParam(name = "image", value = "商铺图标", required = true ,dataType = "String",paramType = "query"),
    })
    public String find(@RequestParam(value = "id")Integer id) {
        return JsonObject.toJson(shopService.selectByPrimaryKey(id));
    }

    @ResponseBody
    // add :  chan  2018/4/4
    @PostMapping("/findMore")
    @ApiOperation(value="查询所有商铺 分页查询（后台管理员）",notes="商铺对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "需要返回的商铺记录数", required = true ,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true ,dataType = "int",paramType = "query"),
            //@ApiImplicitParam(name = "image", value = "商铺图标", required = true ,dataType = "String",paramType = "query"),
    })
    public String findMore(@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num) {
        return JsonObject.toJson(shopService.selectMore((page-1)*num,num));
    }

    //add zheng 2018-6-30
    @ApiOperation(value="查找所有省份（app）",notes="返回值(省份对象)数组或null")
    @ResponseBody
    @PostMapping("/getAllProvince")
    public String getAllProvince(){
        return JsonObject.toJson(shopClassification.getAllProvice());
    }

    @ResponseBody
    @PostMapping("/getSchoolByProvinceId")
    @ApiOperation(value="根据省份Id查找学校（app）",notes="返回值(学校对象)数组或null")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "省份id", required = true ,dataType = "Integer",paramType = "query"),
           })
    public String getSchoolByProvinceId(Integer id){
        return JsonObject.toJson(shopClassification.getSchoolByProvice(id));
    }

    @ResponseBody
    @PostMapping("/getShopBySchoolId")
    @ApiOperation(value="根据学校id获取商铺（app）",notes="返回值(商铺对象)数组或null")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "学校id", required = true ,dataType = "Integer",paramType = "query"),
    })
    public String getShopBySchoolId(Integer id){
        return JsonObject.toJson(shopClassification.selectShopBySchoolId(id));
    }

    @ResponseBody
    @PostMapping("/getSchoolByName")
    @ApiOperation(value="根据学校名模糊查找学校（app）",notes="返回值(学校对象)数组或null")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "学校名称", required = true ,dataType = "String",paramType = "query"),
    })
    public String getShopBySchoolName(String name){
        return JsonObject.toJson(shopClassification.selectSchoolByName(name));
    }
}
