package com.mobaijun.core.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.util.function.Consumer;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: JacksonJsonToolAdapter
 * class description：
 *
 * @author MoBaiJun 2022/7/5 9:32
 */
public class JacksonJsonToolAdapter implements JsonTool {

    static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public void config(Consumer<ObjectMapper> consumer) {
        consumer.accept(mapper);
    }

    @Override
    public String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    @Override
    public <T> T toObj(String json, Class<T> r) throws JsonProcessingException {
        return mapper.readValue(json, r);
    }

    @Override
    public <T> T toObj(String json, Type t) throws JsonProcessingException {
        return mapper.readValue(json, mapper.constructType(t));
    }

    @Override
    public <T> T toObj(String json, TypeReference<T> t) throws JsonProcessingException {
        return mapper.readValue(json, new TypeReference<T>() {
            @Override
            public Type getType() {
                return t.getType();
            }
        });
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static void setMapper(ObjectMapper mapper) {
        JacksonJsonToolAdapter.mapper = mapper;
    }
}
