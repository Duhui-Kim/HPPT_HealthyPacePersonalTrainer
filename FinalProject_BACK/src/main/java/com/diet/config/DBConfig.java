package com.diet.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@MapperScan(basePackages = "com.diet.model.dao")
public class DBConfig {

}
