package com.item.eshop.config;

import com.item.eshop.interceptor.SecurityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


//@Configuration
public class WebSecurityConfig extends WebMvcConfigurationSupport {

    @Override
    public void  addInterceptors(InterceptorRegistry registry){
        InterceptorRegistration addInterceptor = registry.addInterceptor(new SecurityInterceptor());
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.addPathPatterns("/**");

        InterceptorRegistration adminInterceptor = registry.addInterceptor(new SecurityInterceptor());
        adminInterceptor.excludePathPatterns("/error");
        adminInterceptor.excludePathPatterns("/login**");
        adminInterceptor.addPathPatterns("/**");
    }


//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//
//    }
//
//
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/index");
//    }
//
//
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/static/**")
//                .addResourceLocations("/resources/static/");
//    }
}
