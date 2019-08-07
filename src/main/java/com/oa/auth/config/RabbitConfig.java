package com.oa.auth.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 配置队列
 * @author: kk
 * @create: 2019-01-07
 **/
@Configuration
public class RabbitConfig {
    @Bean
    public Queue EmpQueue() {
        return new Queue("employeeInfo.auth");
    }
}
