package com.wisesoft.wzl.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WzlConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(WzlConfigApplication.class, args);
    }

}
