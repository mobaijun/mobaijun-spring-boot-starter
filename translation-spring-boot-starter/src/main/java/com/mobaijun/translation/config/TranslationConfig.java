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
package com.mobaijun.translation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobaijun.translation.annotation.TranslationType;
import com.mobaijun.translation.handler.TranslationBeanSerializerModifier;
import com.mobaijun.translation.handler.TranslationHandler;
import com.mobaijun.translation.service.TranslationInterface;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Description: [翻译配置类，用于管理翻译实现类及其相关设置。]
 * Author: [mobaijun]
 * Date: [2024/9/28 8:30]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Configuration
public class TranslationConfig {

    private static final Logger log = LoggerFactory.getLogger(TranslationConfig.class);

    /**
     * 翻译实现类集合
     */
    private final List<TranslationInterface<?>> translationList;

    /**
     * Jackson 的 ObjectMapper 对象
     */
    private final ObjectMapper objectMapper;

    /**
     * 构造器注入，确保在对象创建时注入依赖
     *
     * @param translationList 实现接口集合
     * @param objectMapper    ObjectMapper 对象
     */
    @Autowired
    public TranslationConfig(List<TranslationInterface<?>> translationList, ObjectMapper objectMapper) {
        this.translationList = translationList;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        Map<String, TranslationInterface<?>> translationMap = new ConcurrentHashMap<>(translationList.size());

        for (TranslationInterface<?> translation : translationList) {
            TranslationType annotation = translation.getClass().getAnnotation(TranslationType.class);
            if (annotation != null) {
                String type = annotation.type();
                if (type != null && !type.isEmpty()) {
                    TranslationInterface<?> existing = translationMap.putIfAbsent(type, translation);
                    if (existing != null) {
                        log.warn("翻译类型 '{}' 存在多个实现类，已使用: {}，忽略: {}", 
                                type, existing.getClass().getName(), translation.getClass().getName());
                    } else {
                        log.debug("注册翻译实现类: {} -> {}", type, translation.getClass().getName());
                    }
                } else {
                    log.warn("翻译实现类 {} 的 TranslationType 注解 type 为空!", translation.getClass().getName());
                }
            } else {
                log.warn("翻译实现类 {} 未标注 TranslationType 注解!", translation.getClass().getName());
            }
        }
        
        TranslationHandler.TRANSLATION_MAPPER.putAll(translationMap);
        log.info("翻译组件初始化完成，共注册 {} 个翻译实现类", translationMap.size());

        // 设置 Bean 序列化修改器
        objectMapper.setSerializerFactory(
                objectMapper.getSerializerFactory()
                        .withSerializerModifier(new TranslationBeanSerializerModifier()));
    }
}
