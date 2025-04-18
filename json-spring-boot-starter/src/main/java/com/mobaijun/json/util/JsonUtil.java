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
package com.mobaijun.json.util;

import cn.hutool.core.lang.Dict;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.mobaijun.core.spring.SpringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;


/**
 * Description: [JSON 工具类]
 * Author: [mobaijun]
 * Date: [2024/7/25 10:17]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

    /**
     * ObjectMapper
     */
    private static final ObjectMapper OBJECT_MAPPER = SpringUtil.getBean(ObjectMapper.class);

    /**
     * 获取ObjectMapper
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * 转换对象为JSON字符串
     *
     * @param entity 对象
     * @param <T>    泛型
     * @return 字符串
     */
    public static <T> String toStr(T entity) {
        return toJsonString(entity);
    }

    /**
     * 转换json字符串为对象
     *
     * @param json  字符串
     * @param clazz 对象类型
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        return parseObject(json, clazz);
    }

    /**
     * 将JSON格式的字符串转换为指定类型的对象，支持复杂类型
     *
     * @param json JSON格式的字符串
     * @param type 指定类型的TypeReference对象
     * @param <T>  目标对象的泛型类型
     * @return 转换后的对象，如果字符串为空则返回null
     * @throws RuntimeException 如果转换过程中发生IO异常，则抛出运行时异常
     */
    public static <T> List<T> toBean(String json, TypeReference<List<T>> type) {
        return parseObject(json, type);
    }

    /**
     * 转换JSON字符串为List对象
     *
     * @param json  JSON字符串
     * @param clazz 对象类型
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        return parseArray(json, clazz);
    }

    /**
     * 转换JSON字符串为Dict对象
     *
     * @param json JSON字符串
     * @return Dict对象
     */
    public static Dict toDict(String json) {
        return parseMap(json);
    }

    /**
     * 将JSON格式的字符串转换为Dict对象的列表
     *
     * @param text JSON格式的字符串
     * @return 转换后的Dict对象的列表，如果字符串为空则返回null
     * @throws RuntimeException 如果转换过程中发生IO异常，则抛出运行时异常
     */
    public static List<Dict> toDictList(String text) {
        return parseArrayMap(text);
    }

    /**
     * 将对象转换为JSON格式的字符串
     *
     * @param object 要转换的对象
     * @return JSON格式的字符串，如果对象为null，则返回null
     * @throws RuntimeException 如果转换过程中发生JSON处理异常，则抛出运行时异常
     */
    public static String toJsonString(Object object) {
        if (ObjectUtils.isEmpty(object)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON格式的字符串转换为指定类型的对象
     *
     * @param text  JSON格式的字符串
     * @param clazz 要转换的目标对象类型
     * @param <T>   目标对象的泛型类型
     * @return 转换后的对象，如果字符串为空则返回null
     * @throws RuntimeException 如果转换过程中发生IO异常，则抛出运行时异常
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        if (text.isBlank()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(text, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字节数组转换为指定类型的对象
     *
     * @param bytes 字节数组
     * @param clazz 要转换的目标对象类型
     * @param <T>   目标对象的泛型类型
     * @return 转换后的对象，如果字节数组为空则返回null
     * @throws RuntimeException 如果转换过程中发生IO异常，则抛出运行时异常
     */
    public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
        if (ObjectUtils.isEmpty(bytes)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON格式的字符串转换为指定类型的对象，支持复杂类型
     *
     * @param text          JSON格式的字符串
     * @param typeReference 指定类型的TypeReference对象
     * @param <T>           目标对象的泛型类型
     * @return 转换后的对象，如果字符串为空则返回null
     * @throws RuntimeException 如果转换过程中发生IO异常，则抛出运行时异常
     */
    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        if (text.isBlank()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(text, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON格式的字符串转换为Dict对象
     *
     * @param text JSON格式的字符串
     * @return 转换后的Dict对象，如果字符串为空或者不是JSON格式则返回null
     * @throws RuntimeException 如果转换过程中发生IO异常，则抛出运行时异常
     */
    public static Dict parseMap(String text) {
        if (text.isBlank()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructType(Dict.class));
        } catch (MismatchedInputException e) {
            // 类型不匹配说明不是json
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON格式的字符串转换为Dict对象的列表
     *
     * @param text JSON格式的字符串
     * @return 转换后的Dict对象的列表，如果字符串为空则返回null
     * @throws RuntimeException 如果转换过程中发生IO异常，则抛出运行时异常
     */
    public static List<Dict> parseArrayMap(String text) {
        if (text.isBlank()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, Dict.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON格式的字符串转换为指定类型对象的列表
     *
     * @param text  JSON格式的字符串
     * @param clazz 要转换的目标对象类型
     * @param <T>   目标对象的泛型类型
     * @return 转换后的对象的列表，如果字符串为空则返回空列表
     * @throws RuntimeException 如果转换过程中发生IO异常，则抛出运行时异常
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        try {
            return OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
