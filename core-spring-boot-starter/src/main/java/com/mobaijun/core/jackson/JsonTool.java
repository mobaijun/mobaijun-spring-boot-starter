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
