package com.mobaijun.sensitive.handler;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.mobaijun.core.spring.SpringUtil;
import com.mobaijun.sensitive.annotation.Sensitive;
import com.mobaijun.sensitive.core.SensitiveService;
import com.mobaijun.sensitive.core.SensitiveStrategy;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;

/**
 * Description: [自定义序列化器，用于根据角色和权限处理敏感数据
 * 此序列化器在将数据写入JSON之前检查数据是否需要脱敏。]
 * Author: [mobaijun]
 * Date: [2024/7/30 9:49]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public class SensitiveHandler extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏策略，用于指定如何对敏感数据进行脱敏。
     */
    private SensitiveStrategy strategy;

    /**
     * 角色键，用于指定哪个角色可以查看或处理敏感数据。
     */
    private String roleKey;

    /**
     * 权限，用于指定需要什么权限来查看或处理敏感数据。
     */
    private String perms;

    /**
     * 序列化给定的值，在写入之前检查是否需要脱敏。
     *
     * @param value       要序列化的值。
     * @param gen         JSON生成器。
     * @param serializers 序列化提供者。
     * @throws IOException 如果发生输入或输出异常。
     */
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            SensitiveService sensitiveService = SpringUtil.getBean(SensitiveService.class);
            if (ObjectUtil.isNotNull(sensitiveService) && sensitiveService.isSensitive(roleKey, perms)) {
                gen.writeString(strategy.desensitize().apply(value));
            } else {
                gen.writeString(value);
            }
        } catch (BeansException e) {
            log.error("脱敏实现不存在, 采用默认处理 => {}", e.getMessage());
            gen.writeString(value);
        }
    }

    /**
     * 根据属性注解创建上下文序列化器。
     *
     * @param prov     序列化提供者。
     * @param property Bean属性。
     * @return 上下文序列化器或默认序列化器。
     * @throws JsonMappingException 如果映射时发生错误。
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Sensitive annotation = property.getAnnotation(Sensitive.class);
        if (Objects.nonNull(annotation) && Objects.equals(String.class, property.getType().getRawClass())) {
            this.strategy = annotation.strategy();
            this.roleKey = annotation.roleKey();
            this.perms = annotation.perms();
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
