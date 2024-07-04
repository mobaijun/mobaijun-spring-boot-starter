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
package com.mobaijun.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: PageResult
 * 类描述： 分页返回结果
 *
 * @author MoBaiJun 2022/5/7 16:00
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页返回结果")
@Schema(title = "分页返回结果")
public class PageResult<T> {

    /**
     * 查询数据列表
     */
    @Schema(title = "查询数据列表")
    @ApiModelProperty(value = "查询数据列表")
    protected List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    @Schema(title = "总数")
    @ApiModelProperty(value = "总数")
    protected Long total = 0L;
}