package com.wzl.chrome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChromeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChromeApplication.class, args);
    }

}
