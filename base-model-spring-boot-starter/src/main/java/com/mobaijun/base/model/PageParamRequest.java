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

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: PageParamRequest
 * 类描述： 前端交互请求入参模型，将被转换为 PageParam 对象
 *
 * @author MoBaiJun 2022/5/7 15:56
 */
@Valid
@Getter
@Setter
@Schema(title = "分页查询入参")
public class PageParamRequest {

    @Parameter(description = "当前页码, 从 1 开始", schema = @Schema(minimum = "1", defaultValue = "1", example = "1"))
    @Min(value = 1, message = "当前页不能小于 1")
    private long current = 1;

    @Parameter(description = "每页显示条数, 最大值为 100",
            schema = @Schema(title = "每页显示条数", description = "最大值为系统设置，默认 100", minimum = "1", defaultValue = "10",
                    example = "10"))
    @Min(value = 1, message = "每页显示条数不能小于1")
    private long size = 10;

    /**
     * 排序规则
     */
    @Parameter(description = "排序规则，格式为：property(,asc|desc)。" + "默认为升序。" + "支持传入多个排序字段。", name = "sort",
            array = @ArraySchema(schema = @Schema(type = "string", pattern = PageableConstants.SORT_REGEX, example = "id,desc")))
    @Pattern(regexp = PageableConstants.SORT_REGEX, message = "排序字段格式非法")
    private List<String> sort;
}