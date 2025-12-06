package com.example.lc.config;


import com.example.lc.service.MyListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListnerBeanConfig {
    @Bean
    public MyListener myListener() {
        return new MyListener();
    }
}

