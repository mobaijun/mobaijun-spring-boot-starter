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
package com.mobaijun.base.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: [修改状态请求参数]
 * Author: [mobaijun]
 * Date: [2024/8/16 11:06]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 *
 * @param <ID>     主键类型
 * @param <Status> 状态类型
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "修改状态请求参数", description = "[修改状态请求参数]")
public class QueryStatusModel<ID, Status> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "[id]字段不能为空,请检查")
    @Schema(name = "id", description = "[主键]")
    private ID id;

    @NotNull(message = "[status]字段不能为空,请检查")
    @Schema(name = "status", description = "[状态]")
    private Status status;
}
