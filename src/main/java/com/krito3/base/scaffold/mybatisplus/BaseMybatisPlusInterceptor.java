package com.krito3.base.scaffold.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created by macro on 2019/4/8.
 */
@Configuration
public class BaseMybatisPlusInterceptor {

    /**
     * 分页插件
     */
    @Bean
    public com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor paginationInterceptor() {
        com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor interceptor = new com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * sql 日志
     *
     * @return SqlLogInterceptor
     */
    @Bean
    public SqlLogInterceptor sqlLogInterceptor() {
        return new SqlLogInterceptor();
    }

    /**
     * 防止全表删除或者全表更新
     * 全表删除或者更新 会抛出 com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of table update operation
     *
     * @return
     */
    @Bean
    public com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor blockAttackInnerInterceptor() {
        com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor interceptor = new com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }


    /**
     * 乐观锁
     *
     * @return
     */
    @Bean
    public com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor mybatisPlusInterceptor() {
        com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor interceptor = new com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

}
