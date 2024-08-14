package com.mobaijun.redisson.handler;

import io.micrometer.common.util.StringUtils;
import org.redisson.api.NameMapper;

/**
 * Description: [redis缓存key前缀处理]
 * Author: [mobaijun]
 * Date: [2024/8/14 18:30]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class KeyPrefixHandler implements NameMapper {

    /**
     * 默认前缀
     */
    private final String keyPrefix;

    /**
     * 构造函数
     *
     * @param keyPrefix 自定义前缀
     */
    public KeyPrefixHandler(String keyPrefix) {
        // 前缀为空 则返回空前缀
        this.keyPrefix = StringUtils.isBlank(keyPrefix) ? "" : keyPrefix + ":";
    }

    /**
     * 增加前缀
     */
    @Override
    public String map(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (StringUtils.isNotBlank(keyPrefix) && !name.startsWith(keyPrefix)) {
            return keyPrefix + name;
        }
        return name;
    }

    /**
     * 去除前缀
     */
    @Override
    public String unmap(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (StringUtils.isNotBlank(keyPrefix) && name.startsWith(keyPrefix)) {
            return name.substring(keyPrefix.length());
        }
        return name;
    }
}
