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
package com.mobaijun.quartz.constont;

/**
 * Description:  Quartz 常量
 * Author: [mobaijun]
 * Date: [2024/8/20 9:59]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public final class JobConstant {

    private JobConstant() {
    }

    /**
     * 保留Job分组
     */
    public static final String RESERVED_JOB_GROUP = "ReservedJobGroup";

    /**
     * Quartz 属性前缀（spring.quartz.properties.*）
     */
    public static final String QUARTZ_PROPERTIES_PREFIX = "spring.quartz.properties";

    /**
     * quartz插件配置前缀
     */
    public static final String PLUGIN_PROPERTIES_PREFIX = "org.quartz.plugin";

    /**
     * 默认的存储历史插件名称
     */
    public static final String DEFAULT_STORE_JOB_HISTORY_PLUGIN_NAME = "storeJobHistory";

    /**
     * 默认的存储历史插件配置前缀
     */
    public static final String DEFAULT_STORE_JOB_HISTORY_PLUGIN_PROPERTIES_PREFIX = QUARTZ_PROPERTIES_PREFIX + "."
            + PLUGIN_PROPERTIES_PREFIX + "." + DEFAULT_STORE_JOB_HISTORY_PLUGIN_NAME;

    /**
     * 默认的存储历史插件类
     */
    public static final String DEFAULT_STORE_JOB_HISTORY_PLUGIN_CLASS = "com.mobaijun.quartz.plugin.StoreJobHistoryPlugin";
}
