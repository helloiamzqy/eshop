package com.item.eshop;

import com.item.eshop.config.Constant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableConfigurationProperties(Constant.class)
@MapperScan("com.item.eshop.mapper")
public class EshopApplication {//extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(EshopApplication.class, args);
	}
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(EshopApplication.class);
//	}
}
