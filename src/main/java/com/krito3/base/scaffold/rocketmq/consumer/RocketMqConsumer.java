package com.krito3.base.scaffold.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
 * selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "group_base", topic = "topic_base", selectorExpression = "*")
public class RocketMqConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("监听到消息：message:{}", message);
    }
}