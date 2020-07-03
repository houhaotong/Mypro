package com.mypro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.mypro.system.mapper")
@SpringBootApplication
public class MyproAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyproAdminApplication.class, args);
    }

}
