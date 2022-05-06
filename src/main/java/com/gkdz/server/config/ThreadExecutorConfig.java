package com.gkdz.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadExecutorConfig {

    @Bean("asyncExecutor")
    public Executor getAsyncExecutorService() {
        //        return Executors.newFixedThreadPool(50);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 设置核心线程数
        executor.setCorePoolSize(10);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 设置缓冲队列大小
        executor.setQueueCapacity(10);
        // 设置线程的最大空闲时间
        executor.setKeepAliveSeconds(10);
        // 设置线程名字的前缀
        executor.setThreadNamePrefix("cart-thread-");
        // 设置拒绝策略：当线程池达到最大线程数时，如何处理新任务
        // CALLER_RUNS：在添加到线程池失败时会由主线程自己来执行这个任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 线程池初始化
        executor.initialize();

        return executor;

    }

}
