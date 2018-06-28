package com.item.eshop.controller;


import com.item.eshop.config.Constant;
import com.item.eshop.mapper.ReportMapper;
import com.item.eshop.model.Status;
import com.item.eshop.util.Cache;
import com.item.eshop.util.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "NormalController", description = "报表访问接口")
@Controller
public class NormalController {

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    Constant constant;

    @GetMapping("/")
    public String index(){
        String path=Thread.currentThread().getContextClassLoader().getResource("").toString();
        System.out.println(path+",,,,,"+constant.getPath());
        return "/login";
    }

    @GetMapping("/html/product")
    public String toProduct(){
        return "Products_List.html";
    }

    @ApiOperation(value="用户相关人数",notes="返回int值")
    @ApiImplicitParams({})


    @ResponseBody
    @GetMapping("/report")
    public String report() {
        Map<String, Integer> report = new HashMap<String, Integer>();

        // ----------------------------------- 用户 报表-----------------------------------------
        report.put("userNum", reportMapper.countUser());        //用户数量
        report.put("amountSum",(int)(reportMapper.countAmountSum()*100));

//        String cache = Cache.getRedis("onlineNum"); //在线用户数量
        int online = 0;
//        if (cache == null) {
//            online = 1;
//        } else {
//            online = Integer.parseInt(cache);
//        }
        report.put("userOnlineNum", online);  //在线数量

        List<Status> result = reportMapper.countUserByStatus();
        int num = 0;
        if (result!=null) {
            for (Status s : result) {
                num = s.getNum();
                switch (s.getStatus()) {
                    case 0:
                        report.put("userFailNum", num);      //审核不通过白条用户数量  0
                        break;
                    case 1:
                        report.put("userNormalNum", num);    //未审核用户数量  1
                        break;
                    case 2:
                        report.put("userPassNum", num);      //审核通过用户数量  2
                        break;
                    case 3:
                        report.put("userReviewNum", num);      //待还款用户数量  3
                        break;
                    case 4:
                        report.put("userExceedTimeNum", num);      //逾期未还款用户数量  4
                        break;
                }
            }
        }

        // ------------------------------------  订单 报表 ---------------------------------------------
        num = 0;
        result = reportMapper.countOrderByStatus();
        report.put("orderNum", reportMapper.countOrder());           //订单总数量
        if(result!=null) {
            for (Status s : result) {
                num = s.getNum();
                switch (s.getStatus()) {
                    case 0:
                        report.put("orderFailNum", num);            //失败订单 0
                        break;
                    case 1:
                        report.put("orderUnpaidNum", num);       //待付费订单数量 1
                        break;
                    case 2:
                        report.put("orderPaidNum", num);        //(已付费)待发货订单  2
                        break;
                    case 3:
                        report.put("orderSendNum", num);           //已发货（处理）订单 3
                        break;
                    case 4:
                        report.put("orderSuccessNum", num);            //已成交订单  4
                        break;
                    // ------------------------------------  订单退货 报表 ---------------------------------------------
                    case 5:
                        report.put("orderCancelNum", num);           //待退货订单  （失败订单）5
                        break;
                    case 6:
                        report.put("orderTreatedNum", num);           //已处理待退货订单    6
                        break;
                    case 7:
                        report.put("CancelSuccessNum", num);           //退货成功订单  7
                        break;
                    case 8:
                        report.put("CancelFailNum", num);           //退货失败订单  8
                        break;
                }
            }
        }

        //-----------------------------------  商品 报表 ------------------------------------
        report.put("goodNum", reportMapper.countGood());           //商品数  （失败订单）
        result = reportMapper.countGoodByStatus();
        num = 0;
        if(result!=null) {
            for (Status s : result) {
                num = s.getNum();
                switch (s.getStatus()) {
                    case 0:
                        report.put("goodDeleteNum", num);           //商品回收站数量   0
                        break;
                    case 1:
                        report.put("goodOnNum", num);           //上架商品数量   1
                        break;
                    case 2:
                        report.put("goodOffNum", num);           //下架商品数量    2
                        break;
                }
            }
        }

        return JsonObject.toJson(report);
    }

}
