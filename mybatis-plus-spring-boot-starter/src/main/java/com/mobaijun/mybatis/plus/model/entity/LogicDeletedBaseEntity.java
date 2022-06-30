package com.mobaijun.mybatis.plus.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: LogicDeletedBaseEntity
 * class description：
 *
 * @author MoBaiJun 2022/6/30 15:05
 */
@Getter
@Setter
public class LogicDeletedBaseEntity extends BaseEntity {

    /**
     * 逻辑删除标识，已删除: 删除时间戳，未删除: 0
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @Schema(title = "逻辑删除标识，1已删除: 删除时间戳，未删除: 0")
    @ApiModelProperty(value = "逻辑删除标识，1已删除: 删除时间戳，未删除: 0")
    private Long deleted;
}
