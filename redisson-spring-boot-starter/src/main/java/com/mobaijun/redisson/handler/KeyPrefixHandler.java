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
