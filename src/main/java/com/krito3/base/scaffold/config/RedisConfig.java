package com.krito3.base.scaffold.config;

import com.krito3.base.scaffold.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Redis配置类
 * Created by macro on 2020/3/2.
 */
@EnableCaching
public class RedisConfig extends BaseRedisConfig {

}
