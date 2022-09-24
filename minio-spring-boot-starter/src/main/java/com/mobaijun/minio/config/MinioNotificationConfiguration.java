package com.mobaijun.minio.config;

import com.mobaijun.minio.annotation.MinioNotification;
import com.mobaijun.minio.prop.MinioConfigurationProperties;
import io.minio.CloseableIterator;
import io.minio.ListenBucketNotificationArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.NotificationRecords;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MinioNotificationConfiguration
 * class description：MinioNotificationConfiguration
 *
 * @author MoBaiJun 2022/9/20 8:57
 */
@Configuration
public class MinioNotificationConfiguration implements ApplicationContextAware {

    private final MinioConfigurationProperties minioConfigurationProperties;
    private final MinioClient minioClient;
    private final List<Thread> THREAD_LIST = new ArrayList<>();

    public MinioNotificationConfiguration(MinioConfigurationProperties minioConfigurationProperties, MinioClient minioClient) {
        this.minioConfigurationProperties = minioConfigurationProperties;
        this.minioClient = minioClient;
    }

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(MinioNotificationConfiguration.class);

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object obj = applicationContext.getBean(beanName);

            Class<?> objClz = obj.getClass();
            if (org.springframework.aop.support.AopUtils.isAopProxy(obj)) {
                objClz = org.springframework.aop.support.AopUtils.getTargetClass(obj);
            }

            for (Method m : objClz.getDeclaredMethods()) {
                if (m.isAnnotationPresent(MinioNotification.class)) {
                    // Check if it has NotificationInfo parameter only
                    if (m.getParameterCount() != 1) {
                        throw new IllegalArgumentException("Minio notification handler should have only one NotificationInfo parameter");
                    }

                    if (m.getParameterTypes()[0] != NotificationRecords.class) {
                        throw new IllegalArgumentException("Parameter should be instance of NotificationRecords");
                    }

                    MinioNotification annotation = m.getAnnotation(MinioNotification.class);

                    // Then registering method handler
                    Thread handler = new Thread(() -> {
                        for (; ; ) {
                            try {
                                log.debug("Registering Minio handler on {} with notification {}", m.getName(), Arrays.toString(annotation.value()));
                                ListenBucketNotificationArgs args = ListenBucketNotificationArgs.builder()
                                        .bucket(minioConfigurationProperties.getBucket())
                                        .prefix(annotation.prefix())
                                        .suffix(annotation.suffix())
                                        .events(annotation.value())
                                        .build();
                                try (CloseableIterator<Result<NotificationRecords>> list = minioClient.listenBucketNotification(args)) {
                                    while (list.hasNext()) {
                                        NotificationRecords info = list.next().get();
                                        try {
                                            log.debug("Receive notification for method {}", m.getName());
                                            m.invoke(obj, info);
                                        } catch (IllegalAccessException | InvocationTargetException e) {
                                            log.error("Error while handling notification for method {} with notification {}", m.getName(), Arrays.toString(annotation.value()));
                                            log.error("Exception is", e);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                log.error("Error while registering notification for method " + m.getName() + " with notification " + Arrays.toString(annotation.value()), e);
                                throw new IllegalStateException("Cannot register handler", e);
                            }
                        }
                    });
                    handler.start();
                    THREAD_LIST.add(handler);
                }
            }
        }
    }
}
