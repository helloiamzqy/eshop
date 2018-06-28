package com.item.eshop.controller;

import com.item.eshop.model.Address;
import com.item.eshop.model.UserCoupon;
import com.item.eshop.model.UserCoupon;
import com.item.eshop.service.UserCouponService;
import com.item.eshop.util.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Api(value = "UserCouponController", description = "用户优惠券增删改查等访问接口")
@Controller
@RequestMapping("/userCoupon")
public class UserCouponController {
    
    @Autowired
    UserCouponService userCouponService;

    @ApiOperation(value="用户获取优惠券",notes="返回值（int):  1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "userCoupon", value = "优惠券用户", required = true ,dataType = "String",paramType = "query")})
    @ResponseBody
    @Transactional
    @PostMapping("/add")
    public int add(@RequestParam(value = "userCoupon")String userCoupon,HttpSession httpSession) {
        UserCoupon  uc = (UserCoupon) JsonObject.toObject(userCoupon,"userCoupon");
        int user_id = (int) httpSession.getAttribute("user_id");
        uc.setUserId(user_id);
        System.out.println(user_id+",,,"+uc.getUserId());
        return userCouponService.insertSelective(uc);
    }

//    @ApiOperation(value="用户删除优惠券",notes="返回值（int):  1:成功，0：失败")
//    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户优惠券关联id", required = true ,dataType = "Integer",paramType = "query")})
//    @ResponseBody
//    @PostMapping("/delete")
//    public int delete(@RequestParam(value = "id")int id) {
//        return userCouponService.deleteByPrimaryKey(id);
//    }




    @ApiOperation(value="用户使用优惠券",notes="返回值（int):  1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "优惠券用户对象", required = true ,dataType = "Integer",paramType = "query")})
    @ResponseBody
    @PostMapping("/use")
    public int use(@RequestParam(value = "id")Integer id, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        UserCoupon uc = new UserCoupon();
        uc.setUserId(user_id);
        uc.setId(id);
        return userCouponService.updateByPrimaryKeySelective(uc);
    }



    // add :  chan  2018/4/4
    @ApiOperation(value="查找用户所有的优惠券",notes="返回值（UserCoupon对象)数组或null")
    @ApiImplicitParams({})
    @ResponseBody
    @PostMapping("/findByUser")
    public String findByUser(HttpSession httpSession) {
        int user_id =  (int) httpSession.getAttribute("user_id");
        return JsonObject.toJson(userCouponService.selectByUser(user_id));
    }

    // add : chan 2018/4/14
    @ApiOperation(value="查找用户可领取的优惠券",notes="返回值（Coupon对象)数组或null")
    @ApiImplicitParams({})
    @ResponseBody
    @PostMapping("/findUserNoGet")
    public String selectUserNoGet(HttpSession httpSession) {
        int user_id =  (int) httpSession.getAttribute("user_id");
        return JsonObject.toJson(userCouponService.selectByUserValid(user_id));
    }

    // ======================  admin background management system interface ( user interceptor) =============================

    @ResponseBody
    @PostMapping("/find")
    public String find(@RequestParam(value = "id")int id) {
        return JsonObject.toJson(userCouponService.selectByPrimaryKey(id));
    }

//    // add :  chan  2018/4/4
//    @ResponseBody
//    @PostMapping("/findMore")
//    public String findMore(@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num) {
//        return JsonObject.toJson(userCouponService.selectMore((page-1)*num,num));
//    }
//
//    // add :  chan  2018/4/4
//    @ResponseBody
//    @PostMapping("/findByType")
//    public String findByType(@RequestParam(value = " status")Integer status,@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num) {
//        return JsonObject.toJson(userCouponService.selectByStatus(status,(page-1)*num,num));
//    }
}
