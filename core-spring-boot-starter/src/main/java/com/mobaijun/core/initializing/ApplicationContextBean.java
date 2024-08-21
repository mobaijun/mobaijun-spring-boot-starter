/*
 * Copyright (C) 2022 [www.mobaijun.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobaijun.core.initializing;

import com.mobaijun.core.properties.ApplicationProperties;
import io.micrometer.common.lang.NonNullApi;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Description: [打印所有bean容器名称]
 * Author: [mobaijun]
 * Date: [2024/8/16 15:22]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 *
 * @param applicationProperties 项目配置文件
 */
@Slf4j
@NonNullApi
public record ApplicationContextBean(
        ApplicationProperties applicationProperties) implements ApplicationContextAware, InitializingBean {

    /**
     * 获取applicationContext
     */
    public static ApplicationContext applicationContext;

    /**
     * 获取 ApplicationContext
     *
     * @param applicationContext 上下文
     * @throws org.springframework.beans.BeansException 异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextBean.applicationContext = applicationContext;
    }

    /**
     * 打印IOC容器中所有的Bean名称
     */
    @Override
    public void afterPropertiesSet() {
        if (applicationProperties.isPrintBean()) {
            String[] beanS = applicationContext.getBeanDefinitionNames();
            Arrays.stream(beanS).forEach(temp -> log.info("Bean 名称:{}", temp));
            log.info("-----------Bean 总计:{}", applicationContext.getBeanDefinitionCount());
        }
    }
}
