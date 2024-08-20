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
package com.mobaijun.xxljob.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: xxl 管控台相关属性
 * Author: [mobaijun]
 * Date: [2024/8/20 9:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */

@Getter
@Setter
@ToString
public class XxlAdminProperties {

    /**
     * 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。 执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
     */
    private String addresses;
}
