package com.item.eshop.controller;

import com.item.eshop.config.Constant;
import com.item.eshop.model.Recommend;
import com.item.eshop.service.RecommendService;
import com.item.eshop.util.JsonObject;
import com.item.eshop.util.UploadImage;
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
import org.springframework.web.multipart.MultipartFile;

@Api(value = "RecommendController", description = "（后台管理系统接口）推荐栏增删改查等访问接口")
@Controller
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    RecommendService recommendService;

    @Autowired
    Constant constant;

//    @ApiOperation(value="根据id值查找推荐",notes="返回值(推荐对象)或null")
//    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "推荐id", required = true ,dataType = "Integer",paramType = "query")})
//    @ResponseBody
//    @PostMapping("/find")
//    public String find(@RequestParam(value = "id")Integer id) {
//        return JsonObject.toJson(recommendService.selectByPrimaryKey(id));
//    }

    // add :  chan  2018/4/4
    @ApiOperation(value="按序根据页数查找多个推荐",notes="返回值(推荐对象)数组或null")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", required = true ,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "num", value = "推荐展示数量", required = true ,dataType = "Integer",paramType = "query")})
    @ResponseBody
    @PostMapping("/findMore")
    public String findMore() {
        return JsonObject.toJson(recommendService.selectMore(0,7));
    }

    // add :  chan  2018/4/4
    @ApiOperation(value="按序根据页数查找多个推荐",notes="返回值(推荐对象)数组或null")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", required = true ,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "num", value = "推荐展示数量", required = true ,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "type", value = "推荐展示类型", required = true ,dataType = "Integer",paramType = "query")})
    @ResponseBody
    @PostMapping("/findByType")
    public String findByType() {
        return JsonObject.toJson(recommendService.selectByType(1,0,7));
    }

    // ======================  admin background management system interface ( user interceptor) =============================

//    @ResponseBody
//    @PostMapping("/add")
//    public int add(@RequestParam(value = "recommend")Recommend recommend) {
//        return recommendService.insert(recommend);
//    }
//
//    @ResponseBody
//    @PostMapping("/delete")
//    public int delete(@RequestParam(value = "id")Integer id) {
//        return recommendService.deleteByPrimaryKey(id);
//    }

    @ResponseBody
    @PostMapping("/update")
    public int update(@RequestParam(value = "id")int id,@RequestParam(value = "image")MultipartFile image) {
        String url = new UploadImage().uploadImage(image,3,constant.getPath(),constant.getUrl());
        Recommend recommend = new Recommend();
        recommend.setId(id);
        recommend.setImage(url);
        return recommendService.updateByPrimaryKeySelective(recommend);
    }

    @ResponseBody
    @PostMapping("/updateStatus")
    public int update(@RequestParam(value = "id")int id,@RequestParam(value = "status")int status) {
        Recommend recommend = new Recommend();
        recommend.setId(id);
        recommend.setrType(status);
        return recommendService.updateByPrimaryKeySelective(recommend);
    }

    @ResponseBody
    @PostMapping("/updateLike")
    public int updateLike(@RequestParam(value = "id")int id) {
        Recommend recommend = recommendService.selectByPrimaryKey(id);
        recommend.setId(id);
        recommend.setGoodId(recommend.getGoodId()+1);
        return recommendService.updateByPrimaryKeySelective(recommend);
    }
}
