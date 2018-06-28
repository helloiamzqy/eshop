package com.item.eshop.controller;

import com.item.eshop.config.Constant;
import com.item.eshop.model.Coupon;
import com.item.eshop.model.Debt;
import com.item.eshop.model.Msg;
import com.item.eshop.model.User;
import com.item.eshop.service.CouponService;
import com.item.eshop.service.DebtService;
import com.item.eshop.service.UserService;
import com.item.eshop.util.Cache;
import com.item.eshop.util.JsonObject;
import com.item.eshop.util.UploadImage;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "UserController", description = "用户登录，注册，查询，冻结，注销等访问接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    DebtService debtService;

    @Autowired
    CouponService couponService;

    @Autowired
    Constant constant;

    private String[] msg_content = {};

    @ApiOperation(value="手机发送验证码",notes="返回值(对象Msg)，1：发送成功，0：发送失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "phone", value = "手机号码", required = true ,dataType = "String",paramType = "query")})
    @PostMapping("/send")
    public Msg sendCodeAndSave(@RequestParam(value = "phone")String phone) {
        int result = userService.sendSms(phone);
       return Msg.getMsg("user",2,result);
    }

    @ApiOperation(value="用户注册",notes="返回值(对象Msg)，1：注册成功，0：注册失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true ,dataType = "Integer",paramType = "query")})
    @PostMapping("/register")
    public Msg register(@RequestParam("phone") String phone,
                        @RequestParam("password") String password,
                        @RequestParam("code") Integer code) {
        User user = new User( );
        user.setPhone(phone);
        user.setPasswd(password);
        int result =userService.register(user,code);
        return Msg.getMsg("user",0,result);
    }

    @ApiOperation(value="用户忘记密码",notes="返回值(对象User)，1：注册成功，0：注册失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true ,dataType = "Integer",paramType = "query")})
    @PostMapping("/forgetPasswd")
    public String forgetPasswd(@RequestParam("phone") String phone,
                        @RequestParam("code") Integer code,HttpSession httpSession) {
        User user = userService.selectByPhone(phone,code);
            if(user!=null) {
                httpSession.setAttribute("phone", phone);
                httpSession.setAttribute("status",user.getStatus());
                httpSession.setAttribute("user_id",user.getId());
                httpSession.setMaxInactiveInterval(2000000);
            }
        return JsonObject.toJson(user);
    }

    @ApiOperation(value="用户更新密码",notes="返回值(对象User)，1：注册成功，0：注册失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "password", value = "手机号", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true ,dataType = "Integer",paramType = "query")})
    @PostMapping("/changePasswd")
    public int changePasswd(@RequestParam("phone") String phone,
                               @RequestParam("code") Integer code,@RequestParam("password") String password,HttpSession httpSession) {
        if(phone==null||code==null)
            return 0;
        User user = userService.selectByPhone(phone,code);
        if(user!=null)
            user.setPasswd(password);
        return userService.updateUser(user);
    }

    @ApiOperation(value="用户验证",notes="返回值(对象Msg)，1：注册成功，0：注册失败")
//    @ApiImplicitParams({@ApiImplicitParam(name = "identity_card", value = "身份证号码", required = true ,dataType = "String",paramType = "query"),
//            @ApiImplicitParam(name = "school", value = "学校", required = true ,dataType = "String",paramType = "query")})
    @Transactional
    @PostMapping(value = "/verify")
    public Msg verify(@ApiParam(value = "身份证照片",required = true) @RequestParam("id_photo") MultipartFile id_photo,
                      @ApiParam(value = "学生证照片",required = true) @RequestParam("stu_photo") MultipartFile stu_photo,
                      @ApiParam(value = "身份证号码",required = true) @RequestParam("identity_card") String identity_card,
                      @ApiParam(value = "学校",required = true) @RequestParam("school") String school,HttpSession httpSession,HttpServletResponse response) {
        int id = (int) httpSession.getAttribute("user_id");
        int status = (int) httpSession.getAttribute("status");
        if(status==-1)
            return null;
        Debt debt = debtService.selectByPrimaryKey(id);
        Coupon coupon = couponService.selectByPrimaryKey(0);
        if(coupon==null||coupon.getValid()==0) {
            return new Msg(-1,"官方未启用白条!");
        }
        if(debt!=null){     //
            if(debt.getValid()!=0)
                return Msg.getMsg("user",4,0);
            else {
                debt.setValid(1);
                debt.setMax_limit(coupon.getTotal());
                debtService.updateByPrimaryKeySelective(debt);
            }
        }
        else{
            debt = new Debt();
            debt.setId(id);
            debt.setMax_limit(coupon.getTotal());
            debtService.insertSelective(debt);
        }
        String url = new UploadImage().uploadImage(id_photo,1,constant.getPath(),constant.getUrl());
        String url2 = new UploadImage().uploadImage(stu_photo,1,constant.getPath(),constant.getUrl());
        User user = new User();
        user.setId(id);
        user.setPhoto(url+";"+url2);
        user.setIdentityCard(identity_card);
        user.setSchool(school);
        int result = userService.updateUser(user);
        return Msg.getMsg("user",4,result);
    }

    @ApiOperation(value="账号登录",notes="返回值(User对象)或null值")
    @ApiImplicitParams({@ApiImplicitParam(name = "phone", value = "手机号码", required = true ,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true ,dataType = "String",paramType = "query")})
    @PostMapping("/login")
    public User login(@RequestParam(value = "phone")String phone, @RequestParam(value = "password")String password, HttpSession httpSession) {
        User user = userService.login(phone,password);
        if(user!=null){
            httpSession.setAttribute("status",user.getStatus());
            httpSession.setAttribute("phone",phone);
            httpSession.setAttribute("user_id",user.getId());
            httpSession.setMaxInactiveInterval(2000000);
        }
        return user;
    }

    @ApiOperation(value="账号退出/注销",notes="返回空")
    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("phone");
        httpSession.removeAttribute("status");
        httpSession.removeAttribute("user_id");
        return "ok";
    }

    @ApiOperation(value="修改个人信息",notes="返回int值：1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "User", value = "用户对象实例", required = true ,dataType = "String",paramType = "body")})
    @PostMapping("/update")
    public int updateUser(@RequestParam(value = "passwd")String passwd,HttpSession httpSession) {
        int id = (int) httpSession.getAttribute("user_id");
        User u = new User();
        u.setId(id);
        u.setPasswd(passwd);
        return userService.updateUser(u);
    }

    @ApiOperation(value="根据id值获取用户信息",notes="返回值(User对象)或null值")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id值", required = true ,dataType = "Integer",paramType = "query")})
    @PostMapping("/select")
    public User selectUser(@RequestParam(value = "id")Integer id) {
        return userService.selectUser(id);
    }

    // ======================  admin background management system interface ( user interceptor) =============================

    @ApiOperation(value="（后台接口）根据id值删除用户信息",notes="返回int值：1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "phone", value = "用户账号", required = true ,dataType = "String",paramType = "query")})
    @PostMapping("/findByPhone")
    public String findUser(@RequestParam(value = "phone")String phone) {
        return JsonObject.toJson(userService.findByPhone(phone));
    }

    @ApiOperation(value="（后台接口）根据id值修改用户认证状态",notes="返回int值：1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id值", required = true ,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "status", value = "用户账号状态", required = true ,dataType = "Integer",paramType = "query")})
    @PostMapping("/checkup")
    public int checkupUser(@RequestParam(value = "id")Integer id, @RequestParam(value = "status")Integer status) {
        return userService.checkupUser(id,status);
    }

    @ApiOperation(value="（后台接口）获取多个用户的信息",notes="返回值(User对象)数组或null值")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", required = true ,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "num", value = "单页显示个数", required = true ,dataType = "Integer",paramType = "query")})
    @PostMapping("/selectMore")
    public List<User> selectMore(@RequestParam(value = "page")Integer page, @RequestParam(value = "num")Integer num) {
        return userService.selectMore(page,num);
    }

    @ApiOperation(value="（后台接口）获取多个用户的信息",notes="返回值(User对象)数组或null值")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", required = true ,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "num", value = "单页显示个数", required = true ,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "status", value = "显示用户类型", required = true ,dataType = "Integer",paramType = "query")})
    @PostMapping("/selectMoreByStatus")
    public List<User> selectMoreByStatus(@RequestParam(value = "page")Integer page, @RequestParam(value = "num")Integer num, @RequestParam(value = "status")Integer status) {
        return userService.selectMoreByStatus(page,num,status);
    }

    @ApiOperation(value="上线人数+1")
    @ApiImplicitParams({})
    @PostMapping("/online")
    public void online(HttpSession httpSession) {
        if(httpSession.getAttribute("user_id")!=null) {
            Cache.incrRedis("onlineNum");
        }
    }

    @ApiOperation(value="上线人数-1")
    @ApiImplicitParams({})
    @PostMapping("/offline")
    public void offline(HttpSession httpSession) {
        if(httpSession.getAttribute("user_id")!=null) {
            Cache.decrRedis("onlineNum");
        }
    }

    @ApiOperation(value="根据id值获取用户个人信息")
    @ApiImplicitParams({})
    @GetMapping("/get/{id}")
    public String getById(@PathVariable(value = "id")int id) {
        User user = userService.selectUser(id);
        Debt debt = debtService.selectByPrimaryKey(id);

        String[] school = user.getSchool().split("@");
        String[] photo = user.getPhoto().split(";");
        Map<String,String> user_detail = new HashMap<>();
        user_detail.put("id",user.getId()+"");
        user_detail.put("phone",user.getPhone());
        user_detail.put("identity_card",user.getIdentityCard());
        user_detail.put("status",user.getStatus()+"");
        if(photo.length>1) {
            user_detail.put("stu_img", photo[1]);
            user_detail.put("idc_img", photo[0]);
        }
        if(school.length>1) {
            user_detail.put("school", school[0]);
            user_detail.put("name", school[1]);
        }
        if(debt!=null){
            user_detail.put("max_limit",debt.getMax_limit()+"");
            user_detail.put("cost",debt.getCost()+"");
            user_detail.put("valid",debt.getValid()+"");
        }else{
            user_detail.put("max_limit","0");
            user_detail.put("cost","0");
            user_detail.put("valid","未申请开通白条");
        }
        return JsonObject.toJson(user_detail);
    }

    @ApiOperation(value="管理员修改用户状态",notes="返回int值：1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "status", value = "用户状态数值", required = true ,dataType = "Integer")})
    @PostMapping("/updateStatus")
    public int updateStatus(@RequestParam(value = "status")Integer status,@RequestParam(value = "user_id")Integer user_id) {
        User u = new User();
        u.setId(user_id);
        u.setStatus(status);
        return userService.updateUser(u);
    }

}
