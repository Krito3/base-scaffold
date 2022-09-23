package com.krito3.base.scaffold.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author kno.ci
 * @description: 异步线程池
 * @date 2022/2/13 下午4:56
 */
@Configuration
@EnableAsync
public class AsyncPoolConfig implements AsyncConfigurer {
    public static final String TASK_EXECUTOR_NAME = "taskExecutor";

    @Override
    @Bean(TASK_EXECUTOR_NAME)
    public AsyncTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数（默认线程数）
        executor.setCorePoolSize(10);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 缓冲队列数
        executor.setQueueCapacity(200);
        // 允许线程空闲时间（单位：默认为秒）
        executor.setKeepAliveSeconds(60);
        // 线程池名前缀
        executor.setThreadNamePrefix("asyncExecutor-");
        // 设置是否等待计划任务在关闭时完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置此执行器应该阻止的最大秒数
        executor.setAwaitTerminationSeconds(60);

        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

}
