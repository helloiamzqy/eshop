package com.item.eshop.controller;

import com.item.eshop.model.TradeGood;
import com.item.eshop.service.TradeGoodService;
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

@Api(value = "TradeGoodController", description = "订单物品增删改查等访问接口")
@Controller
@RequestMapping("/tradeGood")
public class TradeGoodController {



    @Autowired
    TradeGoodService tradeGoodService;

    @ApiOperation(value="添加某订单的商品",notes="返回值（int):  1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "tradeGood", value = "添加商品到订单中", required = true ,dataType = "Object")})
    @ResponseBody
    @PostMapping("/add")
    public int add(@RequestParam(value = "tradeGood")TradeGood tradeGood) {
        //根据tradeId添加商品到订单中。
        return tradeGoodService.insert(tradeGood);
    }

    @ApiOperation(value="删除某订单中的商品",notes="返回值（int):  1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "要删除的订单中的商品id", required = true ,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "trade_id", value = "要删除的商品的订单号", required = true ,dataType = "String",paramType = "query")})
    @ResponseBody
    @PostMapping("/delete")
    public int delete(@RequestParam(value = "id")Integer id,@RequestParam(value = "trade_id")String trade_id) {
        return tradeGoodService.deleteByPrimaryKey(id);
    }

    @ApiOperation(value="更新某订单中的商品数量",notes="返回值（int):  1:成功，0：失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "tradeGood", value = "更新订单商品", required = true ,dataType = "Object",paramType = "query")})
    @ResponseBody
    @PostMapping("/update")
    public int update(@RequestParam(value = "tradeGood")TradeGood tradeGood) {
        return tradeGoodService.updateByPrimaryKeySelective(tradeGood);
    }

    // add :  chan  2018/4/4
    @ApiOperation(value="根据用户查找某订单中的所有商品及数量",notes="返回值（TradeGood对象)数组或null")
    @ApiImplicitParams({@ApiImplicitParam(name = "trade_id", value = "要查询的订单号", required = true ,dataType = "String",paramType = "query")})
    @ResponseBody
    @PostMapping("/findByTrade")
    public String findByUser(@RequestParam(value = "trade_id")String trade_id) {
        return JsonObject.toJson(tradeGoodService.selectByTrade(trade_id));
    }

    // ======================  admin background management system interface ( user interceptor) =============================

    @ResponseBody
    @PostMapping("/find")
    public String find(@RequestParam(value = "id")Integer id) {
        return JsonObject.toJson(tradeGoodService.selectByPrimaryKey(id));
    }


}
