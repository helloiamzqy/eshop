package com.item.eshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileConfig {
    @Autowired
    Constant constant;

    @Bean
    public MultipartConfigElement multipartConfigElement() {    //问卷上传配置
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
        // 这样在文件上传的地方就需要进行异常信息的处理了;
        factory.setMaxFileSize("10MB"); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("10MB");
        // Sets the directory location where files will be stored.
        factory.setLocation(constant.getPath()+"/tmp");
        return factory.createMultipartConfig();
    }
}
