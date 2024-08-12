package com.mobaijun.core.config;

import com.mobaijun.core.exception.ServiceException;
import com.mobaijun.core.spring.SpringUtil;
import java.util.Arrays;
import java.util.concurrent.Executor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;

/**
 * Description: [异步配置]
 * Author: [mobaijun]
 * Date: [2024/7/30 10:10]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(AsyncConfig.class);

    /**
     * 自定义 @Async 注解使用系统线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        if (SpringUtil.isVirtual()) {
            return new VirtualThreadTaskExecutor("async-");
        }
        return SpringUtil.getBean("scheduledExecutorService");
    }

    /**
     * 异步执行异常处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("Async exception message - " + throwable.getMessage());
            StringBuilder sb = new StringBuilder();
            sb.append("Exception message - ").append(throwable.getMessage())
                    .append(", Method name - ").append(method.getName());
            if (!Arrays.stream(objects).toList().isEmpty()) {
                sb.append(", Parameter value - ").append(Arrays.toString(objects));
            }
            throw new ServiceException(sb.toString());
        };
    }
}
