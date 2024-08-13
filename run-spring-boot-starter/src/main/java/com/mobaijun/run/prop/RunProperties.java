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
package com.mobaijun.run.prop;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description: [run 配置文件]
 * Author: [mobaijun]
 * Date: [2023/11/23 11:26]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@ConfigurationProperties(prefix = RunProperties.PREFIX)
public class RunProperties {

    /**
     * 前缀
     */
    public final static String PREFIX = "spring.run.web-server";

    /**
     * 是否启用
     */
    private boolean enabled;

    /**
     * 地址集合
     */
    private List<String> url;

    public RunProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "RunProperties{" +
                "enabled=" + enabled +
                ", url=" + url +
                '}';
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
