package com.krito3.base.scaffold;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.krito3.base.scaffold.mapper"})
public class BaseScaffoldApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseScaffoldApplication.class, args);
    }

}
