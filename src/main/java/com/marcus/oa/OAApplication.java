package com.marcus.oa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.marcus.oa.mapper")
public class OAApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAApplication.class,args);
    }
}
