package com.mobaijun.mybatis.plus.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: BaseEntity
 * 类描述：实体类基类
 *
 * @author MoBaiJun 2022/5/7 16:02
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
