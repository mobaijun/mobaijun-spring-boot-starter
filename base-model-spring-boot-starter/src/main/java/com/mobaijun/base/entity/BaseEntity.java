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
package com.mobaijun.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: BaseEntity
 * class description： 基类
 *
 * @author MoBaiJun 2022/6/30 15:05
 */
@Getter
@Setter
@ApiModel(description = "通用基类")
@Schema(title = "通用基类")
public class BaseEntity implements Serializable {

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(title = "创建者")
    @ApiModelProperty(value = "创建者")
    private Integer createBy;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(title = "更新者")
    @ApiModelProperty(value = "更新者")
    private Integer updateBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(title = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(title = "修改时间")
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
}