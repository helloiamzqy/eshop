package com.item.eshop.config;

import com.item.eshop.interceptor.AdminInterceptor;
import com.item.eshop.interceptor.SecurityInterceptor;
import com.item.eshop.interceptor.SellerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void  addInterceptors(InterceptorRegistry registry){
        InterceptorRegistration addInterceptor = registry.addInterceptor(securityInterceptor());
        addInterceptor.excludePathPatterns("/user/login");
        addInterceptor.excludePathPatterns("/");
        addInterceptor.excludePathPatterns("/product");
        addInterceptor.excludePathPatterns("/user/register");
        addInterceptor.excludePathPatterns("/user/send");
        addInterceptor.excludePathPatterns("/user/forgetPasswd");
        addInterceptor.addPathPatterns("/user/**");
        addInterceptor.addPathPatterns("/address/**");
        addInterceptor.addPathPatterns("/trade/**");
        addInterceptor.addPathPatterns("/debt/**");
        addInterceptor.addPathPatterns("/userCoupon/**");
        addInterceptor.addPathPatterns("/tradeGood/**");
        addInterceptor.addPathPatterns("/shopcar/**");
        addInterceptor.excludePathPatterns("/trade/alipay");
        addInterceptor.excludePathPatterns("/trade/weixinpay");

        InterceptorRegistration adminInterceptor = registry.addInterceptor(adminInterceptor());
       // adminInterceptor.addPathPatterns("/report");
        adminInterceptor.addPathPatterns("/coupon/add","/coupon/delete","/coupon/update");
        adminInterceptor.addPathPatterns("/debt/updateMax","/debt/updateValid","/debt/findById","/debt/findMore","/debt/findByValid");
        adminInterceptor.addPathPatterns("/category/update","/category/add","/category/delete");
       // adminInterceptor.addPathPatterns("/good/updateByRecommend","/good/add","/good/update","/good/updateStatus");
        adminInterceptor.addPathPatterns("/recommend/add","/recommend/update","/recommend/delete");
      //  adminInterceptor.addPathPatterns("/trade/**");
       //adminInterceptor.excludePathPatterns("/trade/confirm","/trade/weixinpay","/trade/alipay","/trade/add","/trade/pay","/trade/findByUser","/trade/findByUserAndStatus","/trade/findByStatusSet","/trade/findById","/trade/cancel","/trade/back");
        adminInterceptor.addPathPatterns("/user/checkup","/user/selectMore","/user/selectMoreByStatus","/user/get/**","/user/updateStatus");
        adminInterceptor.addPathPatterns("/userCoupon/find");

        InterceptorRegistration sellerInterceptor = registry.addInterceptor(sellerInterceptor());
        sellerInterceptor.addPathPatterns("/report");
        sellerInterceptor.addPathPatterns("/good/updateByRecommend","/good/add","/good/update","/good/updateStatus");
        sellerInterceptor.addPathPatterns("/trade/**");
        sellerInterceptor.excludePathPatterns("/trade/confirm","/trade/weixinpay","/trade/alipay","/trade/add","/trade/pay","/trade/findByUser","/trade/findByUserAndStatus","/trade/findByStatusSet","/trade/findById","/trade/cancel","/trade/back");
    }

    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

    @Bean
    public SellerInterceptor sellerInterceptor() {
        return new SellerInterceptor();
    }
}
