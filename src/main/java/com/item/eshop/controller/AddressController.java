package com.item.eshop.controller;

import com.item.eshop.model.Address;
import com.item.eshop.model.Msg;
import com.item.eshop.model.User;
import com.item.eshop.service.AddressService;
import com.item.eshop.util.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Api(value = "AddressController", description = "用户个人收获地址增删改查等访问接口")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @ApiOperation(value="添加收获地址",notes="返回值(int型)，1：添加成功，0：添加失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "address", value = "收获地址", required = true ,dataType = "String",paramType = "query")})
    @PostMapping("/add")
    public int add(@RequestParam(value = "address")String address, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        Address addr = (Address) JsonObject.toObject(address,"address");
        addr.setUserId(user_id);
        return addressService.insert(addr);
    }

    @ApiOperation(value="删除收获地址",notes="返回值(int型)，1：删除成功，0：删除失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "要删除的地址的id值", required = true ,dataType = "Integer",paramType = "query")})
    @PostMapping("/delete")
    public int delete(@RequestParam(value = "id")Integer id,HttpSession httpSession) { //user_id 验证
        int user_id = (int) httpSession.getAttribute("user_id");
        System.out.print(id+",,,"+user_id);
        return addressService.deleteByPrimaryKey(id,user_id);
    }


    @ApiOperation(value="更新收获地址",notes="返回值(int型)，1：更新成功，0：更新失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "address", value = "收获地址", required = true ,dataType = "Object",paramType = "query")})
    @PostMapping("/update")
    public int update(@RequestParam(value = "address")String address,HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        Address addr = (Address) JsonObject.toObject(address,"address");
        addr.setUserId(user_id);
        return addressService.updateByPrimaryKeySelective(addr);
    }

    @ApiOperation(value="根据id值查找收获地址",notes="返回值(Address对象)或NULL值")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "收获地址id值", required = true ,dataType = "Integer",paramType = "query")})
    @PostMapping("/find")
    public String find(@RequestParam(value = "id")Integer id,HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
         return JsonObject.toJson(addressService.selectByPrimaryKey(id));
    }

    @ApiOperation(value="根据用户id值查找收获地址",notes="返回值(Address对象)数组或NULL值")
    @ApiImplicitParams({@ApiImplicitParam(name = "user_id", value = "用户id值", required = true ,dataType = "Integer",paramType = "query")})
    // add :  chan  2018/4/4
    @PostMapping("/findByUser")
    public String findByUser(HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        return JsonObject.toJson(addressService.selectByUserId(user_id));
    }

}
