package com.example.big_event.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration
public class Timeout {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(180000)) // 连接超时时间  3min
                .setReadTimeout(Duration.ofSeconds(180000))    // 读取超时时间 3min
                .build();
    }
}