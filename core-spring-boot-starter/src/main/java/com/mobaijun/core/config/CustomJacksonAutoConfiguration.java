package com.mobaijun.core.config;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mobaijun.core.jackson.CustomJavaTimeModule;
import com.mobaijun.core.jackson.JacksonJsonToolAdapter;
import com.mobaijun.core.jackson.NullSerializerProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: CustomJacksonAutoConfiguration
 * class description：自定义 jackson 配置
 *
 * @author MoBaiJun 2022/7/5 9:27
 */
@Configuration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class CustomJacksonAutoConfiguration {

    @Bean
    @ConditionalOnClass(ObjectMapper.class)
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        // org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.JacksonObjectMapperConfiguration
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 对于空对象的序列化不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 序列化时忽略未知属性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // NULL值修改
        objectMapper.setSerializerProvider(new NullSerializerProvider());
        // 有特殊需要转义字符, 不报错
        objectMapper.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());
        // 更新 JsonUtils 中的 ObjectMapper，保持容器和工具类中的 ObjectMapper 对象一致
        JacksonJsonToolAdapter.setMapper(objectMapper);
        return objectMapper;
    }

    /**
     * 注册自定义 的 jackson 时间格式，高优先级，用于覆盖默认的时间格式
     *
     * @return CustomJavaTimeModule
     */
    @Bean
    @ConditionalOnMissingBean(CustomJavaTimeModule.class)
    public CustomJavaTimeModule customJavaTimeModule() {
        return new CustomJavaTimeModule();
    }
}
