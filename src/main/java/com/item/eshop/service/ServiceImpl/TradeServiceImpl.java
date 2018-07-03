package com.item.eshop.service.ServiceImpl;

import com.item.eshop.controller.CouponController;
import com.item.eshop.controller.UserController;
import com.item.eshop.mapper.*;
import com.item.eshop.model.*;
import com.item.eshop.service.TradeService;
import com.item.eshop.util.JsonObject;
import com.item.eshop.util.TradeID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

import java.math.BigDecimal;
import java.util.*;

@Service(value = "tradeService")
public class TradeServiceImpl implements TradeService {

    @Autowired
    TradeMapper tradeMapper;

    @Autowired
    GoodMapper goodMapper;

    @Autowired
    ShopcarGoodMapper shopcarGoodMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    UserCouponMapper userCouponMapper;


    @Autowired
    AddressMapper addressMapper;

    @Autowired
    TradeGoodMapper tradeGoodMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return tradeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public String insert(Trade record) {
        //获取用户地址
        Address address = addressMapper.selectByPrimaryKey(record.getAddressId());
        if(address==null)
            return "5";
        String addr = address.getProvince()+""+address.getCity()+""+address.getDistrict()+""+address.getStreet()+"@"+address.getName()+"@"+address.getPhone();
        List<ShopcarGood> shopcarGood = shopcarGoodMapper.selectByUser(record.getUserId());
        if(shopcarGood==null)
            return "2";
        String ids = "";
        for(ShopcarGood sg:shopcarGood){
            ids+= sg.getGoodId() + ",";
        }
        ids+= "-1";
        List<Good> goods = goodMapper.selectBySet(ids);
        if(goods==null){
            return "3";
        }

        String uuid = TradeID.getID();
        TradeGood tradeGood;
        //计算金额
        double price = 0;
        String content = "";
        String image = null;

        //商品风格
        String good_type = "";          // 2018-5-15
        String other = "";              // 2018-5-15
        String sub_content = "("+ good_type.split("@")[Integer.parseInt(other)]+")";    // 2018-5-15

        Map goodCount = new HashMap<>();
        List<Object> goodContent = new ArrayList<>();
        for(ShopcarGood sg:shopcarGood){
            other = sg.getOther();      // 2018-5-15
            for(Good good: goods){
                   if(other!=null) {             // 2018-5-15
                       good_type = good.getContent();   // 2018-5-15
                       sub_content = "("+ good_type.split("@")[Integer.parseInt(other)]+")"; // 2018-5-15
                   }
                if(sg.getGoodId()==good.getId()){
                    price += good.getPrice().doubleValue()*sg.getCount();
                    content += good.getName()+sub_content+":"+good.getPrice()+"元/"+sg.getCount()+"个 ; ";        // 2018-5-15
                    if(image==null){
                        image = good.getImage();
                    }
                    goodCount.put("good",good);
                    goodCount.put("count",sg.getCount());
                    goodContent.add(goodCount);
                    goodCount = new HashMap<>();
                }
                sub_content = ""; // 2018-5-15
            }
            tradeGood = new TradeGood();
            tradeGood.setCount(sg.getCount());
            tradeGood.setGoodId(sg.getGoodId());
            tradeGood.setTradeId(uuid);
            tradeGood.setOther(other);
            if(tradeGoodMapper.insertSelective(tradeGood)==0){
                return "4";
            }
        }
        if(content!=null&&content.length()>3) {
            content = content.substring(0, content.length() - 2);
        }
        if(shopcarGoodMapper.deleteByUser(record.getUserId())==0){
            return "4";
        }

        Trade trade = record;
        double fact_price = price;
        int CouponId = 0;
        String rc = record.getCouponId();
        if(rc!=null){
            CouponId = Integer.parseInt(rc);
        }
        if(CouponId!=0) {
            UserCoupon userCoupon = userCouponMapper.selectByUserAndCoupon(record.getUserId(),CouponId);
            if (userCoupon != null&&userCoupon.getDeadline().getTime()>Calendar.getInstance().getTimeInMillis()&&userCoupon.getValid()==1) {
                Coupon uc = couponMapper.selectByPrimaryKey(CouponId
                );
                if(uc!=null&&price>=uc.getTotal().doubleValue()){
                    int result = userCouponMapper.useCoupon(CouponId,record.getUserId());
                    if(result==1)
                    {
                        fact_price = price - uc.getMinus().doubleValue();
                        trade.setCouponId("满 " + uc.getTotal().doubleValue() + " 减 " + uc.getMinus().doubleValue());
                    }
                }
            }
        }
        trade.setId(uuid);
        trade.setStarttime(new Date());
        trade.setAmount(new BigDecimal(price));
        trade.setFactAmount(new BigDecimal(fact_price));
        trade.setAddress(addr);
        trade.setContent(content);
        trade.setImage(image);
        trade.setTradeGood(JsonObject.toJson(goodContent));
        if(tradeMapper.insertSelective(record)==0){
            return "0";
        }
        return uuid;
    }

    @Override
    public int insertSelective(Trade record) {
        return tradeMapper.insertSelective(record);
    }

    @Override
    public Trade selectByPrimaryKey(String id) {
        return tradeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Trade record) {
        return tradeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Trade record) {
        return tradeMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Trade> selectByUser(Integer user_id, Integer page, Integer num) {
        return tradeMapper.selectByUser(user_id,page,num);
    }

    @Override
    public List<Trade> selectByStatus(Integer shopId,Integer status, Integer page, Integer num) {
        return tradeMapper.selectByStatus(shopId,status,page,num);
    }

    @Override
    public List<Trade> selectMore(Integer shopId,Integer page, Integer num) {
        return tradeMapper.selectMore(shopId,page,num);
    }

    @Override
    public List<Trade> selectByUserAndStatus(Integer user_id, Integer status, Integer page, Integer num) {
        List<Trade> trades = tradeMapper.selectByUserAndStatus(user_id, status, page, num);
        return trades;
    }

    @Override
    public Trade selectByIdAndUserId(String id, Integer user_id) {
        Trade trade = tradeMapper.selectByIdAndUserId(id,user_id);
        return trade;
    }

    @Override
    public List<Trade> selectBySet(Integer shopId,Integer user_id,String status) {
        return tradeMapper.selectBySet(shopId,user_id,status);
    }

    @Override
    public List<Trade> selectBySets(Integer shopId,String status, Integer page, Integer num) {
        return tradeMapper.selectBySets(shopId,status, page, num);
    }

    @Override
    public int updateByIdAndStatusOne(String tradeId) {
        return tradeMapper.updateByIdAndStatusOne(tradeId);
    }
}
