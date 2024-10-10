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
package com.mobaijun.translation.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.mobaijun.core.util.reflect.ReflectUtil;
import com.mobaijun.translation.annotation.Translation;
import com.mobaijun.translation.service.TranslationInterface;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: [自定义翻译处理器，负责将带有 {@link Translation} 注解的字段进行翻译。]
 * Author: [mobaijun]
 * Date: [2024/9/28 8:31]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@JsonSerialize(using = TranslationHandler.class)
public class TranslationHandler extends JsonSerializer<Object> implements ContextualSerializer {

    /**
     * 全局翻译实现类映射器
     */
    public static final Map<String, TranslationInterface<?>> TRANSLATION_MAPPER = new ConcurrentHashMap<>();

    /**
     * 翻译注解实例
     */
    private Translation translation;

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        TranslationInterface<?> translator = TRANSLATION_MAPPER.get(translation.type());

        if (translator != null) {
            // 如果映射字段不为空，则取映射字段的值
            if (!translation.mapper().isEmpty()) {
                value = ReflectUtil.invokeGetter(gen.currentValue(), translation.mapper());
            }

            // 如果值为 null，直接写出 null
            if (value == null) {
                gen.writeNull();
                return;
            }

            // 执行翻译并写出结果
            Object result = translator.translation(value, translation.other());
            gen.writeObject(result);
        } else {
            // 如果找不到对应的翻译实现，直接写出原值
            gen.writeObject(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Translation translationAnnotation = property.getAnnotation(Translation.class);

        if (translationAnnotation != null) {
            this.translation = translationAnnotation;
            return this;
        }

        return prov.findValueSerializer(property.getType(), property);
    }
}
