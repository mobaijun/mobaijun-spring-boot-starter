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
package com.mobaijun.i18n.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 对标于 message bundle 的文件消息的抽象
 * Author: [mobaijun]
 * Date: [2024/8/15 11:58]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
@Schema(title = "国际化信息")
public class I18nMessage {

    /**
     * 国际化标识
     */
    @NotEmpty(message = "{i18nMessage.code}：{}")
    @Schema(title = "国际化标识")
    private String code;

    /**
     * 消息
     */
    @NotEmpty(message = "{i18nMessage.message}：{}")
    @Schema(title = "文本值，可以使用 { } 加角标，作为占位符")
    private String message;

    /**
     * 地区语言标签
     */
    @NotEmpty(message = "{i18nMessage.languageTag}：{}")
    @Schema(title = "语言标签")
    private String languageTag;
}
