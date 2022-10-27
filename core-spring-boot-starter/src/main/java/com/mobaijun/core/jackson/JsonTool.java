/*
 * Copyright (C) 2022 www.mobaijun.com
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
package com.mobaijun.core.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;

/**
 * software：IntelliJ IDEA 2022.1
 * interface name: JsonTool
 * interface description：
 * 接口描述：
 *
 * @author MoBaiJun 2022/7/5 9:32
 */
public interface JsonTool {

    /**
     * obj -> jsonStr
     *
     * @param obj obj
     * @return java.lang.String
     * @throws JsonProcessingException JsonProcessingException
     */
    String toJson(Object obj) throws JsonProcessingException;

    /**
     * jsonStr -> obj
     *
     * @param json json str
     * @param r    obj.class
     * @return T
     * @throws JsonProcessingException JsonProcessingException
     */
    <T> T toObj(String json, Class<T> r) throws JsonProcessingException;

    /**
     * jsonStr -> obj
     *
     * @param json json str
     * @param t    (obj.class)type
     * @return T
     * @throws JsonProcessingException JsonProcessingException
     */
    <T> T toObj(String json, Type t) throws JsonProcessingException;

    /**
     * jsonStr -> obj
     *
     * @param json json str
     * @param t    TypeReference
     * @return T
     * @throws JsonProcessingException JsonProcessingException
     */
    <T> T toObj(String json, TypeReference<T> t) throws JsonProcessingException;
}