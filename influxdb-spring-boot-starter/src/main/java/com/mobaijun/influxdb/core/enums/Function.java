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
package com.mobaijun.influxdb.core.enums;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * EnumName: Function
 * 枚举描述：常用的聚合函数枚举
 *
 * @author MoBaiJun 2022/4/29 11:47
 */
public enum Function {

    SUM("sum", "累加"),
    LAST("last", "最后一条数据"),
    MEAN("mean", "平均数");
    private final String tag;

    private final String content;

    Function(String tag, String content) {
        this.tag = tag;
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public String getContent() {
        return content;
    }
}