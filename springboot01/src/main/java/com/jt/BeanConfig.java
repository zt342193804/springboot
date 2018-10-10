package com.jt;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:applicationContext.xml")  //把xml文件导入进来
public class BeanConfig {

}
