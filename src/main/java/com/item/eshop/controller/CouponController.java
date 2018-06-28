package com.item.eshop.controller;

import com.item.eshop.model.Coupon;
import com.item.eshop.service.CouponService;
import com.item.eshop.util.JsonObject;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Api(value = "CouponController", description = "（后台管理系统接口）优惠券增删改查等访问接口")
@Controller
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @ResponseBody
    @PostMapping("/find")
    public String find(@RequestParam(value = "id")Integer id) {
        return JsonObject.toJson(couponService.selectByPrimaryKey(id));
    }

    // add :  chan  2018/4/4
    @ResponseBody
    @PostMapping("/findMore")
    public String findMore(@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num) {
        return JsonObject.toJson(couponService.selectMore((page-1)*num+1,num));
    }

    // ======================  admin background management system interface ( user interceptor) =============================

    @ResponseBody
    @PostMapping("/add")
    public int add(@RequestParam(value = "total")double total,@RequestParam(value = "minus")double minus,@RequestParam(value = "count")Integer count,@RequestParam(value = "day")Integer day) {
        Coupon c = new Coupon();
        c.setCount(count);
        c.setDay(day);
        c.setValid(1);
        c.setMinus(new BigDecimal(minus));
        c.setTotal(new BigDecimal(total));
        return couponService.insertSelective(c);
    }

    @ResponseBody
    @PostMapping("/delete")
    public int delete(@RequestParam(value = "id")Integer id) {
        return couponService.deleteByPrimaryKey(id);
    }

    @ResponseBody
    @PostMapping("/update")
    public int update(@RequestParam(value = "id")Integer id,@RequestParam(value = "total")double total) {
        Coupon coupon = new Coupon();
        coupon.setId(id);
        coupon.setTotal(new BigDecimal(total));
        return couponService.updateByPrimaryKeySelective(coupon);
    }

    @ResponseBody
    @PostMapping("/updateValid")
    public int updateValid(@RequestParam(value = "id")Integer id,@RequestParam(value = "valid")int valid) {
        Coupon coupon = new Coupon();
        coupon.setId(id);
        coupon.setValid(valid);
        return couponService.updateByPrimaryKeySelective(coupon);
    }

}
