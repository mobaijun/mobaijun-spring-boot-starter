package com.mobaijun.mybatis.plus.model.entity;

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
