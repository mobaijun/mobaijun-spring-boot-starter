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
package com.mobaijun.sse.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description: [sse 配置文件]
 * Author: [mobaijun]
 * Date: [2024/10/9 17:51]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@ConfigurationProperties("sse")
public class SseProperties {

    /**
     * sse是否开启
     */
    private Boolean enabled;

    /**
     * 路径
     */
    private String path;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "SseProperties{" +
                "enabled=" + enabled +
                ", path='" + path + '\'' +
                '}';
    }
}
